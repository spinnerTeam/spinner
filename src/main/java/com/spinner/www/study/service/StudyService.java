package com.spinner.www.study.service;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.study.io.CreateStudy;
import com.spinner.www.study.io.UpdateStudyIo;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface StudyService {

    /**
     * 스터디 생성
     * @param createStudy CreateStudy
     * @return ResponseEntity<CommonResponse>
     */
    ResponseEntity<CommonResponse> createStudy(CreateStudy createStudy) throws IOException;

    /**
     * 스터디 수정
     * @param updateStudy UpdateStudyIo
     * @return ResponseEntity<CommonResponse>
     */
    ResponseEntity<CommonResponse> updateStudy(UpdateStudyIo updateStudy);
}
