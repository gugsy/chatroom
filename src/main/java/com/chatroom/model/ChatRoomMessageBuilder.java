package com.chatroom.model;

import java.time.LocalDateTime;

public class ChatRoomMessageBuilder {
    public static class Builder {
        private String message;
        private LocalDateTime timestamp;
        private Long randomChatRoomId;
        private String userName;

        public Builder message(String message) {
            if (message == null || message.isEmpty()) {
                throw new IllegalArgumentException("Message cannot be null or empty");
            }
            this.message = message;
            return this;
        }

        public Builder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder chatRoomId(Long chatRoomId) {
            if (chatRoomId == null) {
                throw new IllegalArgumentException("Chat room ID cannot be null");
            }
            this.randomChatRoomId = chatRoomId;
            return this;
        }

        public Builder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public ChatRoomMessage build() {
            if (message == null || message.isEmpty()) {
                throw new IllegalStateException("Message must be set");
            }
            if (randomChatRoomId == null) {
                throw new IllegalStateException("Chat room ID must be set");
            }
            ChatRoomMessage chatMessage = new ChatRoomMessage();
            chatMessage.setMessage(this.message);
            chatMessage.setTimestamp(this.timestamp);
            chatMessage.setChatRoomId(this.randomChatRoomId);
            chatMessage.setUsername(this.userName);
            return chatMessage;
        }
    }
}
