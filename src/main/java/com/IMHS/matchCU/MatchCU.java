package com.IMHS.matchCU;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotify.apollo.Environment;
import com.spotify.apollo.httpservice.HttpService;
import com.spotify.apollo.httpservice.LoadingException;
import com.spotify.apollo.route.Route;
import io.norberg.automatter.jackson.AutoMatterModule;

// This program is intended to boot/initialize our web application, using Apollo [Incomplete]

public final class MatchCU {

    public static void main(String[] args) throws LoadingException {
        HttpService.boot(MatchCU::init, "match-services", args);
    }

    static void init(Environment environment) {
        ObjectMapper object_mapper = new ObjectMapper().registerModule(new AutoMatterModule());
        /* instantiate the resources before initializing the routes */
        environment.routingEngine()
                .registerAutoRoute(Route.sync("GET", "/ping", ctx -> "pong\n"))
                .registerAutoRoute(Route.sync("GET", "/", ctx -> "Welcome to MATCH!\n" ));
    }

}