package com.spinner.www.member.service;


import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.member.entity.Member;
import com.spinner.www.member.entity.Social;
import org.springframework.http.ResponseEntity;

public interface SocialService {

    /**
     * sub 로 조회
     * @param socialSub String
     * @return social
     */
    Social getSocial(String socialSub);

    /**
     * 소셜 로그인
     * @param social Social
     * @return 로그인 결과
     */
    ResponseEntity<CommonResponse> loginSocial(Social social);

    /**
     * 소셜 연동
     * @param socialNum String
     * @return 연동 결과
     */
    ResponseEntity<CommonResponse> joinSocialMember(String socialNum, String memberEmail, String clientId);
}
