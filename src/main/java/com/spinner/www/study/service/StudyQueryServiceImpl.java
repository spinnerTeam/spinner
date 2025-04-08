package com.spinner.www.study.service;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.member.entity.Member;
import com.spinner.www.study.constants.StudyMemberStatus;
import com.spinner.www.study.dto.MyStudyListDto;
import com.spinner.www.study.dto.PendingStudyMemberDto;
import com.spinner.www.study.repository.StudyQueryRepo;
import com.spinner.www.util.ResponseVOUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudyQueryServiceImpl implements StudyQueryService{

    private final StudyQueryRepo studyQueryRepo;

    /**
     * 나의 스터디 (참여스터디, 가입대기스터디, 종료 스터디)
     * @param studyMemberStatus StudyMemberStatus
     * @param member Member
     * @return List<MyStudyListDto>
     */
    @Override
    public List<MyStudyListDto> joinedStudy(StudyMemberStatus studyMemberStatus, Member member) {
        return studyQueryRepo.joinedStudy(studyMemberStatus, member);
    }

    /**
     * 스터디 멤버관리 (신청인원, 참여인원)
     * @param studyIdx Long
     * @param studyStatus String
     * @return List<PendingStudyMemberDto>
     */
    @Override
    public List<PendingStudyMemberDto> pendingStudyMember(Long studyIdx, String studyStatus) {
        return studyQueryRepo.pendingStudyMember(studyIdx, studyStatus);
    }
}
