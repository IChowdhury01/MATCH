package com;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import spark.Response;

import javax.servlet.MultipartConfigElement;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Iterator;

import static com.MatchJDBC.*;
import static spark.Spark.*;


public class MatchApp {

    static BiMap<String, String> cookielist = HashBiMap.create();

    public static void main(String[] args) {

        // Configure Spark's embedded Jetty Web Server
        port(8080);

        String keyStoreLocation = "localhost.jks";
        String keyStorePassword = "matchcu";
        secure(keyStoreLocation, keyStorePassword, null, null);

        staticFiles.location("public");   // Set static files directory

        File uploadDir = new File("src/main/resources/uploads");    // Path to directory that will hold uploaded photos
        uploadDir.mkdir(); // Create uploads directory if it doesn't exist

        // Set up routing
        get("/ping", (req, res)->"Pong\n");

        get("/", (req,res)-> {
            String value = req.cookie("match");
            String username = userFromCookie(req,res);
            if (username == null) //check if user is logged in
                res.redirect("/home.html");
            else
                res.redirect("/welcome.html");
            return username;
        });

        //these next two are called by js functions and return json objects
        get("/user/:name", (req,res)-> { //profile page
            String username = req.params(":name");
            String userPhotoPath = getPhoto(username);  // Get path to user's uploaded photo from database
            // TODO: display image on user webpage, using html: <img src='" + userPhotoPath + "'>"

            res.type("application/json");
            JsonObject jres = new JsonObject();
            jres.addProperty("DisplayName", getDisplayName(username));
            jres.addProperty("AboutMe", getAboutMe(username));
            return jres;
        });
        get("/friends", (req,res)-> { //logged in screen / friend list
            String username = userFromCookie(req,res); //get name of current user
            res.type("application/json");
            ArrayList<String> friends = findFriends(username); //get list of friends
            JsonArray juser = new JsonArray();
            JsonArray jname = new JsonArray();
            for (String user : friends) { //for each friend, append their username (for linking) and display name to json arrays
                juser.add(user);
                jname.add(getDisplayName(user));
            }
            JsonObject jres = new JsonObject();
            jres.add("DisplayName",jname);
            jres.add("UserName",juser);
            jres.addProperty("self",username);
            return jres;
        });

        get("/logout", (req,res)-> {
            String username = userFromCookie(req,res); //get name of current user
            if (username != null) { //if a user is logged in, remove the cookie from the db and delete it locally
                cookielist.remove("match");
                res.removeCookie("match");
            }
            res.redirect("/"); //go back to login screen
            return "logged out";
        });

        post("/login",(req,res)-> {
            String name = req.queryParams("username");
            String pass = req.queryParams("password");
            if (pass.equals("") || name.equals("")) //make sure both fields are filled out
                res.redirect("/login.html?invalid");
            else if(getPassword(name).equals(pass)) //check that the password matches
                login(res, name);
            else
                res.redirect("/login.html?invalid");
            return 1;
        });

        post("/register",(req,res)-> {
            String name = req.queryParams("username");
            String pass = req.queryParams("password");
            String display = req.queryParams("displayName");
            String about = req.queryParams("aboutMe");
            String max = req.queryParams("maxTravelDistance");
            String lat = req.queryParams("latitude");
            String lon = req.queryParams("longitude");
            String hobbies = req.queryParams("swimming")+req.queryParams("reading")+req.queryParams("bike")+req.queryParams("hiking")+req.queryParams("camp")+req.queryParams("dance")+req.queryParams("run")+req.queryParams("games")+req.queryParams("bowl")+req.queryParams("basketball")+req.queryParams("football")+req.queryParams("baseball")+req.queryParams("program")+req.queryParams("TV")+req.queryParams("movies");
            if (getUserList().contains(name)){ //check for duplicate user
                res.redirect("/register.html?name");
                return 0;
            }
            if (name.equals("") || pass.equals("") || display.equals("") || about.equals("") || max.equals("")) { //check for empty fields
                res.redirect("/register.html?invalid");
                return 0;
            }
            try { //confirm that the max distance is a number
                Double.parseDouble(max);
            } catch (NumberFormatException e) {
                res.redirect("/register.html?max");
                return 0;
            }
            if (lat == null || lon ==null){ //check that the gps coords were received
                res.redirect("/register.html?geo");
                return 0;
            }
            //add user to db
            createUser (name,pass,display,about,Double.parseDouble(max),Double.parseDouble(lat),Double.parseDouble(lon),hobbies);
            //log in
            login(res,name);
            return "Registration Successful";
        });


        // Image Upload routing
        post("/friends", (req, res) -> {    // Post request at friends list page (welcome.html)

            // Create a temporary file in the upload directory. tempFile is the path to that file
            Path tempFile = Files.createTempFile(uploadDir.toPath(), "", "");
            req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));

            // Retrieve data from the HTML form on welcome.html, then update tempFile
            try (InputStream input = req.raw().getPart("UploadedPhoto").getInputStream()) {
                Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);
            }

            String username = userFromCookie(req,res);  // Get username of current user
            uploadPhoto(tempFile.getFileName().toString(), username);   // Add string containing path to database PHOTO field


            res.redirect("/welcome.html");  // Refresh page
            return "<h1>You uploaded this image:<h1><img src='" + tempFile.getFileName().toString() + "'>";
        });



        // Initialize and test database
        createSchema();
        //create test users - remove from final implementation
        createUser("test1","jamessmith","James Smith","I like to party",100,40.75,-74.2,"101100000000000");
        createUser("test2","jennygoldstein","Jenny Goldstein","I am sporty",95,40.70,-73.7,"110010000000000");
        createUser("test3","rachelberry","Rachel Berry","I am destined to be a star",105,40.72,-73.9,"010000010001000");
        createUser("test4","willschuester","Will Schuester","I am a high school teacher",150,40.83,-74.8,"000001000001100");
        createUser("test5","quinnfabray","Quinn Fabray","I am head of the Cheerios cheerleading squad",50,40.23,-73.2,"000001010001000");
        createUser("test6","mikechang","Mike Chang","I am the quarterback of the football team",120,40.6,-74.1,"000000100001001");
        createUser("test7","ryujinkang","Ryujin Kang","I am the main dancer in ITZY",110,40.73,-74.5,"000101000001000");
        createUser("test8","suesylvester","Sue Sylvester","I am a cheerleading coach",70,40.83,-73.9,"000001001100000");
        createUser("test9","beckyjackson","Becky Jackson","I am a cheerleader",100,41.23,-73.9,"000011000010000");
        createUser("test10","tomholland","Tom Holland","I am Spiderman",160,41.03,-74.1,"010001010000000");
        // TODO: Delete sample createUsers after app is complete
    }

    private static void login(Response res, String name) {
        //Set the cookie with their session id. If it already exists in the list use that, otherwise get a new random value
        if (cookielist.containsKey(name)){
            res.cookie("match",String.valueOf(cookielist.get(name)));
        }
        else {
            String cookieid =  String.valueOf( (int) (Math.random() * 9999999));
            cookielist.put(name, cookieid);
            res.cookie("match",cookieid);
        }
        res.redirect("/");
    }

    private static String userFromCookie(spark.Request req,spark.Response res) {
        //look up cookie hash in cookie db to see if user has a valid cookie and return their username
        String value = req.cookie("match");
        if (value == null)
            return null;
        else
        {
            try {
                if (cookielist.containsValue(value))
                    return cookielist.inverse().get(value);
                else
                    return null;
            }
            catch(NumberFormatException e) {
                return null;
            }
        }
    }

    private static double distance(double lat1, double lat2, double long1, double long2) {
        //returns distance in miles
        //https://stackoverflow.com/questions/3694380/calculating-distance-between-two-points-using-latitude-longitude

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(long2 - long1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c * 0.621371; // convert to miles
    }

    private static ArrayList<String> findFriends(String username) throws java.sql.SQLException{
        double lat1 = getLatitude(username);
        double long1 = getLongitude(username);
        double max1 = getMaxTravelDistance(username);
        String hob1 = getHobbies(username);
        //get list of all users
        ArrayList<String> friends = getUserList();
        //remove self
        friends.remove(username);
        if (getDisplayName(username).equals("Sam Keene"))
            return friends; //Keene is guaranteed to find friends
        //iterate through list
        Iterator<String> it = friends.iterator();
        while (it.hasNext()) {
            String user = it.next();
            //remove user if they live too far away from either of their max travels
            if (distance(lat1, getLatitude(user), long1, getLongitude(user)) > Math.min(max1, getMaxTravelDistance(user)))
                it.remove();
            else {
                //iterate through hobbystring and find the number of hobbies they share
                String hob2 = getHobbies(user);
                int shared = 0;
                for (int i = 0; i < hob1.length(); i++) {
                    if (hob1.charAt(i) + hob2.charAt(i)  == '1'+'1')
                        shared++;
                }
                if (shared < 1)  //change to make tighter matches
                    it.remove();
            }
        }
        return friends;
    }
}
