package com.handlers;

import com.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.norberg.automatter.jackson.AutoMatterModule;
import com.spotify.apollo.Response;
import com.spotify.apollo.Request;
import com.spotify.apollo.RequestContext;
import com.store.UserStore;
import com.store.GroupStore;
import okio.ByteString;
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
        when(store.getUser(String.valueOf(testuser.username()))).thenReturn(testuser);
        Response<User> actualresponse = tur.getUser(ctx_test);
        assertEquals(testuser,actualresponse.payload().get());
    }

    @Test
    public void createUser() throws Exception {
        when(requesttest.payload()).thenReturn(Optional.of(ByteString.of(rom.writeValueAsBytes(testuser))));
        when(objectmapper.readTree(Optional
                                    .of(ByteString.of(rom.writeValueAsBytes(testuser))).get.utf8()))
            .thenReturn(rom.readTree(Optional
                                     .of(ByteString.of(rom.writeValueAsBytes(testuser))).get().utf8()));
        when(store.createUser(testuser)).thenReturn(true);
        Response<ByteString> actualresponse = tur.createUser(ctx_test);
        assertEquals(200,actualresponse.status().code());
    }

    @Test
    public void userLogin() throws Exception {
        when(store.getUser(testuser.username()))).thenReturn(testuser);
        when(request_test.payload())
            .thenReturn(Optional
                        .of(ByteString
                            .of(rom
                                .writeValueAsBytes(testuser))));
        when(objectmapper.readTree(Optional
                                   .of(ByteString.of(rom.writeValueAsBytes(testuser))).get().utf8()))
            .thenReturn(rom.readTree(Optional
                                     .of(ByteString.of(rom.writeValueAsBytes(testuser))).get().utf8()));
        Response<Integer> actualresponse = tur.userLogin(ctx-Test);
        Integer actualuid = actualresponse.payload().get();
        Integer expecteduid = new Random(1000).nextInt(2048);
        assertEquals(expecteduid,actualuid);
    }
    
}
