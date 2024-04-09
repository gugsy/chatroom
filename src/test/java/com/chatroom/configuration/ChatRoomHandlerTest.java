package com.chatroom.configuration;

import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Collections;

import com.chatroom.model.ChatRoom;
import com.chatroom.service.ChatRoomServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

@ExtendWith(MockitoExtension.class)
public class ChatRoomHandlerTest {

    @Mock
    private ChatRoomServiceImpl chatRoomService;

    @InjectMocks
    private ChatRoom.ChatRoomHandler chatRoomHandler;

    private WebSocketSession mockSession;

    @BeforeEach
    void setUp() {
        mockSession = mock(WebSocketSession.class);
        Authentication auth = new TestingAuthenticationToken("testUser", "testCredentials");
        when(mockSession.getPrincipal()).thenReturn(auth);
    }

/*    @Test
    void afterConnectionEstablished_shouldSendMessageAndRetrieveMessages() throws IOException {
        // Arrange
        String username = "TESTUSER";
        when(chatRoomService.getMessagesByUserName(username)).thenReturn(Collections.emptyList());

        // Act
        chatRoomHandler.afterConnectionEstablished(mockSession);

        // Assert
        verify(mockSession).sendMessage(new TextMessage("TESTUSER ,you have successfully connected to our Chatroom"));
        verify(chatRoomService).getMessagesByUserName(username);
        verify(mockSession).sendMessage(new TextMessage("TESTUSER ,you currently do not have any previous chats in our Chatroom"));
    }*/

    @Test
    void handleMessage_withTextMessage_shouldSendMessageAndLog() throws Exception {
        // Arrange
        String message = "Hello";
        WebSocketMessage<String> textMessage = new TextMessage(message);

        // Act
        chatRoomHandler.handleMessage(mockSession, textMessage);

        // Assert
        verify(chatRoomService).sendMessage(message, mockSession);
        verify(mockSession).sendMessage(new TextMessage("TESTUSER, message was received: " + message));
    }

    @Test
    void handleMessage_withUnsupportedMessage_shouldCloseSession() throws Exception {
        // Arrange
        WebSocketMessage<String> unsupportedMessage = mock(WebSocketMessage.class);

        // Act
        chatRoomHandler.handleMessage(mockSession, unsupportedMessage);

        // Assert
        verify(mockSession).close(CloseStatus.NOT_ACCEPTABLE.withReason("Unsupported message type"));
    }

    @Test
    void handleTransportError_shouldSendMessageAndLogError() throws IOException {
        // Arrange
        Throwable exception = new RuntimeException("Test error");

        // Act
        chatRoomHandler.handleTransportError(mockSession, exception);

        // Assert
        verify(mockSession).sendMessage(new TextMessage("TESTUSER, your session has been terminated due to an error "));
    }
}
