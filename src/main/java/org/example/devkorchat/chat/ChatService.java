package org.example.devkorchat.chat;

import org.example.devkorchat.chat.chatJoin.ChatJoinEntity;
import org.example.devkorchat.chat.chatRoom.ChatRoomEntity;
import org.example.devkorchat.chat.chatRoom.ChatRoomRepository;
import org.example.devkorchat.chat.chatJoin.ChatJoinRepository;
import org.example.devkorchat.chat.dto.ChatDTO;
import org.example.devkorchat.chat.dto.ChatRoomDTO;
import org.example.devkorchat.user.UserEntity;
import org.example.devkorchat.user.UserRepository;
import org.springframework.http.ResponseEntity;
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
    private final ChatJoinRepository chatJoinRepository;

    public ChatService(ChatRoomRepository chatRoomRepository, UserRepository userRepository, ChatRepository chatRepository, ChatJoinRepository chatJoinRepository){
        this.chatRoomRepository = chatRoomRepository;
        this.userRepository = userRepository;
        this.chatRepository = chatRepository;
        this.chatJoinRepository = chatJoinRepository;
    }

    public List<ChatRoomDTO> getChatRoomList(int userId) {
        return chatJoinRepository.findByUserId(userId).stream()
                .map(chatJoin -> {

                    //TODO: test displayName
                    String displayName = null;
                    String username = null;
                    String profileURL = null;

                    List<ChatJoinEntity> chatJoinList = this.chatJoinRepository.findByChatRoomRoomNumber(chatJoin.getRoomNumber());
                    for (ChatJoinEntity chatJoinEntity : chatJoinList) {
                        if (chatJoinEntity.getUser().getId() != userId) {
                            displayName = chatJoinEntity.getUser().getNickname();
                            username = chatJoinEntity.getUser().getUsername();
                            profileURL = chatJoinEntity.getUser().getProfileURL();
                            break;
                        }
                    }

                    ChatEntity recentChat = chatRepository.findTopByRoomNumberOrderByCreatedAtDesc(chatJoin.getRoomNumber());
                    
                    if(recentChat != null){
                        return new ChatRoomDTO(chatJoin, displayName, username, profileURL, recentChat.getMessage(), recentChat.getCreatedAt());
                    }
                    else {
                        return new ChatRoomDTO(chatJoin, displayName, username, profileURL, "메시지 없음", null);
                    }
                })
                .collect(Collectors.toList());
    }

    public ChatRoomEntity findChatRoomByRoomNumber(int roomNumber){
        return chatRoomRepository.findByRoomNumber(roomNumber).orElseThrow(
                () -> new IllegalArgumentException("해당 채팅방이 존재하지 않습니다"));
    }


    //일대일 채팅방 생성
    public ChatRoomDTO createRoom(UserEntity user, UserEntity user2){

        List<ChatRoomDTO> chatRoomDtos = getChatRoomList(user.getId());
        for(ChatRoomDTO chatRoom: chatRoomDtos){
            if(user2.getUsername().equals(chatRoom.getDisplayName())){
                return chatRoom;
            }
        }

        ChatRoomEntity chatRoom = new ChatRoomEntity();
        this.chatRoomRepository.save(chatRoom);
        ChatJoinEntity chatJoinUser1 = new ChatJoinEntity(user, chatRoom);
        ChatJoinEntity chatJoinUser2 = new ChatJoinEntity(user2, chatRoom);
        this.chatJoinRepository.save(chatJoinUser1);
        this.chatJoinRepository.save(chatJoinUser2);

        return new ChatRoomDTO(chatJoinUser2, user2.getNickname(), user2.getUsername(), user2.getProfileURL(), "메시지가 없습니다", null);
    }

    public void deleteRoom(int roomNumber){
        ChatRoomEntity chatRoom = this.chatRoomRepository.findByRoomNumber(roomNumber).orElseThrow(
                ()-> new IllegalArgumentException("해당 채팅방이 존재하지 않습니다"));

        List<ChatEntity> deleteChat = this.chatRepository.findByRoomNumber(roomNumber);
        this.chatRepository.deleteAll(deleteChat);

        List<ChatJoinEntity> deleteChatJoin = this.chatJoinRepository.findByChatRoomRoomNumber(roomNumber);
        this.chatJoinRepository.deleteAll(deleteChatJoin);

        this.chatRoomRepository.delete(chatRoom);
    }

    public void saveChat(ChatDTO chatDto) {
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
