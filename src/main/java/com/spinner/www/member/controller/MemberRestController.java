package com.spinner.www.member.controller;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.member.entity.Member;
import com.spinner.www.member.io.EmailAuthRequest;
import com.spinner.www.member.io.EmailSend;
import com.spinner.www.member.io.MemberLogin;
import com.spinner.www.member.io.MemberJoin;
import com.spinner.www.member.service.EmailService;
import com.spinner.www.member.service.RedisService;
import com.spinner.www.member.service.TokenService;
import com.spinner.www.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Tag(name = "member", description = "회원 API")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/member")
public class MemberRestController {

    private final MemberService memberService;
    private final EmailService emailService;
    private final TokenService tokenService;

    /**
     * 회원가입
     * @param memberJoin MemberJoin 회원가입 요청
     * @return ResponseEntity<CommonResponse> 회원가입 결과
     */
    @Operation(
            summary = "회원가입 API",
            description = "회원가입 요청입니다."
    )
    @Parameters({
            @Parameter(name = "password", description = "비밀번호"),
            @Parameter(name = "nickName", description = "닉네임"),
            @Parameter(name = "name", description = "이름"),
            @Parameter(name = "birth", description = "생년월일"),
            @Parameter(name = "file", description = "프로필이미지"),
            @Parameter(name = "marketingList", description = "마케팅수신동의"),
            @Parameter(name = "menuList", description = "관심사"),
    })
    @PostMapping("/signup")
    public ResponseEntity<CommonResponse> joinMember(@ModelAttribute MemberJoin memberJoin) throws IOException {
        return memberService.insertUser(memberJoin);
    }

    /**
     * 로그인
     * @param userLoginRequest UserLoginDto 로그인 요청 데이터
     * @return ResponseEntity<CommonResponse> 로그인 결과
     */
    @Operation(
            summary = "로그인 API",
            description = "로그인 요청입니다."
    )
    @Parameters({
            @Parameter(name = "email", description = "이메일"),
            @Parameter(name = "password", description = "비밀번호")
    })
    @PostMapping("/login")
    public ResponseEntity<CommonResponse> loginUser(@RequestBody MemberLogin userLoginRequest) {
        return memberService.loginMember(userLoginRequest);
    }

    /**
     * 이메일 인증
     * @param emailSend EmailSend
     * @return ResponseEntity<CommonResponse> 인증 코드 발송 결과
     */
    @Operation(
            summary = "이메일 인증 API",
            description = "이메일 중복검사 후 발송"
    )
    @Parameters({
            @Parameter(name = "email", description = "이메일주소"),
            @Parameter(name = "type", description = "SIGNUP : 회원가입 , FIND_ID : 아이디찾기, FIND_PASSWORD : 비밀번호찾기, ACCOUNT_DELETION : 계정삭제")
    })
    @PostMapping("/sendEmail")
    public ResponseEntity<CommonResponse> sendEmail(@RequestBody EmailSend emailSend) {
        return emailService.sendEmail(emailSend.getEmail(), emailSend.getType());
    }

    /**
     * 이메일 재발송
     * @param emailSend EmailSend
     * @return ResponseEntity<CommonResponse> 인증 코드 재발송 결과
     */
    @Operation(
            summary = "이메일 재인증 API",
            description = "이메일 중복검사 후 발송"
    )
    @Parameters({
            @Parameter(name = "email", description = "이메일주소"),
            @Parameter(name = "type", description = "SIGNUP : 회원가입 , FIND_ID : 아이디찾기, FIND_PASSWORD : 비밀번호찾기, ACCOUNT_DELETION : 계정삭제")
    })
    @PostMapping("/reSendEmail")
    public ResponseEntity<CommonResponse> reSendEmail(@RequestBody EmailSend emailSend) {
        return emailService.reSendEmail(emailSend.getEmail(), emailSend.getType());
    }

    /**
     * 이메일 인증코드 검증
     * @param emailAuthRequest EmailAuthRequest
     * @return ResponseEntity<CommonResponse> 인증코드 비교 결과
     */
    @Operation(
            summary = "이메일 인증코드 검증 API"
    )
    @Parameters({
            @Parameter(name = "email", description = "이메일주소"),
            @Parameter(name = "authCode", description = "인증코드")
    })
    @PostMapping("/authCode")
    public ResponseEntity<CommonResponse> getAuthCode(@RequestBody EmailAuthRequest emailAuthRequest){
        return emailService.invalidateAuthCode(emailAuthRequest);
    }

    /**
     * 리프레쉬 토큰 재발급
     * @param refreshToken
     * @param memberIdx
     * @return
     */
    @Operation(
            summary = "리프레쉬 토큰 재발급 API"
    )
    @Parameters({
            @Parameter(name = "refreshToken", description = "리프레쉬 토큰")
          
    })
    @PostMapping("/refresh")
    public ResponseEntity<CommonResponse> refreshToken(
            @CookieValue(value = "refreshToken", required = false) String refreshToken,
            @RequestParam("memberIdx") Long memberIdx) {
        Member member = memberService.getMember(memberIdx);
        return tokenService.refreshToken(refreshToken, member);
    }
}
