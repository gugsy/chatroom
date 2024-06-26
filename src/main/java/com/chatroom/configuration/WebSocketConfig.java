package com.chatroom.configuration;

import com.chatroom.service.ChatRoomServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final ChatRoomServiceImpl chatRoomService;
    public WebSocketConfig(ChatRoomServiceImpl chatRoomService) {
        this.chatRoomService = chatRoomService;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler(), "/websocket-chatroom")
                .setAllowedOrigins("*");

        /* TODO: To enable the websocket to authenticate the user, add the following code snippet:
          .addInterceptors(new WebSocketHandShakeInterceptor());
        **/

    }
    @Bean
    public WebSocketHandler webSocketHandler() {
        return new ChatRoomHandler(chatRoomService);
    }

}

