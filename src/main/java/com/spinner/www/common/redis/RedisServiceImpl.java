package com.spinner.www.common.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class RedisServiceImpl implements RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    /**
     * 레디스 데이터 저장
     * @param key String
     * @param value String
     * @param time int
     * @param timeUnit TimeUnit
     */
    @Override
    public void saveRedis(String key, String value, int time , TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, time, timeUnit);
    }

    /**
     * 레디스 key 값으로 value 조회
     * @param key String
     * @return value String
     */
    @Override
    public String getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 레디스에 key 값이 있는지 없는지
     * @param key String
     * @return 키값의 존재 유무
     */
    @Override
    public boolean existsByRedisKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
}
