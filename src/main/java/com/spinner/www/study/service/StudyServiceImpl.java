package com.spinner.www.study.service;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.study.io.StudyCreateRequest;
import com.spinner.www.study.io.StudyUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class StudyServiceImpl implements StudyService {

    @Override
    public ResponseEntity<CommonResponse> getStudyList() {
        return null;
    }

    @Override
    public ResponseEntity<CommonResponse> getStudy(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<CommonResponse> createStudy(StudyCreateRequest studyCreateRequest) {
        return null;
    }

    @Override
    public ResponseEntity<CommonResponse> updateStudy(Long id,
        StudyUpdateRequest studyUpdateRequest) {
        return null;
    }

    @Override
    public ResponseEntity<CommonResponse> deleteStudy(Long id) {
        return null;
    }
}
