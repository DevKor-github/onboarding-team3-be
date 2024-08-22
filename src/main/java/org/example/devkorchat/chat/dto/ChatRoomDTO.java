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
    private String displayName;
    private String username;
    private String profileURL;
    private String message;
    private LocalDateTime messageCreatedDate;

    public ChatRoomDTO(ChatJoinEntity chatJoin, String displayName, String username, String profileURL, String message, LocalDateTime messageCreatedDate){
        this.roomNumber = chatJoin.getRoomNumber();
        this.displayName = displayName;
        this.username = username;
        this.profileURL = profileURL;
        this.message = message;
        this.messageCreatedDate = messageCreatedDate;
    }
}
