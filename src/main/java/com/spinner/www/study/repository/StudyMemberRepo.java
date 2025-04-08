package com.spinner.www.study.repository;

import com.spinner.www.member.entity.Member;
import com.spinner.www.study.constants.StudyMemberStatus;
import com.spinner.www.study.entity.Study;
import com.spinner.www.study.entity.StudyMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudyMemberRepo extends JpaRepository<StudyMember, Long> {

    /**
     * memberIdx 별 스터디 멤버 조회
     * @param member Member
     * @param studyIdx Long
     * @return StudyMember
     */
    StudyMember findByMemberAndStudyMemberIdx(Member member ,Long studyIdx);

    /**
     * 스터디 가입 여부
     * @param studyMemberIdx Long
     * @param member Member
     * @return boolean
     */
    boolean existsByStudyMemberIdxAndMember(Long studyMemberIdx, Member member);

    /**
     * member와 study로 studyMember 객체 조회
     * @param member Member
     * @param study Study
     * @return StudyMember
     */
    StudyMember findByMemberAndStudy(Member member, Study study);

    Optional<StudyMember> findByMemberAndStudyAndStudyMemberStatus(Member member, Study study, StudyMemberStatus studyMemberStatus);
}
