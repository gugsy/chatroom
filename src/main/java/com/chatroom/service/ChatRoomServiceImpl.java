package com.chatroom.service;

import com.chatroom.configuration.ChatRoomId;
import com.chatroom.model.ChatRoomMessage;
import com.chatroom.model.ChatRoomMessageBuilder;
import com.chatroom.repository.ChatRoomMessageJAPRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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
    public void sendMessage(String message, WebSocketSession session) {
        ChatRoomMessage message1 = new ChatRoomMessageBuilder.Builder()
                .message(message)
                .timestamp(LocalDateTime.now())
                .userName(Objects.requireNonNull(session.getPrincipal()).getName())
                .chatRoomId(chatRoomId.getChatRoomId())
                .build();
        chatRoomMessageJAPRepository.save(message1);

    }

    @Override
    public void sendMessage(String message, Authentication authentication) {
        ChatRoomMessage message1 = new ChatRoomMessageBuilder.Builder()
                .message(message)
                .timestamp(LocalDateTime.now())
                .userName(authentication.getName())
                .chatRoomId(chatRoomId.getChatRoomId())
                .build();
        chatRoomMessageJAPRepository.save(message1);
    }

    @Override
    public List<ChatRoomMessage> getMessagesByUserName(String username) {

        return chatRoomMessageJAPRepository.findByUsername(username);
    }

    @Override
    public List<ChatRoomMessage> getAllMessages() {
        return chatRoomMessageJAPRepository.findAll();
    }

    @Override
    public void deleteMessage(Long id) {
        chatRoomMessageJAPRepository.deleteById(id);

    }
}
