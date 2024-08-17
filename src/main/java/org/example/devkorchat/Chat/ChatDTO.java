package org.example.devkorchat.Chat;

import lombok.Getter;
import lombok.NoArgsConstructor;

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
