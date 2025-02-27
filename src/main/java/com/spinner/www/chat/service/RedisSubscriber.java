package com.spinner.www.chat.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spinner.www.chat.io.MessageIo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@RequiredArgsConstructor
public class RedisSubscriber implements MessageListener {

    private static final Logger log = LoggerFactory.getLogger(RedisSubscriber.class);
    private final ObjectMapper objectMapper;

    private static final Map<Long, CopyOnWriteArrayList<WebSocketSession>> roomSessionMap = new ConcurrentHashMap<>();


    // 세션 추가
    public void addSession(Long roomIdx, WebSocketSession session) {
        roomSessionMap.putIfAbsent(roomIdx, new CopyOnWriteArrayList<>());
        roomSessionMap.get(roomIdx).add(session);
        log.info("세션 추가: roomIdx={}, 세션 개수={}", roomIdx, roomSessionMap.get(roomIdx).size());
    }

    // 세션 제거
    public void removeSession(Long roomIdx, WebSocketSession session) {
        if (roomSessionMap.containsKey(roomIdx)) {
            roomSessionMap.get(roomIdx).remove(session);
        }
    }

    /**
     * redis 로 메시지 수신 콜백
     * @param message
     * @param pattern
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        String publish = new String(message.getBody());
        System.out.println("온메시지");
        log.info("Redis 메시지 수신: {}", publish);
        try {
            MessageIo messageIo = objectMapper.readValue(publish, MessageIo.class);
            Long roomIdx = messageIo.getRoomIdx();

            if(roomSessionMap.containsKey(roomIdx)){
                for(WebSocketSession session : roomSessionMap.get(roomIdx)){
                    session.sendMessage(new TextMessage(publish));
                }
            }

        } catch (IOException e) {
            log.info("메시지 수신 오류");
        }
    }
}
