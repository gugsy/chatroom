package com.chatroom.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat-api/v2")
public class UserLoginController {

    private static final Logger logger = LoggerFactory.getLogger(UserLoginController.class);

    @PostMapping("/login")
    public ResponseEntity<String> login(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            logger.info(authentication.getName()," you have been logged in: {}");
            return ResponseEntity.ok("You have successfully logged in: " + authentication.getName());
        } else {
            logger.warn("Login failed");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
        }
    }
}
