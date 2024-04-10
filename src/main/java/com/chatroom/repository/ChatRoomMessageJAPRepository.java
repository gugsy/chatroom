package com.chatroom.repository;

import com.chatroom.model.ChatRoomMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomMessageJAPRepository extends JpaRepository<ChatRoomMessage, Long> {
    List<ChatRoomMessage> findByUsername(String username);
}
