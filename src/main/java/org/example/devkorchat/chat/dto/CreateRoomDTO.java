package org.example.devkorchat.chat.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.devkorchat.chat.chatJoin.ChatJoinEntity;
import org.example.devkorchat.user.UserEntity;

import java.time.LocalDateTime;
@NoArgsConstructor
@Getter
@Setter
public class CreateRoomDTO {
    private String username;

}
