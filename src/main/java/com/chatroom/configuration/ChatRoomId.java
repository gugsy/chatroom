package com.chatroom.configuration;

import java.util.Random;

public class ChatRoomId {
    private final Long chatRoomId;

    public ChatRoomId() {
        this.chatRoomId = generateRandomId();
    }

    private Long generateRandomId() {
        return (long) (new Random().nextInt(9000));
    }
    // TODO see if I can use Lombok instead for this getter
    public Long getChatRoomId() {
        return chatRoomId;
    }
}
