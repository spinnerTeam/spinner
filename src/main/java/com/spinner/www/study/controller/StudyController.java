package com.spinner.www.study.controller;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.study.config.annotation.StudyLeaderOnly;
import com.spinner.www.study.entity.Study;
import com.spinner.www.study.io.StudyCreateRequest;
import com.spinner.www.study.io.StudyUpdateRequest;
import com.spinner.www.study.service.StudyService;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/study")
public class StudyController {

    private final StudyService studyService;

    // 스터디 조회
    @GetMapping("/list")
    public ResponseEntity<CommonResponse> getStudyList() {
        return studyService.getStudyList();
    }

    // 내 스터디 조회
    @GetMapping("/mylist")
    public ResponseEntity<CommonResponse> getMyStudyList() {
        return studyService.getStudyList();
    }

    // 스터디 상세
    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse> getStudy(@PathVariable Long id) {
        return studyService.getStudy(id);
    }

    @PostMapping
    public ResponseEntity<CommonResponse> createStudy(
        @RequestBody @Valid StudyCreateRequest studyCreateRequest) {
        return studyService.createStudy(studyCreateRequest);
    }

    @PostMapping("/upload/{id}")
    public ResponseEntity<CommonResponse> uploadFileStudy(@PathVariable Long id,
        @RequestParam("multiFile") List<MultipartFile> files) throws IOException {
        return studyService.uploadStudyInfoFile(id, files);
    }

    @PutMapping("/{id}")
    @StudyLeaderOnly
    public ResponseEntity<CommonResponse> updateStudy(@PathVariable Long id,
        @RequestBody @Valid StudyUpdateRequest studyUpdateRequest) {
        return studyService.updateStudy(id, studyUpdateRequest);
    }

    @DeleteMapping("/{id}")
    @StudyLeaderOnly
    public ResponseEntity<CommonResponse> deleteStudy(@PathVariable Long id) {
        return studyService.deleteStudy(id);
    }
}
