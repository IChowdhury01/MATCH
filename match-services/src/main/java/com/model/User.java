package com.model;

import io.norberg.automatter.AutoMatter;

import javax.annotation.Nullable;

@AutoMatter
public interface User {
// Do clean compile any time you change automatter

    // Required fields: username, password, displayName, hobbyList, maxTravelDistance, longitude, latitude
    String username();          // Basic account info. Username MUST BE unique, it will be our main identifier
    String password();
    String displayName();       // Display Name, this is the name users will see.
    @Nullable
    String aboutMe();

    int maxTravelDistance();    // Maximum distance to search for friends
    double longitude();         // Geolocation data
    double latitude();


// Do @Nullable before an automatter field if you want to make it so that it can be null (it doesn't need to have a specific value for the UserBuilder to work)
}