package org.example.devkorchat.Chat;

import org.example.devkorchat.User.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository {
    Optional<ChatRoomEntity> findByRoomNumber(int roomNumber);
    Optional<ChatRoomEntity> findByUserandUser2(UserEntity user, UserEntity user2);

}
