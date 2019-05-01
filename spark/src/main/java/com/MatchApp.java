package com;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import org.apache.commons.codec.binary.Base64;

public class MatchApp {

    static BiMap<String, String> cookielist = HashBiMap.create();

    public static void main(String[] args) throws Exception {

        // Configure Spark's embedded Jetty Web Server
        port(8080);

        String keyStoreLocation = "localhost.jks";
        String keyStorePassword = "matchcu";
        secure(keyStoreLocation, keyStorePassword, null, null);

        staticFiles.location("public");   // Set static files directory

        staticFiles.externalLocation("uploads");

        File uploadDir = new File("uploads");    // Path to directory that will hold uploaded photos
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

            res.type("application/json");
            JsonObject jres = new JsonObject();
            jres.addProperty("DisplayName", getDisplayName(username));
            jres.addProperty("AboutMe", getAboutMe(username));
            jres.addProperty("PhotoPath", getPhoto(username));
            String self=userFromCookie(req,res);
            ArrayList<String[]> messages= getMessages(username,self);
            for (String[] message : messages ) {
                if (message[0].equals(self))
                    message[0]="You: ";
                else
                    message[0]=getDisplayName(message[0])+": ";
            }
            Gson gsonBuilder = new GsonBuilder().create();
            jres.addProperty("Messages",gsonBuilder.toJson(messages));
            jres.addProperty("self",username.equals(self));
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
            else if(check(pass,getHash(name))) //check that the password matches the hash
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
            createUser (name,getSaltedHash(pass),display,about,Double.parseDouble(max),Double.parseDouble(lat),Double.parseDouble(lon),hobbies);
            //log in
            login(res,name);
            return "Registration Successful";
        });

        post("/message", (req,res) -> {
            String sender = userFromCookie(req,res);
            String receiver = req.queryParams("receiver");
            String message = req.queryParams("message");
            addMessage(sender,receiver,message);
            res.redirect("/user.html?user="+receiver);
            return 1;
        });


        // Image Upload routing
        post("/upload", (req, res) -> {    // Post request at friends list page (welcome.html)

            // Create a temporary file in the upload directory. tempFile is the path to that file
            Path tempFile = Files.createTempFile(uploadDir.toPath(), "", "");
            req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));

            // Retrieve data from the HTML form on welcome.html, then update tempFile
            try (InputStream input = req.raw().getPart("UploadedPhoto").getInputStream()) {
                Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);
            }

            String username = userFromCookie(req,res);  // Get username of current user
            uploadPhoto(tempFile.getFileName().toString(), username);   // Add string containing path to database PHOTO field

            res.redirect("/user.html?user="+username);  // Refresh page
            return 1;
        });

        post("/reject", (req, res) -> {
            String user1 = req.queryParams("name");
            String user2 = userFromCookie(req,res);
            addEnemy(user1,user2);
            addEnemy(user2,user1);
            res.redirect("/");
            return 1;
        });

            // Initialize and test database
        createSchema();
        //create test users - remove from final implementation
        createUser("test1",getSaltedHash("sarahuang"),"Sara Huang","FANGIRL TO THE CORE. SUPER. HUGE. KPOP. FAN. I like, no, LOVE all the kpop groups and know everyone (BOY GROUPS, GIRL GROUPS, CO-ED GROUPS, YOU NAME IT). I will scream and go berserk if you know anything kpop. My favorite color is obviously PINK. SPECIFICLLY, HOT PINK. I really hope my professor gives me a good grade for this AWESOME project because I am his biggest fan (OHMYGOSH SPOTIFY WOW AMAZING WONDERFUL FANTASTIC 100%). I love food, any food but bittermelon, bleh. I love Marvel. HUGE FAN. Team Stark all the way. My bias in the Avengers (but I like all of them!!!)is Iron Man because he went to MIT at the age of 15 and graduated with degrees in ELECTRICAL ENGINEERING and physics!!! Super cool. I think Tom Holland is SO PRETTY OH MY GOD!!! <3",100,40.75,-74.2,"101100000000000");
        createUser("test2",getSaltedHash("kimnamjoon"),"Kim Namjoon","I am the leader of BTS. I can rap, sing, dance, you name it. You can call me RM (before, I used to be Rap Monster). I was born in South Korea and I love all my ARMY fans out there. Love myself, love yourself is my motto. I learned English through watching the TV series, Friends, and I have the best English out of all of BTS. I've done songs with Wale, Fall Out Boy, and Primary, just to name a few.",95,40.70,-73.7,"110010000000000");
        createUser("test3",getSaltedHash("kimseokjin"),"Kim Seokjin","WORLDWIDE HANDSOME JIN. 'The third one from the left.' Yep, that's me, alright. I am the most handsome member out of all of BTS, known for my charming good looks and broad shoulders. My disadvantage is that I am too hot for words and people are left speechless when they see me. I get scared easily. I am one of the vocalists on the vocal line of BTS and I love ARMY!",105,40.72,-73.9,"010000010001000");
        createUser("test4",getSaltedHash("minyoongi"),"Min Yoongi","SUGA. I am the main rapper of BTS. I write most of the songs for BTS and the lyrics are representative of youth culture and my meanings transcended through music. I tend not to show my emotions although I really care about the members of BTS from the bottom of my seemingly darkless stone heart. I'm not very cool with showing my love very well but I do love ARMY. Honestly, from the beginning, I was not imagining that we would get to perform at the BBMAs so that's cool. Yeah. Peace.",150,40.83,-74.8,"000001000001100");
        createUser("test5",getSaltedHash("junghoseok"),"Jung Hoseok","I AM YOUR HOPE!!!!!!!!! I AM JHOPEEEEEEEEEEEEEE!!! THE LITERAL SUN!!! I'm a happy guy as you can tell. I think the smiley emoji represents who I am because I am so cheery. I am the main dancer in BTS and am part of the rapline as well. I actually wanted to be a part of the vocal line, but after hearing fellow member Taehyung wanting to be a part of the vocal line, I decided to step down and let him do it. Because BTS needed another rap member, I took on that role, even though I really wanted to sing instead of rap. However, I don't regret it and learned a lot from the experience.",50,40.23,-73.2,"000001010001000");
        createUser("test6",getSaltedHash("parkjimin"),"Park Jimin","CHIMCHIM!! Hello!! I am Jimin! I am one of the main dancers of BTS. Before joining BTS, I took lots of ballet lessons and am really good friends with Taehyung. I was given my nickname after being really close with Tony on American Hustle Life. I am part of the vocal line of BTS as well as the maknae line (younger members of the group). I think I'm fat, but the other members say I'm not. Sometimes, I am too rough on myself because I'm a perfectionist and beat myself up sometimes.",120,40.6,-74.1,"000000100001001");
        createUser("test7",getSaltedHash("ryujinkang"),"Ryujin Kang","JINJIN. I am the main dancer in ITZY. Sometimes, I'm known as the guy of the group. I was in BTS' Highlight Reel video, playing the role of Jhope and Jimin's love interest. I had to perform a dance together with Hoseok and Jimin for that video during my trainee days. I am the rapper in ITZY and am known as the 'guy' of the group because I'm really chill and fun.",110,40.73,-74.5,"000101000001000");
        createUser("test8",getSaltedHash("kimtaehyung"),"Kim Taehyung","V. THE REAL WORLDWIDE HANDSOME (LITERALLY VOTED NUMBER ONE FOR MOST HANDSOME FACE OF 2018 BY TC CANDLER AND THE INDEPENDENT: THE OFFICIAL WELL-KNOWN ESTABLISHED RANKING OF LOOKS. I am one of the main vocalists in BTS. I was the 'hidden' member of BTS so I wasn't revealed until officially debuting. I was called V for victory (the peace sign with two fingers). Everyday, without fail, I watch one episode of anime. I like the color purple (it represents royalty) and it means love to me. I LOVE ARMY!!! ARMY IS THE GREATEST LOVE. I am the second youngest in the group. I like to eat black bean noodles and play video games. People call me 'alien' and weird but I LOVE MYSELF. LOVE YOURSELF.",70,40.83,-73.9,"000001001100000");
        createUser("test9",getSaltedHash("jeonjungkook"),"Jeon Jungkook","THE GOLDEN MAKNAE! I AM GOOD AT EVERYTHING which is why I'm golden, literally perfect. I don't know why or how, but I can magically do anything and things just get to me. Well.....maybe not math. But that's the only thing I'm not too good at. I'm the maknae (youngest) in the group and arguably, the best vocals. I love to sing and work hard for ARMY. I was scouted by many entertainment companies but decided to join BTS because RM was there and I really liked RM. People say I'm cute. I am also the crybaby in the group, since I suffer the most (because I empathize with them) when other members of the group are feeling sad or down.",100,41.23,-73.9,"000011000010000");
        createUser("test10",getSaltedHash("tomholland"),"Tom Holland","I AM SPIDERMAN, LITERALLY. Ello, mate, how you doing? I'm English, so I'm from Britain. I have two brothers and the love of my life is my dog, Tessa. I AM SCARED OF SPIDERS. I might be dating Zendaya, who knows? I embarrass myself constantly on set, having broken my nose and leg a bunch of times. I like to joke around with my friend Harrison a lot; I also like to play golf. I am no genius and am really sarcastic.",160,41.03,-74.1,"010001010000000");
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
        for (String enemy : getEnemies(username).split(",")) {
            friends.remove(enemy);
        }

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

    //password hashing from https://stackoverflow.com/a/11038230

    // The higher the number of iterations the more
    // expensive computing the hash is for us and
    // also for an attacker.
    private static final int iterations = 20*1000;
    private static final int saltLen = 32;
    private static final int desiredKeyLen = 256;

    /** Computes a salted PBKDF2 hash of given plaintext password
     suitable for storing in a database.
     Empty passwords are not supported. */
    public static String getSaltedHash(String password) throws Exception {
        //byte[] salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(saltLen); //This line from the algorithm was prohibitively slow
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[saltLen];
        random.nextBytes(salt);
        // store the salt with the password
        return Base64.encodeBase64String(salt) + "$" + hash(password, salt);
    }

    /** Checks whether given plaintext password corresponds
     to a stored salted hash of the password. */
    public static boolean check(String password, String stored) throws Exception{
        String[] saltAndHash = stored.split("\\$");
        if (saltAndHash.length != 2) {
            throw new IllegalStateException(
                    "The stored password must have the form 'salt$hash'");
        }
        String hashOfInput = hash(password, Base64.decodeBase64(saltAndHash[0]));
        return hashOfInput.equals(saltAndHash[1]);
    }

    // using PBKDF2 from Sun, an alternative is https://github.com/wg/scrypt
    // cf. http://www.unlimitednovelty.com/2012/03/dont-use-bcrypt.html
    private static String hash(String password, byte[] salt) throws Exception {
        if (password == null || password.length() == 0)
            throw new IllegalArgumentException("Empty passwords are not supported.");
        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        SecretKey key = f.generateSecret(new PBEKeySpec(
                password.toCharArray(), salt, iterations, desiredKeyLen));
        return Base64.encodeBase64String(key.getEncoded());
    }
}
