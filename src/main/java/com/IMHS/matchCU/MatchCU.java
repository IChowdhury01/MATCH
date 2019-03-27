package com.IMHS.matchCU;

import com.spotify.apollo.Environment;
import com.spotify.apollo.Response;
import com.spotify.apollo.Status;
import com.spotify.apollo.httpservice.HttpService;
import com.spotify.apollo.httpservice.LoadingException;
import com.spotify.apollo.route.AsyncHandler;
import com.spotify.apollo.route.Middleware;
import com.spotify.apollo.route.SyncHandler;

// This program is intended to boot/initialize our web application, using Apollo [Incomplete]

final class MatchCU {

    public static void main(String[] args) throws LoadingException {
        HttpService.boot(MatchCU::init, "match-services", args);
    }

    static void init(Environment environment) {
    }

    /**
     * A generic middleware that maps uncaught exceptions to error code 418
     */
    static <T> Middleware<SyncHandler<Response<T>>, SyncHandler<Response<T>>> exceptionMiddleware() {
        return handler -> requestContext -> {
            try {
                return handler.invoke(requestContext);
            } catch (RuntimeException e) {
                return Response.forStatus(Status.IM_A_TEAPOT);
            }
        };
    }

    /**
     * Async version of {@link #exceptionMiddleware()}
     */
    static <T> Middleware<SyncHandler<Response<T>>, AsyncHandler<Response<T>>> exceptionHandler() {
        return MatchCU.<T>exceptionMiddleware().and(Middleware::syncToAsync);
    }
}