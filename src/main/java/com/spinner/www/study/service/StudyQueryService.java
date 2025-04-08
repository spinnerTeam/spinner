package com.spinner.www.study.service;

import com.spinner.www.member.entity.Member;
import com.spinner.www.study.constants.StudyMemberStatus;
import com.spinner.www.study.dto.MyStudyListDto;
import com.spinner.www.study.dto.PendingStudyMemberDto;

import java.util.List;

public interface StudyQueryService {

    /**
     * 나의 스터디 (참여스터디, 가입대기스터디, 종료 스터디)
     * @param studyMemberStatus StudyMemberStatus
     * @param member Member
     * @return List<MyStudyListDto>
     */
    List<MyStudyListDto> joinedStudy(StudyMemberStatus studyMemberStatus, Member member);

    /**
     * 스터디 멤버관리 (신청인원, 참여인원)
     * @param studyIdx Long
     * @param studyStatus String
     * @return List<PendingStudyMemberDto>
     */
    List<PendingStudyMemberDto> pendingStudyMember(Long studyIdx, String studyStatus);


}
