package com.chatroom.controller;

import com.chatroom.model.ChatRoomMessage;
import com.chatroom.service.ChatRoomServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/chat-api/v1")
public class ChatMessageController {

    private static final Logger logger = LoggerFactory.getLogger(ChatMessageController.class);

    private final ChatRoomServiceImpl messagingService;

    @Autowired
    public ChatMessageController(ChatRoomServiceImpl messagingService) {
        this.messagingService = messagingService;
    }

    @PostMapping("/chat/send")
    public ResponseEntity<String> sendMessage(@RequestBody String message, Authentication authentication) {
        try {
            messagingService.sendMessage(message, authentication);
            logger.info("Chat sent by user: {}", authentication.getName());
            return ResponseEntity.ok("Chat successfully sent by: " + authentication.getName());
        } catch (Exception e) {
            logger.error("Error sending chat message: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending chat message");
        }
    }

    @GetMapping("/chat/all")
    public ResponseEntity<List<ChatRoomMessage>> getMessages() {
        try {
            List<ChatRoomMessage> messages = messagingService.getAllMessages();
            logger.info("All chat messages retrieved");
            return ResponseEntity.ok(messages);
        } catch (Exception e) {
            logger.error("Error retrieving chat messages: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/chat/{username}")
    public ResponseEntity<List<ChatRoomMessage>> getMessagesByUsername(@PathVariable String username) {
        try {
            Optional<List<ChatRoomMessage>> optionalMessages = Optional.ofNullable(messagingService.getMessagesByUserName(username));

            if (optionalMessages.isPresent() && !optionalMessages.get().isEmpty()) {
                return ResponseEntity.ok(optionalMessages.get());
            } else {
                logger.warn("{} you do not have any messages", username);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            logger.error("Error retrieving messages for user {}: {}", username, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMessage(@PathVariable Long id) {
        try {
            messagingService.deleteMessage(id);
            logger.info("Chat message deleted with id: {}", id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Chat message deleted with id: " + id);
        } catch (Exception e) {
            logger.error("Error deleting chat message with id {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting chat message");
        }
    }
}

