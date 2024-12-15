package com.spinner.www.member.service;

import com.spinner.www.member.entity.Member;

import java.util.Date;

public interface TokenService {

    /**
     * 토큰 생성
     * @param expiration Date
     * @param member Member
     * @return token
     */
    String makeToken(Date expiration, Member member);

}
