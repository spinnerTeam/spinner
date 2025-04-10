package com.spinner.www.study.service;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.member.entity.Member;
import com.spinner.www.study.constants.StudyMemberStatus;
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
     * @param studyMemberIdx Long
     * @param member Member
     * @return boolean
     */
    boolean existsByStudyMemberIdxAndMember(Long studyMemberIdx, Member member);



    /**
     * 스터디 가입 여부
     * @param studyIdx Long
     * @param member Member
     * @return boolean
     */
    boolean isStudyMember(Long studyIdx , Member member);

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
}
