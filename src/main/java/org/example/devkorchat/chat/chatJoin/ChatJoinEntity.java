package org.example.devkorchat.chat.chatJoin;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.devkorchat.chat.chatRoom.ChatRoomEntity;
import org.example.devkorchat.user.UserEntity;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "chat_join")
public class ChatJoinEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roomNumber; //id = room number

    @ManyToOne
    @JoinColumn(name="USER")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name="CHAT_ROOM")
    private ChatRoomEntity chatRoom;

    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    private LocalDateTime updatedAt;

    public ChatJoinEntity(UserEntity user, ChatRoomEntity chatRoom){
        this.user = user;
        this.chatRoom = chatRoom;
    }

}
