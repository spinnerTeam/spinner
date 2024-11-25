package com.spinner.www.member.service;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.member.io.EmailAuthRequest;
import org.springframework.http.ResponseEntity;
import java.util.concurrent.TimeUnit;

public interface EmailService {

    /**
     * 랜덤 숫자
     * @return authCode String
     */
    String createAuthCode();

    /**
     * 이메일 전송
     * @param toEmail String
     * @return authCode String
     */
    ResponseEntity<CommonResponse> sendEmail(String toEmail, String type);

    /**
     * 인증코드 레디스 저장
     * @param email String
     * @param authCode String
     * @param days int
     * @param timeUnit TimeUnit
     */
    void saveAuthCode(String email, String authCode, int days , TimeUnit timeUnit);

    /**
     * 이메일 인증코드 검증
     * @param emailAuthRequest EmailAuthRequest
     * @return ResponseEntity<CommonResponse> 인증코드 비교 결과
     */
    ResponseEntity<CommonResponse> invalidateAuthCode(EmailAuthRequest emailAuthRequest);
}
