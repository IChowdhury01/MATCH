package com;

import java.sql.*;

public class MatchJDBC {
    static Connection c = null;
    static Statement stmt = null;

    static void createSchema () {
        try {
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/matchdb","root","");
            //c.setAutoCommit(false);
            stmt = c.createStatement();
            stmt.executeUpdate("DROP DATABASE IF EXISTS matchdb");
            stmt.executeUpdate("CREATE DATABASE matchdb");
            stmt.executeUpdate("USE matchdb");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS users (userid INTEGER PRIMARY KEY NOT NULL, username varchar(30) NOT NULL, userdisplayname varchar(40) NOT NULL, userpassword varchar(50) NOT NULL, usermaxtraveldistance INTEGER NOT NULL, userlatitude varchar(30) NOT NULL, userlongitude varchar(30) NOT NULL, useraboutMe varchar(1000) NOT NULL)");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS hobbies (hid INTEGER PRIMARY KEY NOT NULL, hinterests varchar(100))");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS userhobbies (uhiid INTEGER NOT NULL, uhhid INTEGER NOT NULL, PRIMARY KEY(uhiid,uhhid))");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS friendsList (fid1 INTEGER NOT NULL, fid2 INTEGER NOT NULL, PRIMARY KEY(fid1,fid2))");
        } catch (SQLException e) {
            System.err.println("[ERROR] createSchema : " + e.getMessage());
        }
    }
    static void initSchema () {
        final String[] user = new String[] {
            "1,test1,James Smith,jamessmith,10,40,30,I like to party",
            "2,test2,Jenny Goldstein,jennygoldstein,20,25,30,I am sporty",
            "3,test3,Rachel Berry,rachelberry,25,15,35,I am destined to be a star",
            "4,test4,Will Schuester,willschuester,15,35,45,I am a high school teacher",
            "5,test5,Quinn Fabray,quinnfabray,5,16,35,I am head of the Cheerios cheerleading squad",
            "6,test6,Mike Chang,mikechang,10,35,35,I am the quarterback of the football team",
            "7,test7,Ryujin Kang,ryujinkang,15,20,20,I am the main dancer in ITZY",
            "8,test8,Sue Sylvester,suesylvester,5,10,80,I am a cheerleading coach",
            "9,test9,Becky Jackson,beckyjackson,10,25,25,I am a cheerleader",
            "10,test10,Tom Holland,tomholland,30,79,75,I am Spiderman"
        };
        final String[] hobby = new String[] {
            "1,Swimming",
            "2,Reading",
            "3,Bicycling",
            "4,Hiking",
            "5,Camping",
            "6,Dancing",
            "7,Running",
            "8,Video Games",
            "9,Bowling",
            "10,Basketball",
            "11,Football",
            "12,Baseball",
            "13,Programming",
            "14,Watching TV",
            "15,Going to the Movies"
        };
        final String[] userHobby = new String[] {
            "1,1",
            "1,3",
            "1,4",
            "2,1",
            "2,2",
            "2,5",
            "3,12",
            "3,2",
            "3,8",
            "4,12",
            "4,6",
            "4,13",
            "5,12",
            "5,8",
            "5,6",
            "6,15",
            "6,12",
            "6,7",
            "7,6",
            "7,12",
            "7,4",
            "8,10",
            "8,6",
            "8,9",
            "9,11",
            "9,5",
            "9,6",
            "10,2",
            "10,8",
            "10,6"
        };

        try {
            PreparedStatement usrStmt = c.prepareStatement("INSERT INTO users VALUES(?,?,?,?,?,?,?,?)");
            for (String u : user) {
                final String[] cols = u.split(",");
                usrStmt.setInt(1, Integer.valueOf(cols[0]));
                usrStmt.setString(2, cols[1]);
                usrStmt.setString(3, cols[2]);
                usrStmt.setString(4, cols[3]);
                usrStmt.setInt(5, Integer.valueOf(cols[4]));
                usrStmt.setString(6, cols[5]);
                usrStmt.setString(7, cols[6]);
                usrStmt.setString(8, cols[7]);
                usrStmt.executeUpdate();
            }
            PreparedStatement hbyStmt = c.prepareStatement("INSERT INTO hobbies VALUES(?,?)");
            for (String h : hobby) {
                final String[] cols = h.split(",");
                hbyStmt.setInt(1, Integer.valueOf(cols[0]));
                hbyStmt.setString(2, cols[1]);
                hbyStmt.executeUpdate();
            }
            PreparedStatement usrhbyStmt = c.prepareStatement("INSERT INTO userHobbies VALUES (?,?)");
            for (String r : userHobby) {
                final String[] cols = r.split(",");
                usrhbyStmt.setInt(1, Integer.valueOf(cols[0]));
                usrhbyStmt.setInt(2, Integer.valueOf(cols[1]));
                usrhbyStmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] initSchema: " + e.getMessage());
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

    public static int getMaxTravelDistance(String username) throws SQLException {
        PreparedStatement stmt = c.prepareStatement("SELECT usermaxtraveldistance FROM users WHERE username = ?");
        stmt.setString(1,username);
        final ResultSet rs = stmt.executeQuery();
        int distance=0;
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
        return latitude=0.0;
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
            Double maxTravelDistance, Double latitude, Double longitude,
            boolean Swimming, boolean Reading, boolean Biking, boolean Hiking, boolean Camping,
            boolean Dancing, boolean Running, boolean Video_Games, boolean Bowling, boolean Basketball,
            boolean Football, boolean Baseball, boolean Programming, boolean Watching_TV, boolean Going_to_the_Movies) {
        return false;
    }

    public static String matchUserHobby(String hobby) throws SQLException {
        PreparedStatement stmt = c.prepareStatement("SELECT DISTINCT userdisplayname FROM users, hobbies, userHobbies WHERE userHobbies.uhiid = users.userid and userHobbies.uhhid = hobbies.hid and hobbies.hinterests = '?'");
        stmt.setString(1,hobby);
        final ResultSet rs = stmt.executeQuery();
        String users = "";
        while(rs.next()) {
            users = rs.getString("userdisplayname");
        }
        rs.close();
        return users;
    }
}