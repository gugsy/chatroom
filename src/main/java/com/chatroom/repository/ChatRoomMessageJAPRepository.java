package com.chatroom.repository;

import com.chatroom.model.ChatRoomMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomMessageJAPRepository extends JpaRepository<ChatRoomMessage, Long> {
}
