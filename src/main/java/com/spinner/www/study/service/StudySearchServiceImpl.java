package com.spinner.www.study.service;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.common.service.StudyTopicService;
import com.spinner.www.member.dto.SessionInfo;
import com.spinner.www.member.entity.Member;
import com.spinner.www.member.service.MemberService;
import com.spinner.www.study.constants.StudyMemberStatus;
import com.spinner.www.study.dto.MyStudyListDto;
import com.spinner.www.study.dto.PendingStudyMemberDto;
import com.spinner.www.study.dto.StudyListDto;
import com.spinner.www.study.repository.StudyQueryRepo;
import com.spinner.www.util.ResponseVOUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudySearchServiceImpl implements StudySearchService{

    private final SessionInfo sessionInfo;
    private final MemberService memberService;
    private final StudyMemberService studyMemberService;
    private final StudyService studyService;
    private final StudyTopicService studyTopicService;


    /**
     * 스터디 찾기 (회원 관심사별 랜덤조회)
     * @return ResponseEntity<CommonResponse>
     */
    @Override
    public ResponseEntity<CommonResponse> getSearchTopic() {

        // 관심분야 조회
        List<Long> longList = studyTopicService.findInterestCodeIdxByMember(sessionInfo.getMemberIdx());
        // 관심분야별 랜덤으로 스터디 노출
        List<StudyListDto> response = studyService.findInterestCodeByStudy(longList);

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.CREATED);
    }
}
