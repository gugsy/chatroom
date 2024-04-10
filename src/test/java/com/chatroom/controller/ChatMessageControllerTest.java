package com.chatroom.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import com.chatroom.model.ChatRoomMessage;
import com.chatroom.service.ChatRoomServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

@ExtendWith(MockitoExtension.class)
 class ChatMessageControllerTest {

    @Mock
    private ChatRoomServiceImpl messagingService;

    @InjectMocks
    private ChatMessageController controller;

    @Test
     void testSendMessage() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("testUser");

        ResponseEntity<String> response = controller.sendMessage("Test message", authentication);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Chat successfully sent by: testUser", response.getBody());
        verify(messagingService).sendMessage("Test message", authentication);
    }

    @Test
     void testGetMessages() {
        List<ChatRoomMessage> messages = Arrays.asList(new ChatRoomMessage(1L, "Hello world", "Hellow", LocalDateTime.now(), "user1", 1L ), new ChatRoomMessage(1L, "Hello world", "Hellow", LocalDateTime.now(), "user2", 1L));
        when(messagingService.getAllMessages()).thenReturn(messages);

        ResponseEntity<List<ChatRoomMessage>> response = controller.getMessages();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(messages, response.getBody());
        verify(messagingService).getAllMessages();
    }

    @Test
     void testGetMessagesByUsername() {
        String username = "testUser";
        List<ChatRoomMessage> messages = Arrays.asList(new ChatRoomMessage(1L, "Hello world", "Hellow", LocalDateTime.now(), "user1", 1L), new ChatRoomMessage(1L, "Hello world", "Hellow", LocalDateTime.now(), "user2", 1L));
        when(messagingService.getMessagesByUserName(username)).thenReturn(messages);

        ResponseEntity<List<ChatRoomMessage>> response = controller.getMessagesByUsername(username);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(messages, response.getBody());
        verify(messagingService).getMessagesByUserName(username);
    }

    @Test
     void testGetMessagesByUsername_NoMessagesFound() {
        String username = "testUser";
        when(messagingService.getMessagesByUserName(username)).thenReturn(null);

        ResponseEntity<List<ChatRoomMessage>> response = controller.getMessagesByUsername(username);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(messagingService).getMessagesByUserName(username);
    }

    @Test
     void testDeleteMessage() {
        Long messageId = 1L;

        ResponseEntity<String> response = controller.deleteMessage(messageId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("Chat message deleted with id: 1", response.getBody());
        verify(messagingService).deleteMessage(messageId);
    }
}
