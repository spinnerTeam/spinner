package com.spinner.www.study.service;

import com.spinner.www.member.entity.Member;
import com.spinner.www.study.entity.StudyMember;

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
}
