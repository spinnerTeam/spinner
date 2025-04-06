package com.spinner.www.study.controller;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.study.io.CreateStudy;
import com.spinner.www.study.service.StudyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/study")
public class StudyController {

    private final StudyService studyService;

//    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public ResponseEntity<CommonResponse> createStudy(@ModelAttribute CreateStudy createStudy) throws IOException {
        return studyService.createStudy(createStudy);
    }
}
