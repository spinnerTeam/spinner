package com.spinner.www.users.service;

import com.spinner.www.users.dto.UserLoginDto;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public interface TokenService {

    /**
     * 토큰 생성
     * @param expiration Date
     * @param userLoginDto UserLoginDto
     * @return token
     */
    String makeToken(Date expiration, UserLoginDto userLoginDto);

    /**
     * 토큰 레디스에 저장
     * @param memberIdx String
     * @param refreshToken String
     * @param days int
     * @param timeUnit TimeUnit
     */
    void saveRefreshToken(String memberIdx, String refreshToken, int days, TimeUnit timeUnit);

    /**
     * 레디스에서 회원 번호로 토큰 조회
     * @param memberIdx String
     * @return refreshToken String
     */
    String getRefreshToken(String memberIdx);
}
