package com.chatroom.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatRoomConfiguration {

    @Bean
    public ChatRoomId ChatRoomId() {
        return new ChatRoomId();
    }
}
