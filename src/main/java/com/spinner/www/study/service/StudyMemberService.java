package com.spinner.www.study.service;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.member.entity.Member;
import com.spinner.www.study.constants.StudyMemberStatus;
import com.spinner.www.study.dto.StudyJoinMemberDto;
import com.spinner.www.study.entity.Study;
import com.spinner.www.study.entity.StudyMember;
import com.spinner.www.study.io.JoinStudyMember;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

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
     *  studyMember 객체 조회
     * @param member Member
     * @param study Study
     * @return StudyMember
     */
    StudyMember getStudyMember(Member member, Study study);

    /**
     * 스터디 가입여부
     * @param study Study
     * @param member Member
     * @return boolean
     */
    boolean existsByStudyAndMember(Study study, Member member);

    /**
     * 스터디 가입 여부
     * @param study Study
     * @param member Member
     * @return boolean
     */
    boolean isStudyMember(Study study , Member member);

    /**
     * 스터디 멤버 상태별 조회
     * @param member Member
     * @param study Study
     * @param studyMemberStatus StudyMemberStatus
     * @return Optional<StudyMember>
     */
    Optional<StudyMember> findByMemberAndStudyAndStudyMemberStatus(Member member, Study study, StudyMemberStatus studyMemberStatus);

    /**
     * 스터디 멤버 삭제
     * @param studyMemberIdx Long
     */
    void deleteById(Long studyMemberIdx);

    /**
     * 가입된 회원 수
     * @param studyIdx Long
     * @return Long
     */
    Long countApprovedMembers(Long studyIdx);

    /**
     * 스터디 가입 멤버 리스트
     * @param studyIdx Long
     * @return List<StudyJoinMemberDto>
     */
    List<StudyJoinMemberDto> getApprovedStudyMembers(Long studyIdx);
}
