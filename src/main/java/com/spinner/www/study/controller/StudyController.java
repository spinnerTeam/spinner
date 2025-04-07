package com.spinner.www.study.controller;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.study.io.CreateStudy;
import com.spinner.www.study.service.StudyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
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

    /**
     * 스터디 생성
     * @param createStudy CreateStudy
     * @return ResponseEntity<CommonResponse>
     * @throws IOException IOException
     */
    @Operation(
            summary = "스터디 생성 API",
            description = "스터디 생성입니다. 인원은 최소 2명 이상 최대 15명 이하입니다. "
    )
    @Parameters({
            @Parameter(name = "studyName", description = "스터디명"),
            @Parameter(name = "file", description = "파일"),
            @Parameter(name = "studyInfo", description = "스터디소개"),
            @Parameter(name = "studyMaxPeople", description = "최대인원"),
            @Parameter(name = "studyTopicIdx", description = "스터디주제"),
    })
//    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public ResponseEntity<CommonResponse> createStudy(@ModelAttribute CreateStudy createStudy) throws IOException {
        return studyService.createStudy(createStudy);
    }
}
