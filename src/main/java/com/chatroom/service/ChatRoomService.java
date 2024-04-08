package com.chatroom.service;

import com.chatroom.model.ChatRoomMessage;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ChatRoomService {

    void sendMessage(String message);
    List<ChatRoomMessage> getMessagesByUserName(String userName);
    List<ChatRoomMessage> getAllMessages();
    void deleteMessage(Long id);

}
