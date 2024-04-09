package com.chatroom.configuration;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
public class WebSocketHandShakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
        HttpHeaders headers = request.getHeaders();

        // Check if the Authorization header is present
        if (headers.containsKey(HttpHeaders.AUTHORIZATION)) {
            // Get the value of the Authorization header
            String authHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);

            // Extract the credentials from the Authorization header
            String credentials = authHeader.substring("Basic".length()).trim();

            // Decode the Base64-encoded credentials
            byte[] decodedCredentials = Base64Utils.decodeFromString(credentials);
            String decodedCredentialsString = new String(decodedCredentials, StandardCharsets.UTF_8);

            // Split the decoded credentials into username and password
            String[] splitCredentials = decodedCredentialsString.split(":");
            String username = splitCredentials[0];
            String password = splitCredentials[1];

            // Perform authentication logic
            if ("admin".equals(username) && "admin".equals(password)) {
                // Authentication successful
                attributes.put("username", username);
                return true;
            }
        }

        // Authentication failed
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                               Exception ex) {
        String username = (String) request.getPrincipal().getName();
        System.out.println("User authenticated successfully: " + username);
    }
}
