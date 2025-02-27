package com.spinner.www.study.service;

import com.spinner.www.common.io.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class StudyMemberServiceImpl implements StudyMemberService {

    @Override
    public ResponseEntity<CommonResponse> findStudyMember(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<CommonResponse> joinRequestStudyMember(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<CommonResponse> acceptStudyMember(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<CommonResponse> disapproveStudyMember(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<CommonResponse> leaveStudyMember(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<CommonResponse> kickStudyMember(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<CommonResponse> transferStudyMember(Long studyidx, Long newleaderidx) {
        return null;
    }
}
