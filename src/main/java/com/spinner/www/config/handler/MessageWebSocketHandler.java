package com.spinner.www.config.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spinner.www.chat.io.MessageIo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@RequiredArgsConstructor
public class MessageWebSocketHandler extends TextWebSocketHandler {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ChannelTopic chatTopic;
    private final ObjectMapper objectMapper;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            // JSON 파싱
            ObjectMapper objectMapper = new ObjectMapper();
            MessageIo messageIo = objectMapper.readValue(message.getPayload(), MessageIo.class);

            // 로그 출력
            System.out.println("메시지 수신: " + messageIo);

            // 클라이언트로 응답
            session.sendMessage(new TextMessage("메시지 처리 완료: " + messageIo.getContent()));
        } catch (Exception e) {
            // 에러 로그 출력
            System.err.println("메시지 파싱 오류: " + e.getMessage());

            // 클라이언트로 에러 응답 전송
            session.sendMessage(new TextMessage("메시지 형식 오류"));
        }
    }
}
