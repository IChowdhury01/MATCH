package com.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotify.apollo.RequestContext;
import com.store.UserStore;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)

public class userHandlersTest {

    @Mock
    ObjectMapper objectMapper;
    @Mock
    UserStore userStore;
    @Mock
    RequestContext requestContext;

    private userHandlers testClass;

    @Before
    public void setup() {
        testClass = new userHandlers(objectMapper, userStore);
    }

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void getUser() {
    }

    @Test
    public void createUser() throws Exception {
    }

    @Test
    public void userLogin() {
    }
}
