package com.spinner.www.study.service;

import com.spinner.www.member.entity.Member;
import com.spinner.www.study.StudyMemberRepo;
import com.spinner.www.study.entity.StudyMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudyMemberServiceImpl implements StudyMemberService{

    private final StudyMemberRepo studyMemberRepo;

    /**
     * 스터디 멤버 저장
     * @param studyMember  StudyMember
     */
    @Override
    public void saveStudyMember(StudyMember studyMember) {
        studyMemberRepo.save(studyMember);
    }

    /**
     * memberIdx 별 스터디 멤버 조회
     * @param member Member
     * @param studyIdx Long
     * @return StudyMember
     */
    @Override
    public StudyMember getStudyMember(Member member ,Long studyIdx) {
        return studyMemberRepo.findByMemberAndStudyMemberIdx(member ,studyIdx);
    }
}
