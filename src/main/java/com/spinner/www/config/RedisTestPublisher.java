package com.spinner.www.config;

import jakarta.annotation.PostConstruct;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisTestPublisher {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisTestPublisher(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    public void testPublish() {
        redisTemplate.convertAndSend("SpinnerChatRoom", "Test message from server");
        System.out.println("Redis에 메시지 퍼블리시 완료: room/1");
    }
}
