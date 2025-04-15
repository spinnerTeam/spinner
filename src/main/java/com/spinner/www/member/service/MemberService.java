package com.spinner.www.member.service;


import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.member.constants.MemberStatus;
import com.spinner.www.member.dto.MemberDto;
import com.spinner.www.member.dto.MemberSessionDto;
import com.spinner.www.member.entity.Member;
import com.spinner.www.member.io.MemberLogin;
import com.spinner.www.member.io.MemberJoin;
import com.spinner.www.member.io.WithdrawMemberIo;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface MemberService {

    /**
     * 회원가입
     * @param memberRequest UserRequestDto 회원가입 요청 데이터
     * @return ResponseEntity<CommonResponse> 회원가입 결과
     */
    ResponseEntity<CommonResponse> insertUser(MemberJoin memberRequest) throws IOException;

    /**
     * 로그인
     * @param memberLogin MemberLogin 로그인 요청 데이터
     * @return ResponseEntity<CommonResponse> 로그인 결과
     */
    ResponseEntity<CommonResponse> loginMember(MemberLogin memberLogin);

    /**
     * 쿠키에 refreshToken 저장
     * @param response HttpServletResponse
     * @param refreshToken String
     * @param expiryDate int
     */
    void setRefreshTokenCookie(HttpServletResponse response, String refreshToken , int expiryDate);

    /**
     * idx로 회원 조회
     * @param memberIdx Long
     * @return Member
     */
    Member getMember(Long memberIdx);

    /**
     * 이메일로 회원조회
     * @param memberEmail String
     * @return Member
     */
    Member getMember(String memberEmail);

    /**
     * 토큰 생성
     * @param member Member
     * @param memberSessionDto MemberLoginDto
     */
    void makeLoginToken(Member member, MemberSessionDto memberSessionDto);


    /**
     * user 이메일 중복검사
     * @param memberEmail String
     * @return 조회한 결과 Boolean
     */
    boolean isEmailInvalid (String memberEmail);

    /**
     * 비밀번호 변경
     * @param password String
     * @return  ResponseEntity<CommonResponse>
     */
    ResponseEntity<CommonResponse> updatePw(String password);

    /**
     * 프로필 수정 시 멤버 닉네임과 생년월일을 수정하기 위한 함수
     * @param memberIdx Long
     * @param nickname String
     * @param birth String
     */
    void updateNicknameAndBirth(Long memberIdx, String nickname, String birth);

    /**
     * 스터디별 스터디원 수 조회
     * @param studyIdx Long
     * @return Integer
     */
    Integer getStudyMemberCountByStudyIdx(Long studyIdx);

    /**
     * 회원 탈퇴 신청
     * @param withdrawMemberIo WithdrawMemberIo
     * @return ResponseEntity<CommonResponse>
     */
    ResponseEntity<CommonResponse> withdrawMember(WithdrawMemberIo withdrawMemberIo);

    /**
     * 회원 상태 + 날짜 별 조회
     * @param memberStatus 멤버상태값 예: ACTIVE
     * @return 조건에 맞는 회원 리스트
     */
    List<Member> findWithdrawnMembersBefore(MemberStatus memberStatus, LocalDate localDate);

    /**
     * 회원 리스트 저장
     * @param members 저장할 회원리스트
     */
    void saveAll(List<Member> members);
}
