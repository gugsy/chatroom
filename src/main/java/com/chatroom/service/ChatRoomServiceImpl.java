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

@Service
public class ChatRoomServiceImpl implements ChatRoomService{
    ChatRoomMessageJAPRepository chatRoomMessageJAPRepository;
    private final ChatRoomId chatRoomId;

    @Autowired
    public ChatRoomServiceImpl(ChatRoomId chatRoomId, ChatRoomMessageJAPRepository chatRoomMessageJAPRepository) {
        this.chatRoomId = chatRoomId;
        this.chatRoomMessageJAPRepository = chatRoomMessageJAPRepository;
    }


    @Override
    public void sendMessage(String message, WebSocketSession session) {
        ChatRoomMessage message1 = new ChatRoomMessageBuilder.Builder()
                .message(message)
                .timestamp(LocalDateTime.now())
          /*
              TODO: in the event of authentication via the websocket, the username will be retrieved from the session instead of the session id

           */
                .userName(session.getId())
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
