package org.example.devkorchat.chat;
import org.example.devkorchat.chat.chatJoin.ChatJoinEntity;
import org.example.devkorchat.chat.chatJoin.ChatJoinRepository;
import org.example.devkorchat.chat.chatRoom.ChatRoomEntity;
import org.example.devkorchat.chat.dto.ChatRoomDTO;
import org.example.devkorchat.chat.dto.CreateRoomDTO;
import org.example.devkorchat.user.UserEntity;
import org.example.devkorchat.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.http.HttpResponse;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;
    private final UserRepository userRepository;
    private final ChatJoinRepository chatJoinRepository;

    public ChatController(ChatService chatService, UserRepository userRepository, ChatJoinRepository chatJoinRepository) {
        this.chatService = chatService;
        this.userRepository = userRepository;
        this.chatJoinRepository = chatJoinRepository;
    }

    @GetMapping("/list")
    public ResponseEntity<List<ChatRoomDTO>> findChatRoomList(Authentication authentication, Principal principal) {
        UserEntity user = this.userRepository.findByUsername(authentication.getName());
        List<ChatRoomDTO> chatRoomDtos = this.chatService.getChatRoomList(user.getId());
        return ResponseEntity.ok(chatRoomDtos);
        //각각의 채팅방은 ws://localhost:8080/api/chat/{roomNumber}로 연결
    }

    @PostMapping("/createRoom")
    public ResponseEntity<ChatRoomDTO> createChatRoom(@RequestBody CreateRoomDTO createRoomDTO, Authentication authentication, Principal principal){
        String user_chat = createRoomDTO.getUsername();
        if(!this.userRepository.existsByUsername(user_chat)){
            //TODO: return when user not found
            System.out.println(user_chat);
            throw new RuntimeException("유저가 존재하지 않습니다.");
        }

        UserEntity user = this.userRepository.findByUsername(authentication.getName());
        UserEntity other = this.userRepository.findByUsername(user_chat);

        ChatRoomDTO chatRoomDTO = this.chatService.createRoom(user, other);
        return ResponseEntity.ok(chatRoomDTO);
    }

}
