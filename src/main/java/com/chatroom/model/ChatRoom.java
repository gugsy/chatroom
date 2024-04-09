package com.chatroom.model;

import com.chatroom.service.ChatRoomServiceImpl;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Entity
@Data
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public static class ChatRoomHandler implements WebSocketHandler {

        private final ChatRoomServiceImpl chatRoomService;

        private static final Logger log = LoggerFactory.getLogger(ChatRoomHandler.class);

        public ChatRoomHandler(ChatRoomServiceImpl chatRoomService) {
            this.chatRoomService = chatRoomService;
        }

        @Override
        public void afterConnectionEstablished(WebSocketSession session) throws IOException {
            String username = Objects.requireNonNull(session.getPrincipal()).getName();
            session.sendMessage(new TextMessage(username.toUpperCase() +" ,you have successfully connected to our Chatroom" ));

            List<ChatRoomMessage> chatRoomMessages = chatRoomService.getMessagesByUserName(username.toUpperCase());

            if (chatRoomMessages == null) {
                session.sendMessage(new TextMessage(username.toUpperCase() +" ,you currently do not have any previous chats in our Chatroom" ));
            } else {
                for (ChatRoomMessage chatRoomMessage : chatRoomMessages) {
                    session.sendMessage(new TextMessage(chatRoomMessage.getMessage()));
                }
            }
            log.info("User connected to chat: {}", session.getId());
        }

        @Override
        public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
            String username = Objects.requireNonNull(session.getPrincipal()).getName();
            if (message instanceof TextMessage textMessage) {
                String chatMessage = textMessage.getPayload();
                chatRoomService.sendMessage(chatMessage, session);
                log.info("Received message from user {}: {}", username, chatMessage);
                session.sendMessage(new TextMessage(username.toUpperCase()+ ", message was received: " + chatMessage));
            } else {
                log.warn("Received unsupported message type from user {}", session.getId());
                session.close(CloseStatus.NOT_ACCEPTABLE.withReason("Unsupported message type"));
            }
        }


        @Override
        public void handleTransportError(WebSocketSession session, Throwable exception) throws IOException {
            String username = Objects.requireNonNull(session.getPrincipal()).getName();
            session.sendMessage(new TextMessage(username.toUpperCase()+ ", your session has been terminated due to an error "));
            log.error("Error occurred for user {}: {}", session.getId(), exception.getMessage());
        }

        @Override
        public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws IOException {
            log.info("User disconnected from chat: {} with status: {}", session.getId(), closeStatus.getCode());
        }


        @Override
        public boolean supportsPartialMessages() {
            return false;
        }
    }
}
