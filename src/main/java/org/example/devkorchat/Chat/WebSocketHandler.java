package org.example.devkorchat.Chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
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

    @Override
    public void handleTextMessage (WebSocketSession session, TextMessage textMessage) throws Exception {
        Gson gson = new Gson();
        Message message = gson.fromJson(textMessage.getPayload(), Message.class);
        message.setSender(session.getId());

        WebSocketSession receiver = sessions.get(message.getReceiver());

        if(receiver != null && receiver.isOpen()) {
            receiver.sendMessage(new TextMessage(message.toString()));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String sessionId = session.getId();

        sessions.remove(sessionId);

        final Message message = new Message();
        message.closeConnect();
        message.setSender(sessionId);

        sessions.values().forEach(s ->{
            try{
                s.sendMessage(new TextMessage(message.toString()));
            } catch (Exception e){
                //TODO: throw
            }
        });
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
    }

}
