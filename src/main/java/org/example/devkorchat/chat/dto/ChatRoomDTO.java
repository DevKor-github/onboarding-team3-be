package org.example.devkorchat.chat.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.devkorchat.chat.chatRoom.ChatRoomEntity;

@Getter
@NoArgsConstructor
public class ChatRoomDTO {
    private int roomNumber;
    private String username;
    private String message;
    private String messageCreatedDate;

    public ChatRoomDTO(ChatRoomEntity chatRoom, String message, String messageCreatedDate){
        this.roomNumber = chatRoom.getRoomNumber();
        this.username = chatRoom.getUser2().getUsername();
        this.message = message;
        this.messageCreatedDate = messageCreatedDate;
    }
}
