package com.spinner.www.member.service;

import com.spinner.www.member.dto.JwtProperties;
import com.spinner.www.member.entity.Member;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

@RequiredArgsConstructor
@Service
public class TokenServiceImpl implements TokenService{

    private final JwtProperties jwtProperties;
    private final RedisTemplate<String, String> redisTemplate;

    /**
     * 토큰 생성
     * @param expiration Date
     * @param member Member
     * @return 만들어진 토큰 반환
     */
    @Override
    public String makeToken(Date expiration, Member member) {
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(now)
                .setExpiration(expiration)
                .setSubject(member.getMemberEmail())
                .claim("nickname", member.getMemberNickname())
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }

}
