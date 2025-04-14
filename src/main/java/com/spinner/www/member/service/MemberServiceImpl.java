package com.spinner.www.member.service;

import com.spinner.www.common.entity.StudyTopic;
import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.common.redis.RedisService;
import com.spinner.www.common.service.StudyTopicService;
import com.spinner.www.constants.CommonResultCode;
import com.spinner.www.file.entity.Files;
import com.spinner.www.file.service.FileService;
import com.spinner.www.member.constants.RoleName;
import com.spinner.www.member.dto.*;
import com.spinner.www.member.entity.*;
import com.spinner.www.member.io.MemberLogin;
import com.spinner.www.member.io.MemberJoin;
import com.spinner.www.member.io.WithdrawMemberIo;
import com.spinner.www.member.mapper.MemberMapper;
import com.spinner.www.member.repository.*;
import com.spinner.www.util.EncryptionUtils;
import com.spinner.www.util.ResponseVOUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
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
    private final MemberWithDrawalLogRepo memberWithDrawalLogRepo;
    private EncryptionUtils encryptionUtils;
    private final SessionInfo sessionInfo;
    private final MemberMapper memberMapper;
    private final TokenService tokenService;
    private final HttpServletResponse response;
    private final RedisService redisService;
    private final MemberRoleService memberRoleService;
    private final FileService fileService;
    private final MemberFileService memberFileService;
    private final ServiceTermsRepo serviceTermsRepo;
    private final MarketingRepo marketingRepo;
    private final StudyTopicService studyTopicService;
    private final MemberInterestService memberInterestService;
    private final MemberQueryRepo memberQueryRepo;


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
    @Override
    public boolean isEmailInvalid (String memberEmail){
        return memberRepo.existsByMemberEmail(memberEmail);
    }

    /**
     * 회원가입
     * @param memberJoin memberJoin 회원가입 요청 데이터
     * @return ResponseEntity<CommonResponse> 회원가입 결과
     */
    @Override
    @Transactional
    public ResponseEntity<CommonResponse> insertUser(MemberJoin memberJoin) throws IOException {

        if(sessionInfo.getMemberEmail() == null){
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(0000, sessionInfo.getMemberEmail()),HttpStatus.NOT_FOUND);
        }
        // 세션에 있는 이메일이 레디스에 있나 없나 체크
        String key = redisService.getValue(sessionInfo.getMemberEmail());
        if(key == null)  {
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.DATA_NOT_FOUND),HttpStatus.NOT_FOUND);
        }

        // 비밀번호가 영문, 숫자 포함 8~20 자리인지 체크
        encryptionUtils.checkPasswordFormat(memberJoin.getPassword());

        // 비밀번호 암호화
        String password = encryptionUtils.encrypt(memberJoin.getPassword());
        MemberJoin updateMemberJoin = new MemberJoin(
            memberJoin,password
        );
        MemberCreateDto memberCreateDto = memberMapper.memberJoinToMemberCreate(updateMemberJoin);

        // 권한 조회
        MemberRole memberRole = memberRoleService.getRole(RoleName.GENERAL_MEMBER);
        MemberCreateDto updateMemberCreateDto = new MemberCreateDto(
                memberCreateDto,
                sessionInfo.getMemberEmail(),
                memberRole
        );
        // 멤버 디비 저장
        Member member = Member.insertMember(updateMemberCreateDto);
        memberRepo.save(member);

        Files files = null;
        // 프로필 이미지 첨부 여부
        if(memberJoin.getFile() != null && !memberJoin.getFile().isEmpty()){
            // 사진 업로드
            ResponseEntity<CommonResponse> response = fileService.uploadFiles(memberJoin.getFile());
            List<Long> idxs = (List<Long>) response.getBody().getResults();
            // 파일 저장
            files = fileService.getFiles(idxs.get(0));
        } else {
            // 기본이미지
            files = fileService.getFiles(114L);
        }

        memberFileService.create(member, files);

        // 마케팅 수신 동의 저장
        List<ServiceTerms> serviceTermsList = serviceTermsRepo.findByServiceTermsIsUse(true);
        List<Boolean> marketingList = memberJoin.getMarketingList();
        for (int i = 0; i < serviceTermsList.size(); i++) {
            MarketingCreateDto marketingCreateDto = new MarketingCreateDto(
                    serviceTermsList.get(i),
                    marketingList.get(i),
                    member
            );
            Marketing marketing = Marketing.insertMarketing(marketingCreateDto);
            marketingRepo.save(marketing);
        }

        // 관심분야
        List<Long> menuList = memberJoin.getMenuList();
        for(Long idx : menuList){
            StudyTopic studyTopic = studyTopicService.getStudyTopicByStudyTopicIdx(idx).orElseThrow(() -> new NoSuchElementException("존재하지 않는 관심분야입니다."));
            MemberInterestCreateDto memberInterestCreateDto = new MemberInterestCreateDto(
                    studyTopic,
                    member
            );
            memberInterestService.insertMemberInterest(memberInterestCreateDto);
        }

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

    /**
     * 비밀번호 변경
     * @param password String
     * @return ResponseEntity<CommonResponse>
     */
    @Override
    public ResponseEntity<CommonResponse> updatePw(@RequestParam String password) {
        Member member = memberRepo.findByMemberEmail(sessionInfo.getMemberEmail());
        if(member == null){
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.DATA_NOT_FOUND), HttpStatus.NOT_FOUND);
        }
        encryptionUtils.checkPasswordFormat(password);
        String afterPassword = encryptionUtils.encrypt(password);

        Member updateMember = new Member(
                member.getMemberIdx(),
                member.getMemberRole(),
                member.getMemberEmail(),
                afterPassword,
                member.getMemberName(),
                member.getMemberNickname(),
                member.getMemberBirth(),
                member.getMemberStatus(),
                member.getWithdrawalDate()
        );
        memberRepo.save(updateMember);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(), HttpStatus.OK);
    }
    /**
     * 프로필 수정 시 멤버 닉네임과 생년월일을 수정하기 위한 함수
     * @param memberIdx Long
     * @param nickname String
     * @param birth String
     */
    @Override
    @Transactional
    public void updateNicknameAndBirth(Long memberIdx, String nickname, String birth) {
        Member member = getMember(memberIdx);

        LocalDate birthDate = null;
        try {
            birthDate = LocalDate.parse(birth);
        } catch (DateTimeParseException e) {}
        member.updateNicknameAndBirth(nickname, birthDate);
    }

    /**
     * 스터디별 스터디원 수 조회
     * @param studyIdx Long
     * @return Integer
     */
    @Override
    public Integer getStudyMemberCountByStudyIdx(Long studyIdx) {
        return memberQueryRepo.getStudyMemberCountByStudyIdx(studyIdx);
    }

    /**
     * 회원 탈퇴 신청
     * @param withdrawMemberIo WithdrawMemberIo
     * @return ResponseEntity<CommonResponse>
     */
    @Override
    public ResponseEntity<CommonResponse> withdrawMember(WithdrawMemberIo withdrawMemberIo) {
        // 세션idx로 멤버 객체 불러오기
        Member member = getMember(sessionInfo.getMemberIdx());
        if(member == null){
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.FORBIDDEN), HttpStatus.FORBIDDEN);
        }

        // 탈퇴 사유 테이블 insert
        MemberWithdrawalLog memberWithdrawalLog = MemberWithdrawalLog.insertMemberWithdrawalLog(withdrawMemberIo, member.getMemberIdx());
        memberWithDrawalLogRepo.save(memberWithdrawalLog);

        // 탈퇴 처리
        member.updateWithdrawalMember();
        memberRepo.save(member);

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse("탈퇴신청이 완료되었습니다."), HttpStatus.OK);
    }
}
