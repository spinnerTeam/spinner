package com.spinner.www.member.service;

import java.util.concurrent.TimeUnit;


public interface RedisService {

    /**
     * 인증코드 레디스 저장
     * @param email String
     * @param authCode String
     * @param days int
     * @param timeUnit TimeUnit
     */
    void saveRedis(String email, String authCode, int days , TimeUnit timeUnit);

    /**
     * 레디스에서 key 값으로 value 조회
     * @param key String
     * @return value String
     */
    String getValue(String key);

    /**
     * 소셜관련 세션 삭제
     */
    void deleteOauthRedisSession();

    /**
     * redis 에 key 값이 있는지 체크
     * @param key String
     * @return 키값의 존재 유무
     */
    boolean existsByRedisKey(String key);
}

