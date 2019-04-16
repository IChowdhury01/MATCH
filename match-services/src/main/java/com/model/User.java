package com.model;

import io.norberg.automatter.AutoMatter;
import java.util.ArrayList;

@AutoMatter
public interface User {

    // Required fields: username, password, displayName, hobbyList, maxTravelDistance, longitude, latitude
    String username();          // Basic account info. Username MUST BE unique, it will be our main identifier
    String password();
    String displayName();       // Display Name, this is the name users will see.
    String aboutMe();

    int maxTravelDistance();    // Maximum distance to search for friends
    double longitude();         // Geolocation data
    double latitude();
    int oldFriendCount();       // number of friends on last login - used to identify old and new users

    // TODO: Figure out how to implement boolean/string arrays and arraylists into builders and JSON handling
    boolean[] hobbyList();      // Checklist of hobbies chosen from preset list
    String[] availableHobbies();  // List of possible hobbies - same for all users, might temporary field in handler class instead
    ArrayList<String> friendsList();  // ArrayList of strings (each string is a friend's display name)

    // TODO: Profile Pic: how to allow user to upload an image from their computer to the java server & database, and store it?
}