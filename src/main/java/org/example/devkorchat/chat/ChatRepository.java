package org.example.devkorchat.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<ChatEntity, String> {
    List<ChatEntity> findByRoomNumber(int roomNumber);
    ChatEntity findTopByRoomNumberOrderByCreatedAtDesc(int roomNumber);
}
