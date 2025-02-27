package com.spinner.www.chat.controller;

import com.spinner.www.chat.io.MessageIo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final RedisTemplate<String, Object> redisTemplate;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat") // 클라이언트가 /app/chat으로 메시지를 보낼 때 처리
    public void handleMessage(MessageIo msg) {
        Long roomId = msg.getRoomIdx(); // 메시지에서 roomIdx 추출
        // 동적으로 경로 지정하여 메시지 전송
        messagingTemplate.convertAndSend("/room/chat/" + roomId, msg);
    }
}
