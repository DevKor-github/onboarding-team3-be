package org.example.devkorchat.chat;
import org.example.devkorchat.chat.chatJoin.ChatJoinEntity;
import org.example.devkorchat.chat.chatJoin.ChatJoinRepository;
import org.example.devkorchat.chat.chatRoom.ChatRoomEntity;
import org.example.devkorchat.chat.chatRoom.ChatRoomRepository;
import org.example.devkorchat.chat.dto.ChatDTO;
import org.example.devkorchat.chat.dto.ChatRoomDTO;
import org.example.devkorchat.chat.dto.CreateRoomDTO;
import org.example.devkorchat.user.UserEntity;
import org.example.devkorchat.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    private final ChatRoomRepository chatRoomRepository;

    public ChatController(ChatService chatService, UserRepository userRepository, ChatJoinRepository chatJoinRepository, ChatRoomRepository chatRoomRepository) {
        this.chatService = chatService;
        this.userRepository = userRepository;
        this.chatJoinRepository = chatJoinRepository;
        this.chatRoomRepository = chatRoomRepository;
    }

    @GetMapping("/list")
    public ResponseEntity<List<ChatRoomDTO>> findChatRoomList(Authentication authentication, Principal principal) {
        UserEntity user = this.userRepository.findByUsername(authentication.getName());
        List<ChatRoomDTO> chatRoomDtos = this.chatService.getChatRoomList(user.getId());
        return ResponseEntity.ok(chatRoomDtos);
        //각각의 채팅방은 http://localhost:8080/api/chat/{roomNumber} (GET)으로 연결
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

    @GetMapping("/{roomNumber}")
    public ResponseEntity<List<ChatDTO>> getChatRoomMessages(@PathVariable int roomNumber) {
        if(!this.chatRoomRepository.existsById(roomNumber)){
            throw new RuntimeException("유저가 존재하지 않습니다.");
        }
        return ResponseEntity.ok(this.chatService.getChatsByRoomNumber(roomNumber));
        //각 채팅방에서 메시지 전송: ws://localhost:8080/api/chat/{roomNumber}
    }

    @DeleteMapping("/{roomNumber}")
    public ResponseEntity<Void> deleteChatRoom(@PathVariable int roomNumber){
        if(!this.chatRoomRepository.existsById(roomNumber)){
            throw new RuntimeException("유저가 존재하지 않습니다.");
        }
        this.chatService.deleteRoom(roomNumber);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
