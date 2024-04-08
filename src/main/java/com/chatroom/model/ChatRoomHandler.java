package com.chatroom.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.*;

public class ChatRoomHandler implements WebSocketHandler {

    private static final Logger log = LoggerFactory.getLogger(ChatRoomHandler.class);

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        //get all chat messages from database

        log.info("User connected to chat: {}", session.getId());
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        if (message instanceof TextMessage textMessage) {
            String chatMessage = textMessage.getPayload();
            //save chat message to database
            log.info("Received message from user {}: {}", session.getId(), chatMessage);
            session.sendMessage(new TextMessage("Your message was received: " + chatMessage));
        } else {
            log.warn("Received unsupported message type from user {}", session.getId());
            session.close(CloseStatus.NOT_ACCEPTABLE.withReason("Unsupported message type"));
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        log.error("Error occurred for user {}: {}", session.getId(), exception.getMessage());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
        log.info("User disconnected from chat: {} with status: {}", session.getId(), closeStatus.getCode());
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
