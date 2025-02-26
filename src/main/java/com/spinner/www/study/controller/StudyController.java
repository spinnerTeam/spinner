package com.spinner.www.study.controller;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.study.config.annotation.StudyLeaderOnly;
import com.spinner.www.study.entity.Study;
import com.spinner.www.study.io.StudyCreateRequest;
import com.spinner.www.study.io.StudyUpdateRequest;
import com.spinner.www.study.service.StudyService;
import jakarta.validation.Valid;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/study")
public class StudyController {

    private final StudyService studyService;

    @GetMapping("/list")
    public ResponseEntity<CommonResponse> getStudyList() {
        return studyService.getStudyList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse> getStudy(@PathVariable Long id) {
        return studyService.getStudy(id);
    }

    @PostMapping
    public ResponseEntity<CommonResponse> createStudy(@RequestBody @Valid StudyCreateRequest studyCreateRequest) {
        return studyService.createStudy(studyCreateRequest);
    }

    @PutMapping("/{id}")
    @StudyLeaderOnly
    public ResponseEntity<CommonResponse> updateStudy(@PathVariable Long id, @RequestBody @Valid StudyUpdateRequest studyUpdateRequest) {
        return studyService.updateStudy(id, studyUpdateRequest);
    }

    @DeleteMapping("/{id}")
    @StudyLeaderOnly
    public ResponseEntity<CommonResponse> deleteStudy(@PathVariable Long id) {
        return studyService.deleteStudy(id);
    }
}
