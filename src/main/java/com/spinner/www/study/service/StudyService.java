package com.spinner.www.study.service;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.study.entity.Study;
import com.spinner.www.study.io.CreateStudy;
import com.spinner.www.study.io.UpdateStudyIo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

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

    /**
     * 스터디 파일 수정
     * @param studyIdx Long
     * @param file MultipartFile
     * @return ResponseEntity<CommonResponse>
     */
    ResponseEntity<CommonResponse> updateStrudyFile(Long studyIdx, MultipartFile file) throws IOException;

}
