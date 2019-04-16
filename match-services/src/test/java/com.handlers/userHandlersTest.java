package com.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.User;
import com.model.UserBuilder;
import com.spotify.apollo.RequestContext;
import com.store.UserStore;
import io.norberg.automatter.jackson.AutoMatterModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

// TODO Implement user handler tests for login, logout, createuser and getuser


@RunWith(MockitoJUnitRunner.class)

public class userHandlersTest {

    @Mock ObjectMapper objectMapper;
    @Mock UserStore userStore;
    @Mock RequestContext requestContext;

    private userHandlers testClass;

    @Before
    public void setup() {
        testClass = new userHandlers(objectMapper, userStore);

        rom = new ObjectMapper().registerModule(new AutoMatterModule());
        tur = new UserResource(object_mapper,store,group_store);
        when(ctx_test.request()).thenReturn(request_test);

        testuser = new UserBuilder()
                .userid(1)
                .username("tester123")
                .userdisplayname("Test")
                .userpassword("test123")
                .userhobbylist("ski,boat,sleep")
                .usermaxtraveldistance("25 miles")
                .userlatitude("25W")
                .userlongitude("80N")
                .build();
        when(ctx_test.pathArgs()).thenReturn(Collections.singletonMap("id",String.valueOf(testuser.uid())));

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
