package com.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotify.apollo.RequestContext;
import com.store.UserStore;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.awt.print.Book;

@RunWith(MockitoJUnitRunner.class)

public class userHandlersTest {

    @Mock ObjectMapper objectMapper;
    @Mock UserStore userStore;
    @Mock RequestContext requestContext;

    private userHandlers testClass;

    @Before
    public void setup() {
        testClass = new userHandlers(objectMapper, userStore);
    }
    @Test
    public void getUser() {
        // Setup vars
        User expecteduser = new UserBuilder()
                .username("Test User 1")
                .password("")
                .displayName("")
                .aboutMe("")

                .build();

        // Mock dependencies and inputs
        // Call test class
        // Assert and verify
    }

    @Test
    public void createUser() throws Exception {
    }

    @Test
    public void userLogin() {

    }
}
