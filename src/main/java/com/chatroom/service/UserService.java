package com.chatroom.service;

import com.chatroom.model.User;

public interface UserService {

    User save(User user);
    User findByUsername(String username);
}
