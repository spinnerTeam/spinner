package com.spinner.www.member.service;


import com.spinner.www.common.CommonResponse;
import com.spinner.www.member.io.MemberLogin;
import com.spinner.www.member.io.MemberCreate;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface MemberService {

    /**
     * 회원가입
     * @param memberRequest UserRequestDto 회원가입 요청 데이터
     * @return ResponseEntity<CommonResponse> 회원가입 결과
     */
    ResponseEntity<CommonResponse> insertUser(MemberCreate memberRequest);

    /**
     * 로그인
     * @param userLoginRequest UserLoginDto 로그인 요청 데이터
     * @return ResponseEntity<CommonResponse> 로그인 결과
     */
    ResponseEntity<CommonResponse> loginUser(MemberLogin userLoginRequest);

    /**
     * 쿠키에 refreshToken 저장
     * @param response HttpServletResponse
     * @param refreshToken String
     * @param expiryDate int
     */
    void setRefreshTokenCookie(HttpServletResponse response, String refreshToken , int expiryDate);
}
