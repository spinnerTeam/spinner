package com.spinner.www.study.service;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.study.io.StudyCreateRequest;
import com.spinner.www.study.io.StudyUpdateRequest;
import java.io.IOException;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface StudyService {

    ResponseEntity<CommonResponse> getStudyList();

    ResponseEntity<CommonResponse> getStudy(Long id);

    ResponseEntity<CommonResponse> createStudy(StudyCreateRequest studyCreateRequest);

    ResponseEntity<CommonResponse> uploadStudyInfoFile(Long id, List<MultipartFile> files) throws IOException;

    ResponseEntity<CommonResponse> updateStudy(Long id, StudyUpdateRequest studyUpdateRequest);

    ResponseEntity<CommonResponse> deleteStudy(Long id);

}
