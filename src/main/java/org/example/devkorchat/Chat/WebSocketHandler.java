package org.example.devkorchat.Chat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {
    //Key: session ID; Value: session
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String sessionId = session.getId();
        sessions.put(sessionId, session); //세션 저장

        Message message = Message.builder().sender(sessionId).receiver("all").build();
        message.newConnect();

        sessions.values().forEach(s -> {
            try{
                if(!s.getId().equals(sessionId)) { //나를 제외한 모든 세션에 알림
                    s.sendMessage(new TextMessage(message.toString()));
                }
            }
            catch (Exception e) {
                //TODO: throw
            }
        });
    }


}
