package com.chatroom.service;

import com.chatroom.model.ChatRoomMessage;
import org.springframework.security.core.Authentication;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

public interface ChatRoomService {

    void sendMessage(String message, WebSocketSession session);
    void sendMessage(String message, Authentication authentication);
    List<ChatRoomMessage> getMessagesByUserName(String userName);
    List<ChatRoomMessage> getAllMessages();
    void deleteMessage(Long id);

}
