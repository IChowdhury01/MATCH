package com.User;

import java.util.ArrayList;

public class User {

    // Fields
    private static String[] availableHobbies; // List of possible hobbies - same for all users

    private String username;
    private String password;
    private String displayName;

    private boolean[] hobbyList;  // Checklist of chosen hobbies
    private int maxTravelDistance;
    private int oldFriendCount; //number of friends on last login
    private double longitude, latitude; // Geolocation data
    // Profile Pic - field not added yet

    // Constructor
    public User(String newUsername, String newPass, int newMTDist, double newLat, double newLong, String newDisplayName, boolean[] newHobbies) {                //constructor for new account
        // Create new user from signup info
        this.username = newUsername;
        this.password = newPass;
        this.displayName = newDisplayName;
        this.hobbyList = newHobbies;
        this.maxTravelDistance = newMTDist;
        this.latitude = newLat;
        this.longitude = newLong;

        // Create friends list for the user
        ArrayList<String> friendsList = new ArrayList<String>();  // Arraylist of strings (each string is a friend's display name
    }

    // Getters/Setters
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String name) {
        this.username = name;
    }


    public String getPass() {
        return this.password;
    }

    public void setPass(String pass) {
        this.password = pass;
    }


    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String dispname) {
        this.displayName= dispname;
    }


    public int getMaxTravelDistance() {
        return this.maxTravelDistance;
    }

    public void setMTDistance(int dist) {
        this.maxTravelDistance= dist;
    }


    public double getLatitude() {
        return this.latitude;
    }
    public double getLongitude() {
        return this.longitude;
    }

    public void setLatitude(double lat) {
        this.latitude = lat;
    }
    public void setLongitude(double lng) {
        this.longitude= lng;
    }


//  Array/ArrayList setter/getter - incomplete
//    public Boolean[] getHobbies() {
//        for (Boolean k : hobbyList) {
//            System.out.println(k);
//        }
//    }
//
//    public void getPossibleHobbies() {
//        for (String j : availableHobbies) {
//            System.out.println(j);
//        }
//    }
//
//    public void getFriendsList() {  // Print contents of friends list
//        for (String i : friendsList) {
//            System.out.println(i);
//        }
//    }

}
