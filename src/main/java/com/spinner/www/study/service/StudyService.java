package com.spinner.www.study.service;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.study.io.CreateStudy;
import org.springframework.http.ResponseEntity;

public interface StudyService {

    /**
     * 스터디 생성
     * @param createStudy CreateStudy
     * @return ResponseEntity<CommonResponse>
     */
    ResponseEntity<CommonResponse> createStudy(CreateStudy createStudy);
}
