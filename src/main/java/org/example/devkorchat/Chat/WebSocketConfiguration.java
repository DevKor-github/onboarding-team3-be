package org.example.devkorchat.Chat;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                .addHandler(signalingSocketHandler(), "/api/chat/*") //server endpoint: url:port/room
                .setAllowedOrigins("*"); //CORS 설정: 모든 요청 수용
    }

    @Bean
    public WebSocketHandler signalingSocketHandler() {
        return new WebSocketHandler();
    }
}
