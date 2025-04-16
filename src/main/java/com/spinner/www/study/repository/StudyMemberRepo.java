package com.spinner.www.study.repository;

import com.spinner.www.member.entity.Member;
import com.spinner.www.study.constants.StudyMemberRole;
import com.spinner.www.study.constants.StudyMemberStatus;
import com.spinner.www.study.entity.Study;
import com.spinner.www.study.entity.StudyMember;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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
     * @param study Study
     * @param member Member
     * @return boolean
     */
    boolean existsByStudyAndMember(Study study, Member member);

    /**
     * member 와 study 로 studyMember 객체 조회
     * @param member Member
     * @param study Study
     * @return StudyMember
     */
    StudyMember findByMemberAndStudy(Member member, Study study);

    /**
     * 스터디 멤버 상태별 조회
     * @param member Member
     * @param study Study
     * @param studyMemberStatus StudyMemberStatus
     * @return Optional<StudyMember>
     */
    Optional<StudyMember> findByMemberAndStudyAndStudyMemberStatus(Member member, Study study, StudyMemberStatus studyMemberStatus);

    /**
     * 스터디, 회원, 권한 기준으로 해당 역할의 멤버 존재 여부 확인
     *
     * @param study 스터디 엔티티
     * @param member 멤버 엔티티
     * @param studyMemberRole 확인할 멤버 역할 (예: LEADER)
     * @return 해당 조건에 맞는 멤버가 존재하면 true, 아니면 false
     */
    boolean existsByStudyAndMemberAndStudyMemberRole(Study study, Member member, StudyMemberRole studyMemberRole);

    /**
     * 스터디 멤버 ID와 스터디 기준으로 스터디 멤버를 조회합니다.
     *
     * @param studyMemberIdx 조회할 스터디 멤버의 고유 ID
     * @param study 해당 멤버가 소속된 스터디 엔티티
     * @return 조건에 일치하는 스터디 멤버 엔티티, 없으면 null
     */
    StudyMember findByStudyMemberIdxAndStudy(Long studyMemberIdx, Study study);

    /**
     * 스터디 탈퇴일이 지난 데이터 삭제
     * @param date 스터디 탈퇴일
     */
    @Modifying
    @Query("DELETE FROM StudyMember sm WHERE sm.isStudyMemberRemoved = true AND sm.studyMemberStatus = 'WITHDRAWN' AND sm.studyMemberWithdrawalDate < :date")
    void deleteWithdrawnMembersBefore(@Param("date") LocalDate date);
}
