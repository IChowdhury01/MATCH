package com.store;

import com.model.User;

public interface UserStore {

    User getUser(String username);

}
