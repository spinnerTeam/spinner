package com.spinner.www.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
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
     * 소셜로그인 세션 삭제
     */
    @Override
    public void deleteOauthRedisSession() {

        String sessionKey = "spring:session:sessions:*";

        Set<String> sessionList = redisTemplate.keys(sessionKey);
        if(sessionList != null)  {
            for(String key : sessionList){
                Object sessionData = redisTemplate.opsForHash().entries(sessionKey);

                if (isSocialLoginSession(sessionData)) {
                    redisTemplate.delete(sessionKey);
                }
            }
        }
    }

    // 소셜 로그인 세션 여부 확인 메서드
    private boolean isSocialLoginSession(Object sessionData) {
        if (sessionData instanceof Map) {
            Map<String, Object> attributes = (Map<String, Object>) sessionData;
            return attributes.containsKey("socialLoginFlag");
        }
        return false;
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
