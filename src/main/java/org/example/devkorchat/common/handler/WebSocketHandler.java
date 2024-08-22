package org.example.devkorchat.common.handler;

import com.google.gson.*;
import lombok.extern.slf4j.Slf4j;
import org.example.devkorchat.chat.ChatEntity;
import org.example.devkorchat.chat.ChatService;
import org.example.devkorchat.chat.dto.ChatDTO;
import org.example.devkorchat.chat.dto.Message;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {

    private final ChatService chatService;

    //Key: session ID; Value: session
    List<HashMap<String, Object>> sessions = new ArrayList<>();

    public WebSocketHandler(ChatService chatService) {
        this.chatService = chatService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        String sessionId = session.getId();
        String url = session.getUri().toString();
        String roomNumber = url.split("/api/chat/")[1];
        int idx = sessions.size();
        boolean flag = false;

        if(!sessions.isEmpty()) {
            for(int i=0; i<sessions.size(); i++){
                String sessionRoomNumber = (String) sessions.get(i).get("roomNumber");
                if(roomNumber.equals(sessionRoomNumber)){
                    flag = true;
                    idx = i;
                    break;
                }
            }
        }

        HashMap<String, Object> map = new HashMap<>();
        //존재하는 방에 입장
        if(flag) {
            map = sessions.get(idx);
            map.put(session.getId(), session);
        } else { //입장하는 방 생성
            map.put("roomNumber", roomNumber);
            map.put(session.getId(), session);
            sessions.add(map);
        }

        Message message = new Message("new", session.getId(), roomNumber, "new connection");

        for(String k: map.keySet()){
            if(k.equals("roomNumber")) continue;
            WebSocketSession wss = (WebSocketSession) map.get(k);
            if(wss != null) {
                try{
                    wss.sendMessage( new TextMessage(message.toString()));
                } catch (Exception e) {
                    //TODO
                }
            }
        }

    }

    @Override
    public void handleTextMessage (WebSocketSession session, TextMessage textMessage) throws Exception {
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
            @Override
            public LocalDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                Instant instant = Instant.ofEpochMilli(json.getAsJsonPrimitive().getAsLong());
                return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
            }
        }).create();

        ChatEntity chatEntity = gson.fromJson(textMessage.getPayload(), ChatEntity.class);

        ChatDTO chatDto = new ChatDTO(chatEntity.getUsername(), chatEntity.getMessage(), chatEntity.getRoomNumber());
        chatService.saveChat(chatDto);

        String roomNumber = Integer.toString(chatEntity.getRoomNumber());
        HashMap<String, Object> temp = new HashMap<>();

        for (HashMap<String, Object> stringObjectHashMap : sessions) {
            String sessionRoomNumber = (String) stringObjectHashMap.get("roomNumber");
            if (roomNumber.equals(sessionRoomNumber)) {
                temp = stringObjectHashMap;
                break;
            }
        }

        for(String k: temp.keySet()){
            if(k.equals("roomNumber")) continue;
            WebSocketSession wss = (WebSocketSession) temp.get(k);
            if(wss != null) {
                try{
                    wss.sendMessage( new TextMessage(chatEntity.toString()));
                } catch (IOException e) {
                   //TODO
                }
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

        if(!sessions.isEmpty()){
            for (HashMap<String, Object> stringObjectHashMap : sessions) {
                stringObjectHashMap.remove(session.getId());
            }
        }

        super.afterConnectionClosed(session, status);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
    }

}
