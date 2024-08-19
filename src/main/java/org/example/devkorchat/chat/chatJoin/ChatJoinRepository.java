package org.example.devkorchat.chat.chatJoin;

import org.example.devkorchat.chat.chatRoom.ChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatJoinRepository extends JpaRepository<ChatJoinEntity, Long> {

    List<ChatJoinEntity> findByChatRoomRoomNumber(int roomNumber);
    List<ChatJoinEntity> findByUserId(int userId);
}
