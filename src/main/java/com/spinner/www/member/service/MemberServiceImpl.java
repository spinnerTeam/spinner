package com.spinner.www.member.service;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.constants.CommonResultCode;
import com.spinner.www.file.entity.Files;
import com.spinner.www.file.service.FileService;
import com.spinner.www.member.constants.RoleName;
import com.spinner.www.member.dto.MemberCreateDto;
import com.spinner.www.member.dto.MemberSessionDto;
import com.spinner.www.member.dto.SessionInfo;
import com.spinner.www.member.entity.Member;
import com.spinner.www.member.entity.MemberRole;
import com.spinner.www.member.io.MemberLogin;
import com.spinner.www.member.io.MemberJoin;
import com.spinner.www.member.mapper.MemberMapper;
import com.spinner.www.member.repository.MemberRepo;
import com.spinner.www.util.EncryptionUtils;
import com.spinner.www.util.ResponseVOUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    // 토큰 만료 시간
    private static final int DEFAULT_ACCESS_EXPIRATION_MINUTES = 30;
    // 토큰 만료 일
    private static final int DEFAULT_REFRESH_EXPIRATION_DAYS = 7;

    private final MemberRepo memberRepo;
    private final EncryptionUtils encryptionUtils;
    private final SessionInfo sessionInfo;
    private final MemberMapper memberMapper;
    private final TokenService tokenService;
    private final HttpServletResponse response;
    private final RedisService redisService;
    private final MemberRoleService memberRoleService;
    private final FileService fileService;

    /**
     * 이메일로 회원조회
     * @param memberEmail String
     * @return member
     */
    @Override
    public Member getMember(String memberEmail) {
        return memberRepo.findByMemberEmail(memberEmail);
    }

    /**
     * memberIdx 로 회원 조회
     * @param memberIdx Long
     * @return member
     */
    @Override
    public Member getMember(Long memberIdx) {
        return memberRepo.findById(memberIdx).orElse(null);
    }

    /**
     * user 이메일 중복검사
     * @param memberEmail String
     * @return 조회한 결과 Boolean
     */
    private boolean isEmailInvalid (String memberEmail){
        return memberRepo.existsByMemberEmail(memberEmail);
    }

    /**
     * 회원가입
     * @param memberJoin memberJoin 회원가입 요청 데이터
     * @return ResponseEntity<CommonResponse> 회원가입 결과
     */
    @Override
    public ResponseEntity<CommonResponse> insertUser(MemberJoin memberJoin) {

        System.out.println("member");

        // 비밀번호가 영문, 숫자 포함 8~20 자리인지 체크
        String PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,20}$";
        if(!memberJoin.getPassword().matches(PASSWORD_PATTERN))  {
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.INVALID_PASSWORD_FORMAT), HttpStatus.BAD_REQUEST);
        }

        // 세션에 있는 이메일이 레디스에 있나 없나 체크
//        String key = redisService.getValue(sessionInfo.getMemberEmail());
//        if(key == null)  {
//            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.DATA_NOT_FOUND),HttpStatus.NOT_FOUND);
//        }

        // 비밀번호 암호화
        String password = encryptionUtils.encrypt(memberJoin.getPassword());
        memberJoin.setPassword(password);

        MemberCreateDto memberCreateDto = memberMapper.memberJoinToMemberCreate(memberJoin);

        // 권한 조회
        MemberRole memberRole = memberRoleService.getRole(RoleName.GENERAL_MEMBER);
        memberCreateDto.setMemberRole(memberRole);

        // 사진 업로드
        ResponseEntity<CommonResponse> response = fileService.uploadFile(memberJoin.getFile());
        List<Long> idxs = (List<Long>) response.getBody().getResults();

        // 멤버 디비 저장
        Member member = Member.insertMember(memberCreateDto);

        // 사진 멤버 디비 저장
//        Files files = fileService.

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(), HttpStatus.CREATED);
    }

    /**
     *  로그인
     * @param memberLogin UserLoginDto 로그인 요청 데이터
     * @return ResponseEntity<CommonResponse> 로그인 결과
     */
    @Override
    public ResponseEntity<CommonResponse> loginMember(MemberLogin memberLogin)  {

        // 이메일로 회원 객체 조회
        MemberSessionDto memberSessionDto = memberMapper.memberLoginToMemberSessionDto(memberLogin);
        Member member = getMember(memberSessionDto.getMemberEmail());

        if (member == null)  {
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.DATA_NOT_FOUND),HttpStatus.BAD_REQUEST);
        }

        boolean invalidatePassword = encryptionUtils.invalidatePassword(memberLogin.getPassword(), member.getMemberPassword());

        if(!invalidatePassword)  {
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.PASSWORD_MISMATCH),HttpStatus.UNAUTHORIZED);
        }
        memberSessionDto.setMemberIdx(member.getMemberIdx());
        // 토큰 생성 및 저장
        makeLoginToken(member, memberSessionDto);
        redisService.saveRedis("email", memberSessionDto.getMemberEmail(), DEFAULT_REFRESH_EXPIRATION_DAYS, TimeUnit.DAYS);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(memberSessionDto.getAcessToken()), HttpStatus.OK);
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

    /**
     * 로그인 토큰 생성
     * @param member Member
     * @param memberSessionDto MemberSessionDto
     */
    @Override
    public void makeLoginToken(Member member, MemberSessionDto memberSessionDto){

        String accessToken =
                tokenService.makeToken(new Date(System.currentTimeMillis() + Duration.ofMinutes(DEFAULT_ACCESS_EXPIRATION_MINUTES).toMillis()), member);
        memberSessionDto.setAcessToken(accessToken);
        // refreshToken 생성
        String refreshToken =
                tokenService.makeToken(new Date(System.currentTimeMillis() + Duration.ofDays(DEFAULT_REFRESH_EXPIRATION_DAYS).toMillis()), member);

        // refreshToken redis에 저장
        redisService.saveRedis(String.valueOf(member.getMemberIdx()), refreshToken, DEFAULT_REFRESH_EXPIRATION_DAYS , TimeUnit.DAYS);
        // refreshToken http쿠키에 저장
        setRefreshTokenCookie(response, refreshToken, DEFAULT_REFRESH_EXPIRATION_DAYS);
        sessionInfo.setSession(memberSessionDto);
    }


}
