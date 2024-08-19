package org.example.devkorchat.chat.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.devkorchat.chat.chatJoin.ChatJoinEntity;
import org.example.devkorchat.chat.chatRoom.ChatRoomEntity;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class ChatRoomDTO {
    private int roomNumber;
    private String name;
    private String message;
    private LocalDateTime messageCreatedDate;

    public ChatRoomDTO(ChatJoinEntity chatJoin, String name, String message, LocalDateTime messageCreatedDate){
        this.roomNumber = chatJoin.getRoomNumber();
        this.name = name;
        this.message = message;
        this.messageCreatedDate = messageCreatedDate;
    }
}
