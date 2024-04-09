package com.chatroom.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.chatroom.configuration.ChatRoomId;
import com.chatroom.model.ChatRoomMessage;
import com.chatroom.repository.ChatRoomMessageJAPRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.web.socket.WebSocketSession;

@ExtendWith(MockitoExtension.class)
public class ChatRoomServiceImplTest {

    @Mock
    private ChatRoomMessageJAPRepository chatRoomMessageJAPRepository;

    @Mock
    private ChatRoomId chatRoomId;

    @InjectMocks
    private ChatRoomServiceImpl chatRoomService;

    @BeforeEach
    void setUp() {
        // Mock behavior for chatRoomId
            MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendMessage_withSession_shouldSaveMessage() {
        // Arrange
        WebSocketSession session = mock(WebSocketSession.class);
        when(session.getPrincipal()).thenReturn(() -> "user1");

        // Act
        chatRoomService.sendMessage("Hello", session);

        // Assert
        verify(chatRoomMessageJAPRepository).save(any(ChatRoomMessage.class));
    }

    @Test
    void sendMessage_withAuthentication_shouldSaveMessage() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("user2");

        // Act
        chatRoomService.sendMessage("Hi", authentication);

        // Assert
        verify(chatRoomMessageJAPRepository).save(any(ChatRoomMessage.class));
    }

    @Test
    void getMessagesByUserName_shouldReturnMessages() {
        // Arrange
        String username = "user1";
        List<ChatRoomMessage> messages = new ArrayList<>();
        messages.add(new ChatRoomMessage(1L, "Hello world", "Hellow", LocalDateTime.now(), "user1", 1L));

        when(chatRoomMessageJAPRepository.findByUsername(username)).thenReturn(messages);

        // Act
        List<ChatRoomMessage> result = chatRoomService.getMessagesByUserName(username);

        // Assert
        assertEquals(messages, result);
    }

    @Test
    void getAllMessages_shouldReturnAllMessages() {
        // Arrange
        List<ChatRoomMessage> messages = new ArrayList<>();
        messages.add(new ChatRoomMessage(1L, "Hello world", "Hellow", LocalDateTime.now(), "user1", 1L));
        messages.add(new ChatRoomMessage(1L, "Hello world", "Hellow", LocalDateTime.now(), "user1", 1L));
        when(chatRoomMessageJAPRepository.findAll()).thenReturn(messages);

        // Act
        List<ChatRoomMessage> result = chatRoomService.getAllMessages();

        // Assert
        assertEquals(messages, result);
    }

    @Test
    void deleteMessage_shouldDeleteMessage() {
        // Arrange
        Long messageId = 1L;
        doNothing().when(chatRoomMessageJAPRepository).deleteById(messageId);

        // Act
        chatRoomService.deleteMessage(messageId);

        // Assert
        verify(chatRoomMessageJAPRepository).deleteById(messageId);
    }
}
