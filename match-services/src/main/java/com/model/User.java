package com.model;

import io.norberg.automatter.AutoMatter;
import java.util.ArrayList;

@AutoMatter
public interface User {
    String username();          // Basic account info. Username MUST BE unique, it will be our main identifier
    String password();
    String displayName();       // Display Name =
    String aboutMe();

    boolean[] hobbyList();      // Checklist of hobbies chosen from preset list
    int maxTravelDistance();    // Maximum distance to search for friends
    double longitude();         // Geolocation data
    double latitude();

    int oldFriendCount();       // number of friends on last login - used to identify old and new users
    String[] availableHobbies();  // List of possible hobbies - same for all users
    ArrayList<String> friendsList();  // ArrayList of strings (each string is a friend's display name)

    // Profile Pic: how to allow user to upload an image from their computer to the java server & database, and store it?
}