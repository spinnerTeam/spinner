package com.spinner.www.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * 메시지 브로커
     * [note] 클라이언트가 메시지를 주고받는 경로
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 브로커 경로
       registry.enableSimpleBroker("/room");
       // 서버로 메시지 보낼 때 사용하는 경로
       registry.setApplicationDestinationPrefixes("/app");
    }

    /**
     * STOMP 엔드포인드 등록
     * [note] 클라이언와 서버를 연결하는 webSocket 경로
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat")
                .setAllowedOrigins("*");
    }


}
