package com.store;

import com.model.User;

public interface userStore {

    User getUser(String username);

}