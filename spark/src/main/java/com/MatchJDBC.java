package com;

import java.sql.*;
import java.util.ArrayList;

public class MatchJDBC {
    static Connection c = null;
    static Statement stmt = null;
    public static int usercount;

    static void createSchema () {
        try {
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/","root","");
            //c.setAutoCommit(false);
            stmt = c.createStatement();
//            stmt.executeUpdate("DROP DATABASE IF EXISTS matchdb"); //uncomment this line if you need to flush the db
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS matchdb");
            stmt.executeUpdate("USE matchdb");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS users (username varchar(30) PRIMARY KEY NOT NULL, userdisplayname varchar(40) NOT NULL, userpassword varchar(50) NOT NULL, usermaxtraveldistance varchar(30) NOT NULL, userlatitude varchar(30) NOT NULL, userlongitude varchar(30) NOT NULL, useraboutMe varchar(1000) NOT NULL, userhobbies varchar(100) NOT NULL)");
        } catch (SQLException e) {
            System.err.println("[ERROR] createSchema : " + e.getMessage());
        }
    }

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

    public static ArrayList<String> getUserList() throws SQLException {
        final ResultSet rs = stmt.executeQuery("SELECT username FROM users");
        ArrayList<String> users = new ArrayList<String>();
        while(rs.next()) {
            users.add(rs.getString("username"));
        }
        rs.close();
        return users;
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


    public static boolean createUser (String username, String password, String displayName, String aboutMe,
            double maxTravelDistance, double latitude, double longitude, String hobbies) {

        try {
            PreparedStatement usrStmt = c.prepareStatement("INSERT INTO users VALUES(?,?,?,?,?,?,?,?)");
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

}