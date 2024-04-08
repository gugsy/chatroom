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
@RequestMapping("/chat-room-api/v1/chat")
public class MessagingController {

    private static final Logger logger = LoggerFactory.getLogger(MessagingController.class);

    private final ChatRoomServiceImpl messagingService;

    @Autowired
    public MessagingController(ChatRoomServiceImpl messagingService) {
        this.messagingService = messagingService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody String message, Authentication authentication) {
        messagingService.sendMessage(message, authentication);
        logger.info("Message sent by user: {}", authentication.getName());
        return ResponseEntity.ok("Message sent!");
    }

    @GetMapping("/getAllChats")
    public ResponseEntity<List<ChatRoomMessage>> getMessages() {
        List<ChatRoomMessage> messages = messagingService.getAllMessages();
        logger.info("Retrieved all messages");
        return ResponseEntity.ok(messages);
    }

    @GetMapping("getChatsByUsername/{username}")
    public ResponseEntity<List<ChatRoomMessage>> getMessagesByUsername(@PathVariable String username) {
        Optional<List<ChatRoomMessage>> optionalMessages = Optional.ofNullable(messagingService.getMessagesByUserName(username));

        if (optionalMessages.isPresent() && !optionalMessages.get().isEmpty()) {
            return ResponseEntity.ok(optionalMessages.get());
        } else {
            logger.warn("No messages found for user: {}", username);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMessage(@PathVariable Long id) {
        messagingService.deleteMessage(id);
        logger.info("Deleted message with id: {}", id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Deleted message with id: " + id);
    }
}
