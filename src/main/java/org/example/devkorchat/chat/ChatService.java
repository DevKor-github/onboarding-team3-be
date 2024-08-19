package org.example.devkorchat.chat;

import org.example.devkorchat.chat.chatRoom.ChatRoomEntity;
import org.example.devkorchat.chat.chatRoom.ChatRoomRepository;
import org.example.devkorchat.chat.dto.ChatDTO;
import org.example.devkorchat.chat.dto.ChatRoomDTO;
import org.example.devkorchat.user.UserEntity;
import org.example.devkorchat.user.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;

    public ChatService(ChatRoomRepository chatRoomRepository, UserRepository userRepository, ChatRepository chatRepository){
        this.chatRoomRepository = chatRoomRepository;
        this.userRepository = userRepository;
        this.chatRepository = chatRepository;
    }

    public List<ChatRoomDTO> getChatRoomList() {
        return chatRoomRepository.findAll().stream()
                .map(chatRoom -> {
                    ChatEntity recentChat = chatRepository.findTopByRoomNumberOrderByCreatedAtDesc(chatRoom.getRoomNumber());

                    if(recentChat != null){
                        return new ChatRoomDTO(chatRoom, recentChat.getMessage(), recentChat.getCreatedAt());
                    }
                    else {
                        return new ChatRoomDTO(chatRoom, "메시지 없음", null);
                    }
                })
                .collect(Collectors.toList());
    }

    public ChatRoomEntity findChatRoomByRoomNumber(int roomNumber){
        return chatRoomRepository.findByRoomNumber(roomNumber).orElseThrow(
                () -> new IllegalArgumentException("해당 채팅방이 존재하지 않습니다"));
    }

    public ChatRoomEntity createRoom(UserEntity user, UserEntity user2){
        ChatRoomEntity chatRoom = new ChatRoomEntity(user, user2);
        return this.chatRoomRepository.save(chatRoom);
    }

    public void deleteRoom(int roomNumber){
        ChatRoomEntity chatRoom = this.chatRoomRepository.findByRoomNumber(roomNumber).orElseThrow(
                ()-> new IllegalArgumentException("해당 채팅방이 존재하지 않습니다"));

        List<ChatEntity> deleteChat = this.chatRepository.findByRoomNumber(roomNumber);
        this.chatRepository.deleteAll(deleteChat);
        this.chatRoomRepository.delete(chatRoom);
    }

    public void save(ChatDTO chatDto) {
        ChatEntity chat = chatDto.toEntity();
        chatRepository.save(chat);
    }

    public List<ChatDTO> getChatsByRoomNumber(int roomNumber) {
        List<ChatEntity> chat = this.chatRepository.findByRoomNumber(roomNumber);

        List<ChatDTO> chatDtoList = new ArrayList<>();
        for(ChatEntity chatInfo: chat) {
            ChatDTO chatDto = new ChatDTO(chatInfo.getUsername(), chatInfo.getMessage(), chatInfo.getRoomNumber());
            chatDtoList.add(chatDto);
        }
        return chatDtoList;
    }

}
