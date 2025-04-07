package com.spinner.www.study;

import com.spinner.www.member.entity.Member;
import com.spinner.www.study.entity.StudyMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyMemberRepo extends JpaRepository<StudyMember, Long> {

    /**
     * memberIdx 별 스터디 멤버 조회
     * @param member Member
     * @param studyIdx Long
     * @return StudyMember
     */
    StudyMember findByMemberAndStudyMemberIdx(Member member ,Long studyIdx);
}
