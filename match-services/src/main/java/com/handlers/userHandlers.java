package com.handlers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.model.User;
import com.model.UserBuilder;
import com.spotify.apollo.RequestContext;
import com.spotify.apollo.Response;
import com.spotify.apollo.Status;
import com.spotify.apollo.route.*;
import com.store.UserStore;
import okio.ByteString;

import java.io.IOException;
import java.util.stream.Stream;

/**
 *  This class implements user-specific routing, the functions called to handle
 *  each route (such as for registering a user or logging in and finding friends), and
 *  middleware used for JSON serialization.
 */

//TODO: Implement cookies (field, constructor, getCookie/createCookie) and Middleware. Finalize login/logout (cookie methods)
public class userHandlers implements RouteProvider {

    // Fields
    private final ObjectMapper objectmapper;
    private final UserStore store;
    // Implement Cookie field - for persistent user session and logout
    static BiMap<String, Integer> cookielist = HashBiMap.create();

    // Constructor
    public userHandlers(final ObjectMapper objectMapper, final UserStore userstore) {
        this.objectmapper = objectMapper;
        this.store = userstore;
    }

    // Route.sync - runs methods e.g. getUser
    // .RegisterAutoRoute(Route.sync) - just sends a string
    // Routing
    @Override
    public Stream<Route<AsyncHandler<Response<ByteString>>>> routes() {
        return Stream.of (
            Route.sync("GET", "/user/<username>", this::getUser)
                .withMiddleware(jsonMiddleware()),

            Route.sync("POST", "/register", this::createUser)
                .withMiddleware(jsonMiddleware()),

            Route.sync("POST", "/login", this::userLogin)
                .withMiddleware(jsonMiddleware()),

            Route.sync("POST", "/user/<username>/logout", this::userLogout)
                    .withMiddleware(jsonMiddleware())
        );
    }

    // Handler Functions (to be unit tested)
    /**User Login method
     * Serialize to JSON
     * Check that username and password fields are filled.
     * Find the password for the specified username, in the database.
     * Compare to given password. If it is the same, log in (return a response containing the user's cookieID)
     *
     * Initiate friend-matching algorithm (final implementation)
     * Query the database for a list of users that have any matching hobbies.
     * Filter the list for users within max travel distance
     * Count the number of friends matched during this login.
     * Each friend has their display name (and picture) shown.
     */

    @VisibleForTesting
    public Response<Integer> userLogin(RequestContext requestContext) {
        JsonNode userJSON2 = null;
        String passinDB = null;
        User userinDB = null;
        try {
            userJSON2 = objectmapper.readTree(requestContext.request().payload().get().utf8());

            if (userJSON2 != null) {
                // Check if any fields are blank
                if (
                        (userJSON2.get("username").asText() == null) || (userJSON2.get("username").asText().isEmpty()) ||
                                (userJSON2.get("password").asText() == null) || (userJSON2.get("password").asText().isEmpty())
                ) {
                    throw new IOException("Login failed: username and password fields must be filled");
                }
            }
            // Check if given username is in database
            userinDB = store.getUser(userJSON2.get("username").asText());
            if (userinDB == null) {
                throw new IOException("Login failed: user does not exist");
            }

            // Check if given password matches database password for that user
            passinDB = userinDB.password();
            if (!passinDB.equals(userJSON2.get("username").asText())) {
                throw new IOException("Login failed: incorrect password");
            }

        } catch (IOException err) {
            System.out.println(err);
        }

        if ((passinDB != null) && (passinDB.equals(userJSON2.get("username").asText()))) {
            if (cookielist.containsKey(userinDB.username())) {
                return Response.ok().withPayload(cookielist.get(userinDB.username()));
            }
            else {
                Integer cookieid = (int) (Math.random() * 9999999);
                cookielist.put(userinDB.username(), cookieid);
                //TODO create cookie
                return Response.ok().withPayload(cookieid);
            }
        }
        else
            return Response.forStatus(Status.NOT_FOUND);
    }
    // getUser - returns a user object, given a specific username

    @VisibleForTesting
    User getUser(final RequestContext requestContext) {
        return store.getUser(requestContext.pathArgs().get("username"));
    }

    /**Create User method
     * Serialize request to JSON
     * Check that all REQUIRED fields are filled
     * Check that username does not already exist in database
     * Create user (not automatically logged in)
     */
    @VisibleForTesting
    public Boolean createUser(RequestContext requestContext) {
        if (!requestContext.request().payload().isPresent()) {
            return false;
        }

        JsonNode userJSON;
        User newUser = null;
        try {
            newUser = objectmapper.readValue(requestContext.request().payload().get().toByteArray(), User.class);   // Convert payload to user object
            userJSON = objectmapper.readTree(requestContext.request().payload().get().utf8());

            // Check if the username entered for registration already exists (getUser will return user info from db)
            if (store.getUser(userJSON.get("username").asText()) != null) {
                throw new IOException("Failed to create user: Username already exists.");
            }

            newUser = new UserBuilder()    // Create user
                    .username(userJSON.get("username").asText())    // Registration fields
                    .password(userJSON.get("password").asText())
                    .displayName(userJSON.get("displayName").asText())
                    // .aboutMe(userJSON.get("aboutMe").asText())   Add aboutMe to database table

                    .maxTravelDistance(userJSON.get("maxTravelDistance").asInt())
                    .longitude(userJSON.get("longitude").asDouble())
                    .latitude(userJSON.get("latitude").asDouble())
                    .build();
        }
        catch (IOException err) {
            System.out.println(err);
        }
        return store.createUser(newUser);
    }

    // userLogout - removes user's cookie ID from the database
    @VisibleForTesting
    public Response<ByteString> userLogout(RequestContext requestContext) {
        // cookielist.remove(Integer.valueOf(requestContext.pathArgs().get("id"))); /TODO create way to remove cookie from storage
        return Response.ok();
    }


    // Middleware for JSON serialization and routing
    private <T> Middleware<AsyncHandler<T>, AsyncHandler<Response<ByteString>>> jsonMiddleware() {
        return JsonSerializerMiddlewares.<T>jsonSerialize(objectmapper.writer())
                .and(Middlewares::httpPayloadSemantics)
                .and(responseAsyncHandler -> requestContext ->
                        responseAsyncHandler.invoke(requestContext)
                                .thenApply(response -> response.withHeader("Access-Control-Allow-Origin", "*")));
    }
}

//TODO: Implement user session handling.
