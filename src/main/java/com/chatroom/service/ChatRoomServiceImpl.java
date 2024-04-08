package com.chatroom.service;

import com.chatroom.config.ChatRoomId;
import com.chatroom.model.ChatRoomMessage;
import com.chatroom.model.ChatRoomMessageBuilder;
import com.chatroom.repository.ChatRoomMessageJAPRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatRoomServiceImpl implements ChatRoomService{
    @Autowired
    ChatRoomMessageJAPRepository chatRoomMessageJAPRepository;
    private final ChatRoomId chatRoomId;

    @Autowired
    public ChatRoomServiceImpl(ChatRoomId chatRoomId) {
        this.chatRoomId = chatRoomId;
    }


    @Override
    public void sendMessage(String message) {
        ChatRoomMessage message1 = new ChatRoomMessageBuilder.Builder()
                .message(message)
                .timestamp(LocalDateTime.now())
                .userName("user")
                .chatRoomId(chatRoomId.getChatRoomId())
                .build();
        chatRoomMessageJAPRepository.save(message1);

    }

    @Override
    public List<ChatRoomMessage> getMessagesByUserName(String userName) {
        return null;
    }

    @Override
    public List<ChatRoomMessage> getAllMessages() {
        return null;
    }

    @Override
    public void deleteMessage(Long id) {

    }
}
