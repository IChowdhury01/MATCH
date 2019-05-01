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
        //    stmt.executeUpdate("DROP DATABASE IF EXISTS matchdb"); // TODO: Delete this line after app is complete
            stmt.executeUpdate("SET wait_timeout = 2700000 , interactive_timeout = 2700000");
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS matchdb");
            stmt.executeUpdate("USE matchdb");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS users (username varchar(30) PRIMARY KEY NOT NULL, userdisplayname varchar(40) NOT NULL, userhash varchar(100) NOT NULL, usermaxtraveldistance varchar(30) NOT NULL, userlatitude varchar(30) NOT NULL, userlongitude varchar(30) NOT NULL, useraboutMe varchar(1000) NOT NULL, userhobbies varchar(100) NOT NULL, userPHOTO varchar(100), userenemies varchar(500))");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS messages (messageid Integer PRIMARY KEY NOT NULL, sender varchar(30) NOT NULL, receiver varchar(30) NOT NULL, message varchar(1000) NOT NULL)");
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

    public static String getHash(String username) throws SQLException {
        PreparedStatement stmt = c.prepareStatement("SELECT userhash FROM users WHERE username = ?");
        stmt.setString(1,username);
        final ResultSet rs = stmt.executeQuery();
        String password="";
        while(rs.next()) {
            password = rs.getString("userhash");
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

    public static String getEnemies(String username) throws SQLException {
        PreparedStatement stmt = c.prepareStatement("SELECT userenemies FROM users WHERE username = ?");
        stmt.setString(1,username);
        final ResultSet rs = stmt.executeQuery();
        String enemies="";
        while(rs.next()) {
            enemies = rs.getString("userenemies");
        }
        rs.close();
        return enemies;
    }

    public static boolean addEnemy(String username, String enemy) throws SQLException {
        try {
            PreparedStatement stmt = c.prepareStatement("UPDATE users SET userenemies = ? WHERE username = ?");
            stmt.setString(1, getEnemies(username) + "," + enemy);
            stmt.setString(2, username);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("[ERROR] addEnemy: " + e.getMessage());
            return false;
        }
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

    //returns an ArrayList of all the users in the db
    public static ArrayList<String[]> getMessages(String sender, String receiver) throws SQLException {
        PreparedStatement stmt = c.prepareStatement("SELECT sender, message FROM messages WHERE (sender = ? AND receiver = ?) OR (sender = ? AND receiver = ?)");
        stmt.setString(1, sender);
        stmt.setString(2, receiver);
        stmt.setString(3, receiver);
        stmt.setString(4, sender);
        ArrayList<String[]> messages = new ArrayList<String[]>();
        //ArrayList<String> senders = new ArrayList<String>();
        final ResultSet rs = stmt.executeQuery();
        while(rs.next()) {
            String[] message = {rs.getString("sender"),rs.getString("message")};
            messages.add(message);
        }
        rs.close();
        return messages;
    }

    public static boolean addMessage(String sender, String receiver, String message) throws SQLException {
        try {
            stmt = c.createStatement();
            final ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM messages");
            String count = "";
            while(rs.next()) {
                count = ""+(Integer.parseInt(rs.getString("COUNT(*)"))+1);
            }
            PreparedStatement stmt = c.prepareStatement("INSERT INTO messages(messageid, sender, receiver, message) VALUES(?,?,?,?)");
            stmt.setString(1, count);
            stmt.setString(2, sender);
            stmt.setString(3, receiver);
            stmt.setString(4, message);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("[ERROR] addMessage: " + e.getMessage());
            return false;
        }
    }



    //adds a user to the db
    public static boolean createUser (String username, String hash, String displayName, String aboutMe,
                                      double maxTravelDistance, double latitude, double longitude, String hobbies) {

        try {
            PreparedStatement usrStmt = c.prepareStatement("INSERT INTO users(username, userdisplayname, userhash, usermaxtraveldistance, userlatitude, userlongitude, useraboutMe, userhobbies,userenemies) VALUES(?,?,?,?,?,?,?,?,?)");
            usrStmt.setString(1, username);
            usrStmt.setString(2, displayName);
            usrStmt.setString(3, hash);
            usrStmt.setDouble(4, maxTravelDistance);
            usrStmt.setDouble(5, latitude);
            usrStmt.setDouble(6, longitude);
            usrStmt.setString(7, aboutMe);
            usrStmt.setString(8, hobbies);
            usrStmt.setString(9, "");
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
