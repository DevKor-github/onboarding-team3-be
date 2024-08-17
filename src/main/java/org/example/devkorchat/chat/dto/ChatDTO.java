package org.example.devkorchat.chat.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.devkorchat.chat.ChatEntity;

@Getter
@NoArgsConstructor
public class ChatDTO {

    private String username;
    private String message;
    private int roomNumber;

    public ChatEntity toEntity(){
        return ChatEntity.builder()
                .username(username)
                .message(message)
                .roomNumber(roomNumber)
                .build();
    }
}
