package com.spinner.www.member.service;

import com.spinner.www.common.CommonResponse;
import com.spinner.www.constants.CommonResultCode;
import com.spinner.www.member.dto.MemberLoginDto;
import com.spinner.www.member.dto.SessionInfo;
import com.spinner.www.member.entity.Member;
import com.spinner.www.member.io.MemberLogin;
import com.spinner.www.member.io.MemberCreate;
import com.spinner.www.member.mapper.MemberMapper;
import com.spinner.www.member.repository.UserRepo;
import com.spinner.www.util.EncryptionUtils;
import com.spinner.www.util.ResponseVOUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    // 토큰 만료 시간
    private static final int DEFAULT_ACCESS_EXPIRATION_MINUTES = 30;
    // 토큰 만료 일
    private static final int DEFAULT_REFRESH_EXPIRATION_DAYS = 7;
    // redis 만료
    private static final int DEFAULT_SESSION_EXPIRATION_MINUTES = 60 * 24 * 7;

    private final UserRepo userRepo;
    private final EncryptionUtils encryptionUtils;
    private final SessionInfo sessionInfo;
    private final MemberMapper memberMapper;
    private final TokenService tokenService;
    private final HttpServletResponse response;

    /**
     * user 이메일 중복검사
     * @param memberEmail String
     * @return 조회한 결과 Boolean
     */
    private boolean isEmailInvalid (String memberEmail){
        return userRepo.existsByMemberEmail(memberEmail);
    }

    /**
     * 회원가입
     * @param memberRequest UserRequestDto 회원가입 요청 데이터
     * @return ResponseEntity<CommonResponse> 회원가입 결과
     */
    @Override
    public ResponseEntity<CommonResponse> insertUser(MemberCreate memberRequest) {

        // 이메일 중복검사
        boolean isEmailInvalid = isEmailInvalid(memberRequest.getMemberEmail());

        if (isEmailInvalid) {
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.DUPLICATE),HttpStatus.CONFLICT);
        }

        // 비밀번호 암호화
        String pw = encryptionUtils.encrypt(memberRequest.getMemberPassword());

        Member user = Member.builder()
                .memberEmail(memberRequest.getMemberEmail())
                .memberName(memberRequest.getMemberName())
                .memberPassword(pw)
                .memberBirth(memberRequest.getMemberBirth())
                .memberNickname(memberRequest.getMemberNickname())
                .build();
        userRepo.save(user);

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(), HttpStatus.CREATED);
    }

    /**
     * users > userLoginDto 변환
     * @param memberEmail String
     * @return userLoginDto
     */
    public MemberLoginDto getUserLoginDto(String memberEmail) {
        Member user = userRepo.findByMemberEmail(memberEmail);
        return memberMapper.memberToMemberLoginDTO(user);
    }

    /**
     *  로그인
     * @param userLoginRequest UserLoginDto 로그인 요청 데이터
     * @return ResponseEntity<CommonResponse> 로그인 결과
     */
    @Override
    public ResponseEntity<CommonResponse> loginUser(MemberLogin userLoginRequest) {

        // 이메일로 회원 객체 조회
        MemberLoginDto memberLoginDto = getUserLoginDto(userLoginRequest.getMemberEmail());

        if (memberLoginDto == null)  {
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.DATA_NOT_FOUND),HttpStatus.BAD_REQUEST);
        }

        boolean invalidatePassword = encryptionUtils.invalidatePassword(userLoginRequest.getMemberPassword(), memberLoginDto.getMemberPassword());

        if(!invalidatePassword)  {
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.DATA_NOT_FOUND),HttpStatus.BAD_REQUEST);
        }

        // acessToken 생성
        String accessToken =
                tokenService.makeToken(new Date(System.currentTimeMillis() + Duration.ofMinutes(DEFAULT_ACCESS_EXPIRATION_MINUTES).toMillis()), memberLoginDto);
        memberLoginDto.setAcessToken(accessToken);
        // refreshToken 생성
        String refreshToken =
                tokenService.makeToken(new Date(System.currentTimeMillis() + Duration.ofDays(DEFAULT_REFRESH_EXPIRATION_DAYS).toMillis()), memberLoginDto);

        // refreshToken redis에 저장
        tokenService.saveRefreshToken(String.valueOf(memberLoginDto.getMemberIdx()), refreshToken, DEFAULT_REFRESH_EXPIRATION_DAYS , TimeUnit.DAYS);
        // refreshToken http쿠키에 저장
        setRefreshTokenCookie(response, refreshToken, DEFAULT_REFRESH_EXPIRATION_DAYS);

        sessionInfo.Login(memberLoginDto);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(accessToken), HttpStatus.OK);
    }

    /**
     * 쿠키에 refreshToken 저장
     * @param response HttpServletResponse
     * @param refreshToken String
     * @param expiryDate int
     */
    @Override
    public void setRefreshTokenCookie(HttpServletResponse response, String refreshToken, int expiryDate) {
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        // 추후 https로 변경 하고 수정
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(expiryDate);

        response.addCookie(cookie);
    }
}
