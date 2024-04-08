/*

package com.chatroom.controller;

import com.chatroom.model.ChatRoomMessage;
import com.chatroom.service.ChatRoomServiceImpl;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class ChatController {

    private final ChatRoomServiceImpl chatRoomService;

    @Autowired
    public ChatController(ChatRoomServiceImpl chatRoomService) {
        this.chatRoomService = chatRoomService;
    }


    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")*/
/**//*

    public ChatRoomMessage sendMessage(
            @Payload ChatRoomMessage chatMessage
    ) {
        System.out.println("chatMessage: "+chatMessage.getMessage());
        chatRoomService.sendMessage(chatMessage.getMessage());
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
*/
