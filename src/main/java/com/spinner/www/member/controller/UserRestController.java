package com.spinner.www.member.controller;

import com.spinner.www.common.CommonResponse;
import com.spinner.www.member.dto.SessionInfo;
import com.spinner.www.member.io.MemberLogin;
import com.spinner.www.member.io.MemberCreate;
import com.spinner.www.member.service.TokenService;
import com.spinner.www.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserRestController {

    private final MemberService memberService;
    private final SessionInfo sessionInfo;
    private final TokenService tokenService;

    /**
     * 회원가입
     * @param memberRequest UserRequestDto 회원가입 요청 데이터
     * @return ResponseEntity<CommonResponse> 회원가입 결과
     */
    @PostMapping("/signup")
    public ResponseEntity<CommonResponse> invalidateEmail(@RequestBody MemberCreate memberRequest) {
        return memberService.insertUser(memberRequest);
    }

    /**
     * 로그인
     * @param userLoginRequest UserLoginDto 로그인 요청 데이터
     * @return ResponseEntity<CommonResponse> 로그인 결과
     */
    @PostMapping("/login")
    public ResponseEntity<CommonResponse> loginUser(@RequestBody MemberLogin userLoginRequest) {
        return memberService.loginUser(userLoginRequest);
    }

    /**
     * 토큰 조회
     * @param uIdx
     * @return
     */
    @PostMapping("/refreshToken")
    public String getRefreshToken(@RequestParam String uIdx){
        return tokenService.getRefreshToken(uIdx);
    }

//    @PostMapping("/sendEmail")
//    public ResponseEntity<CommonResponse> sendEmail(@RequestParam String email){
//        return userService.
//    }
}
