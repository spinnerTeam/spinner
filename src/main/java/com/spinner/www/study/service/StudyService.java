package com.spinner.www.study.service;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.study.io.StudyCreateRequest;
import com.spinner.www.study.io.StudyUpdateRequest;
import org.springframework.http.ResponseEntity;

public interface StudyService {

    ResponseEntity<CommonResponse> getStudyList();

    ResponseEntity<CommonResponse> getStudy(Long id);

    ResponseEntity<CommonResponse> createStudy(StudyCreateRequest studyCreateRequest);

    ResponseEntity<CommonResponse> updateStudy(Long id, StudyUpdateRequest studyUpdateRequest);

    ResponseEntity<CommonResponse> deleteStudy(Long id);
}
