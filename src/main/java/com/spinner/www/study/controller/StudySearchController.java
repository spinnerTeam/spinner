package com.spinner.www.study.controller;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.study.service.StudyFacadeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class StudySearchController {

    private final StudyFacadeService studyFacadeService;

    /**
     * 스터디 찾기 (회원 관심사별 랜덤조회)
     * @return ResponseEntity<CommonResponse>
     */
    @Operation(
            summary = "스터디 찾기 랜덤 조회 API",
            description = "스터디 찾기 (회원 관심사별 랜덤조회) 입니다.  "
    )
    @GetMapping("/topic")
    public ResponseEntity<CommonResponse> getSearchTopic(){
        return studyFacadeService.getSearchTopic();
    }
}
