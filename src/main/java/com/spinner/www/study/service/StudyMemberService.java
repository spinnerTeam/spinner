package com.spinner.www.study.service;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.member.entity.Member;
import com.spinner.www.study.entity.StudyMember;
import com.spinner.www.study.io.JoinStudyMember;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface StudyMemberService {

    /**
     * 스터디 멤버 저장
     * @param studyMember  StudyMember
     */
    void saveStudyMember(StudyMember studyMember);

    /**
     * memberIdx 별 스터디 멤버 조회
     * @param studyIdx Long
     * @param member Member
     * @return StudyMember
     */
    StudyMember getStudyMember(Member member,Long studyIdx);

    /**
     * 스터디 가입여부
     * @param studyMemberIdx Long
     * @param member Member
     * @return boolean
     */
    boolean existsByStudyMemberIdxAndMember(Long studyMemberIdx, Member member);

    /**
     * 나의 스터디 (참여스터디, 가입대기스터디, 종료 스터디)
     * @param memberIdx Long
     * @param studyMemberStatus String
     * @return  ResponseEntity<CommonResponse>
     */
    ResponseEntity<CommonResponse> findByStudyMemberStatusAndMember(String studyMemberStatus, Long memberIdx);

    /**
     * 스터디 멤버관리(신청인원, 참여인원)
     * @param studyIdx Long
     * @param studyStatus String
     * @return ResponseEntity<CommonResponse>
     */
    ResponseEntity<CommonResponse> findByStudyAndStudyStatus(Long studyIdx, String studyStatus);

    /**
     * 스터디 가입 승인 & 스터디 가입 거절 취소?
     * @param studyIdx
     * @param memberIdx
     * @return
     */
    ResponseEntity<CommonResponse> updateStudyMember(Long studyIdx, Long memberIdx);

    /**
     * 스터디 가입취소
     * @param studyIdx Long
     * @return ResponseEntity<CommonResponse>
     */
    ResponseEntity<CommonResponse> cancelStudy(Long studyIdx);

    ResponseEntity<CommonResponse> cancelStudyMember(Long studyIdx, Long memberIdx);
}
