package com.chatroom.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

 class UserLoginControllerTest {

    private final UserLoginController controller = new UserLoginController();

    @Test
     void testLoginSuccess() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getName()).thenReturn("testUser");

        ResponseEntity<String> response = controller.login(authentication);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("You have successfully logged in: testUser", response.getBody());
    }

    @Test
    public void testLoginFailure() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(false);

        ResponseEntity<String> response = controller.login(authentication);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Login failed", response.getBody());
    }
}
