package com;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.handlers.userHandlers;
import com.spotify.apollo.Environment;
import com.spotify.apollo.httpservice.HttpService;
import com.spotify.apollo.httpservice.LoadingException;
import com.spotify.apollo.route.Route;
import com.store.MatchJdbc;
import com.store.UserStore;
import io.norberg.automatter.jackson.AutoMatterModule;

// This is the main class - the entry point of our app

public final class MatchApp {

    /**
     * The main method boots the Apollo HTTP server, following the configuration in
     * the text file "match-services.conf", which specifies the server port used and mySQL
     * login info. If an error occurs, the program will throw a loading exception.
     */

    public static void main(String[] args) throws LoadingException {
        HttpService.boot(MatchApp::init, "match-services", args);
    }

    /**
     * The init method instantiates our resource classes (userHandler and userStore) and
     * initializes the routes used in the app.
     */

    public static void init(final Environment environment) {

        // Instantiate ObjectMapper class used for serializing to JSON
        ObjectMapper objectmapper = new ObjectMapper().registerModule(new AutoMatterModule());

        // Instantiate stores
        UserStore userstore = new MatchJdbc(environment.config());

        // Instantiate resources/handlers
        userHandlers userhandler = new userHandlers(objectmapper,userstore);

        // Initialize routes
        environment
                .routingEngine()
                .registerAutoRoute(Route.sync("GET", "/ping", ctx -> "pong\n")) // Default route
                ;//.registerRoutes(userhandler.routes());  // Routes defined in userHandlers class

        userhandler.initRoutes(environment);
    }

}