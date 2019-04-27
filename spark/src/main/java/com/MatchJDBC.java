package com;

import java.sql.*;
import java.util.ArrayList;

public class MatchJDBC {
    static Connection c = null;
    static Statement stmt = null;
    public static int usercount;

    static void createSchema () {
        //create schema and table if they don't exist
        try {
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=EST","root","");
            stmt = c.createStatement();
            stmt.executeUpdate("DROP DATABASE IF EXISTS matchdb"); // TODO: Delete this line after app is complete
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS matchdb");
            stmt.executeUpdate("USE matchdb");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS users (username varchar(30) PRIMARY KEY NOT NULL, userdisplayname varchar(40) NOT NULL, userpassword varchar(50) NOT NULL, usermaxtraveldistance varchar(30) NOT NULL, userlatitude varchar(30) NOT NULL, userlongitude varchar(30) NOT NULL, useraboutMe varchar(1000) NOT NULL, userhobbies varchar(100) NOT NULL, userPHOTO varchar(100))");
        } catch (SQLException e) {
            System.err.println("[ERROR] createSchema : " + e.getMessage());
        }
    }

    //the following methods query the db and return a property of the given user
    public static String getDisplayName(String username) throws SQLException {
        PreparedStatement stmt = c.prepareStatement("SELECT userdisplayname FROM users WHERE username = ?");
        stmt.setString(1,username);
        final ResultSet rs = stmt.executeQuery();
        String displayName="";
        while(rs.next()) {
            displayName = rs.getString("userdisplayname");
        }
        rs.close();
        return displayName;
    }

    public static String getPassword(String username) throws SQLException {
        PreparedStatement stmt = c.prepareStatement("SELECT userpassword FROM users WHERE username = ?");
        stmt.setString(1,username);
        final ResultSet rs = stmt.executeQuery();
        String password="";
        while(rs.next()) {
            password = rs.getString("userpassword");
        }
        rs.close();
        return password;
    }

    public static String getAboutMe(String username) throws SQLException {
        PreparedStatement stmt = c.prepareStatement("SELECT useraboutMe FROM users WHERE username = ?");
        stmt.setString(1,username);
        final ResultSet rs = stmt.executeQuery();
        String aboutMe="";
        while(rs.next()) {
            aboutMe = rs.getString("useraboutMe");
        }
        rs.close();
        return aboutMe;
    }

    public static String getHobbies(String username) throws SQLException {
        PreparedStatement stmt = c.prepareStatement("SELECT userhobbies FROM users WHERE username = ?");
        stmt.setString(1,username);
        final ResultSet rs = stmt.executeQuery();
        String hobbies="";
        while(rs.next()) {
            hobbies = rs.getString("userhobbies");
        }
        rs.close();
        return hobbies;
    }


    public static double getMaxTravelDistance(String username) throws SQLException {
        PreparedStatement stmt = c.prepareStatement("SELECT usermaxtraveldistance FROM users WHERE username = ?");
        stmt.setString(1,username);
        final ResultSet rs = stmt.executeQuery();
        double distance=0.0;
        while(rs.next()) {
            distance = rs.getInt("usermaxtraveldistance");
        }
        rs.close();
        return distance;
    }

    public static double getLatitude(String username) throws SQLException {
        PreparedStatement stmt = c.prepareStatement("SELECT userlatitude FROM users WHERE username = ?");
        stmt.setString(1,username);
        final ResultSet rs = stmt.executeQuery();
        double latitude=0.0;
        while(rs.next()) {
            latitude = rs.getDouble("userlatitude");
        }
        rs.close();
        return latitude;
    }

    public static double getLongitude(String username) throws SQLException {
        PreparedStatement stmt = c.prepareStatement("SELECT userlongitude FROM users WHERE username = ?");
        stmt.setString(1,username);
        final ResultSet rs = stmt.executeQuery();
        double longitude=0.0;
        while(rs.next()) {
            longitude = rs.getDouble("userlongitude");
        }
        rs.close();
        return longitude;
    }

    //returns an ArrayList of all the users in the db
    public static ArrayList<String> getUserList() throws SQLException {
        final ResultSet rs = stmt.executeQuery("SELECT username FROM users");
        ArrayList<String> users = new ArrayList<String>();
        while(rs.next()) {
            users.add(rs.getString("username"));
        }
        rs.close();
        return users;
    }

    //adds a user to the db
    public static boolean createUser (String username, String password, String displayName, String aboutMe,
                                      double maxTravelDistance, double latitude, double longitude, String hobbies) {

        try {
            PreparedStatement usrStmt = c.prepareStatement("INSERT INTO users(username, userdisplayname, userpassword, usermaxtraveldistance, userlatitude, userlongitude, useraboutMe, userhobbies) VALUES(?,?,?,?,?,?,?,?)");
            usrStmt.setString(1, username);
            usrStmt.setString(2, displayName);
            usrStmt.setString(3, password);
            usrStmt.setDouble(4, maxTravelDistance);
            usrStmt.setDouble(5, latitude);
            usrStmt.setDouble(6, longitude);
            usrStmt.setString(7, aboutMe);
            usrStmt.setString(8, hobbies);
            usrStmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("[ERROR] createUser: " + e.getMessage());
            return false;
        }
        return true;
    }

    // Photo upload methods
    /**
     * This function will store the temporary path to the uploaded photo into the USER database's PHOTO field (text)
     * @param pathToUpload  This string is the temporary path to the uploaded photo tempFile.getFileName().toString()
     * @param username  This is the username
     * @return
     */
    public static boolean uploadPhoto(String pathToUpload, String username) {
        PreparedStatement stmt;
        if(username == null)
            return false;
        try {
            stmt = c.prepareStatement("UPDATE users SET userPHOTO = ? WHERE username = ?");

            stmt.setString(1, pathToUpload);
            stmt.setString(2, username);

            stmt.executeUpdate();
        }
        catch ( Exception e ) {
            System.err.println("[ERROR] uploadPhoto : " + e.getMessage());
            return false;
        }

        System.out.println(username+"'s profile picture has been uploaded successfully.");
        return true;
    }

    /**
     *     This function retrieves a String containing the path to the user's uploaded profile photo, from the database
     *     To display a user's photo on a webpage, call getPhoto(user's username) and store the String variable.
     *     Then, the image can be loaded via HTML tag
     *     Example:
     *     return "<img src='" + getPhoto(test1) + "'>";
     */
    public static String getPhoto(String username) throws SQLException {
        PreparedStatement stmt = c.prepareStatement("SELECT userPHOTO FROM users WHERE username = ?");
        stmt.setString(1,username);
        final ResultSet rs = stmt.executeQuery();
        String pathtoPhoto="";
        while(rs.next()) {
            pathtoPhoto = rs.getString("userPHOTO");
        }
        rs.close();
        return pathtoPhoto;
    }
}
