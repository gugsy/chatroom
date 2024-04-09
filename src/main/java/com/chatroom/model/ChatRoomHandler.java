package com.chatroom.model;

import com.chatroom.service.ChatRoomServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class ChatRoomHandler implements WebSocketHandler {

    private final ChatRoomServiceImpl chatRoomService;

    private static final Logger log = LoggerFactory.getLogger(ChatRoomHandler.class);

    public ChatRoomHandler(ChatRoomServiceImpl chatRoomService) {
        this.chatRoomService = chatRoomService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        //add the username
        String username = Objects.requireNonNull(session.getPrincipal()).getName();
        session.sendMessage(new TextMessage("You have successfully connected to our Chatroom: " + username.toUpperCase()));

        List<ChatRoomMessage> chatRoomMessages = chatRoomService.getMessagesByUserName(username.toUpperCase());

        if (chatRoomMessages == null) {
            session.sendMessage(new TextMessage("No messages found for user: " + username.toUpperCase()));
        } else {
            for (ChatRoomMessage chatRoomMessage : chatRoomMessages) {
                session.sendMessage(new TextMessage(chatRoomMessage.getMessage()));
            }
        }
        session.sendMessage(new TextMessage("You have successfully connected to our Chatroom: " + username.toUpperCase()));
        log.info("User connected to chat: {}", session.getId());
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {

        if (message instanceof TextMessage textMessage) {
            String chatMessage = textMessage.getPayload();
            chatRoomService.sendMessage(chatMessage, session);
            log.info("Received message from user {}: {}", session.getId(), chatMessage);
            // add username in web socket session
            session.sendMessage(new TextMessage("Your message was received: " + chatMessage));
        } else {
            log.warn("Received unsupported message type from user {}", session.getId());
            session.close(CloseStatus.NOT_ACCEPTABLE.withReason("Unsupported message type"));
        }
    }


    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws IOException {
        String username = Objects.requireNonNull(session.getPrincipal()).getName();
        session.sendMessage(new TextMessage("Your session has been terminated due to an error: " + username.toUpperCase()));
        log.error("Error occurred for user {}: {}", session.getId(), exception.getMessage());
    }

    //logic to send farewell message to user
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws IOException {
  /*      String username = Objects.requireNonNull(session.getPrincipal()).getName();

        // Use a separate thread to delay sending the farewell message
        new Thread(() -> {
            try {
                Thread.sleep(5000); // Delay for 5 seconds (5000 milliseconds)
                session.sendMessage(new TextMessage("Your session has been closed. Thank you for chatting, " + username.toUpperCase()));
            } catch (InterruptedException | IOException e) {
                log.error("Error while sending farewell message: {}", e.getMessage());
            }
        }).start();
*/
        log.info("User disconnected from chat: {} with status: {}", session.getId(), closeStatus.getCode());
    }


    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
