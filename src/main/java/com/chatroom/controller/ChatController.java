
package com.chatroom.controller;

import com.chatroom.model.ChatRoomMessage;
import com.chatroom.service.ChatRoomServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Configuration
@Controller
public class ChatController {

    private final ChatRoomServiceImpl chatRoomService;
    @Autowired
    ChatController(ChatRoomServiceImpl chatRoomService) {
        this.chatRoomService = chatRoomService;
    }


    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")


    public ChatRoomMessage sendMessage(
            @Payload ChatRoomMessage chatMessage
    ) {
        System.out.println("chatMessage: "+chatMessage.getMessage());
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatRoomMessage addUser(
            @Payload ChatRoomMessage chatMessage,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getUsername());
        return chatMessage;
    }

}
