package com.spinner.www.users.service;


import com.spinner.www.common.CommonResponse;
import com.spinner.www.users.io.UserLoginRequest;
import com.spinner.www.users.io.UserRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {

    /**
     * 회원가입
     * @param userRequest UserRequestDto 회원가입 요청 데이터
     * @return ResponseEntity<CommonResponse> 회원가입 결과
     */
    ResponseEntity<CommonResponse> insertUser(UserRequest userRequest);

    /**
     * 로그인
     * @param userLoginRequest UserLoginDto 로그인 요청 데이터
     * @return ResponseEntity<CommonResponse> 로그인 결과
     */
    ResponseEntity<CommonResponse> loginUser(UserLoginRequest userLoginRequest);

    /**
     * 쿠키에 refreshToken 저장
     * @param response HttpServletResponse
     * @param refreshToken String
     * @param expiryDate int
     */
    void setRefreshTokenCookie(HttpServletResponse response, String refreshToken , int expiryDate);
}
