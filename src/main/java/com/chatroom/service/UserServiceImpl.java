package com.chatroom.service;

import com.chatroom.model.User;
import com.chatroom.repository.UserJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserJPARepository userJPARepository;
    @Override
    public User save(User user) {
        return userJPARepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userJPARepository.findByUsername(username);
    }
}
