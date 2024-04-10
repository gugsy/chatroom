package com.chatroom.configuration;

import java.util.Random;

public class ChatRoomId {
    private final Long randomChatRoomId;

    public ChatRoomId() {
        this.randomChatRoomId = generateRandomId();
    }

    private Long generateRandomId() {
        return (long) (new Random().nextInt(9000));
    }
    public Long getChatRoomId() {
        return randomChatRoomId;
    }
}
