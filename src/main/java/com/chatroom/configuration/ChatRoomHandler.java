package com.chatroom.configuration;

import com.chatroom.service.ChatRoomServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.*;

import java.io.IOException;

public  class ChatRoomHandler implements WebSocketHandler {

        private final ChatRoomServiceImpl chatRoomService;

        static final Logger log = LoggerFactory.getLogger(ChatRoomHandler.class);

        public ChatRoomHandler(ChatRoomServiceImpl chatRoomService) {
            this.chatRoomService = chatRoomService;
        }

        @Override
        public void afterConnectionEstablished(WebSocketSession session) throws IOException {

            String username = session.getId();
            session.sendMessage(new TextMessage(username.toUpperCase() +" ,you have successfully connected to our Chatroom" ));
            log.info("User connected to chat: {}", session.getId());
        }

        @Override
        public void handleMessage(WebSocketSession session, WebSocketMessage<?> webSocketMessage) throws Exception {
            String username = session.getId();
            if (webSocketMessage instanceof TextMessage textMessage) {
                String chatMessage = textMessage.getPayload();
                chatRoomService.sendMessage(chatMessage, session);
                log.info("Message received from user {}: {}", username, chatMessage);
                session.sendMessage(new TextMessage(username.toUpperCase()+ ", message was received: " + chatMessage));
            } else {
                log.warn("Unsupported message type  received from user {}", session.getId());
                session.close(CloseStatus.NOT_ACCEPTABLE.withReason("Unsupported message type"));
            }
        }


        @Override
        public void handleTransportError(WebSocketSession webSocketSession, Throwable exception) throws IOException {
            String username = webSocketSession.getId();
            webSocketSession.sendMessage(new TextMessage(username.toUpperCase()+ ", your session has been terminated due to an error "));
            log.error(" An error occurred for user {}: {}", webSocketSession.getId(), exception.getMessage());
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
