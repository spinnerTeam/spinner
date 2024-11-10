package com.spinner.www.users.service;

import com.spinner.www.users.dto.JwtProperties;
import com.spinner.www.users.dto.UserLoginDto;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class TokenServiceImpl implements TokenService{

    private final JwtProperties jwtProperties;
    private final RedisTemplate<String, String> redisTemplate;

    /**
     * 토큰 생성
     * @param expiration Date
     * @param userLoginDto userLoginDto
     * @return 만들어진 토큰 반환
     */
    @Override
    public String makeToken(Date expiration, UserLoginDto userLoginDto) {
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(now)
                .setExpiration(expiration)
                .setSubject(userLoginDto.getEmail())
                .claim("nickname", userLoginDto.getUNickname())
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }

    /**
     * 레디스에 토큰 저장
     * @param uIdx String
     * @param refreshToken String
     * @param days int
     */
    @Override
    public void saveRefreshToken(String uIdx, String refreshToken, int days , TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(uIdx, refreshToken, days, TimeUnit.DAYS);
    }

    /**
     * 레디스에서 토큰 조회
     * @param uIdx String
     * @return refreshToken
     */
    @Override
    public String getRefreshToken(String uIdx) {
        return redisTemplate.opsForValue().get(uIdx);
    }


}
