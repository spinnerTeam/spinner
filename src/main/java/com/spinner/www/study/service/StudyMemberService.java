package com.spinner.www.study.service;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.study.io.StudyMemberJoinRequest;
import org.springframework.http.ResponseEntity;

public interface StudyMemberService {

    ResponseEntity<CommonResponse> findStudyMember(Long id);

    ResponseEntity<CommonResponse> joinRequestStudyMember(Long id, StudyMemberJoinRequest studyMemberJoinRequest);

    ResponseEntity<CommonResponse> acceptStudyMember(Long id, Long memberIdx);

    ResponseEntity<CommonResponse> disapproveStudyMember(Long id, Long memberIdx);

    ResponseEntity<CommonResponse> leaveStudyMember(Long id);

    ResponseEntity<CommonResponse> kickStudyMember(Long id, Long memberIdx);

    ResponseEntity<CommonResponse> transferLeaderStudyMember(Long studyidx, Long newleaderidx);
}
