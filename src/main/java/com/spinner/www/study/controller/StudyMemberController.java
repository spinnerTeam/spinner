package com.spinner.www.study.controller;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.study.io.JoinStudyMember;
import com.spinner.www.study.service.StudyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/studyMember")
public class StudyMemberController {

    private final StudyService studyService;

    /**
     * 스터디 가입 신청 > 로그인 체크
     * @param studyIdx
     * @param studyMember
     * @return
     */
    @Operation(
            summary = "스터디 가입 API",
            description = "스터디 가입입니다.  "
    )
    @Parameters({
            @Parameter(name = "studyIdx", description = "스터디 PK"),
            @Parameter(name = "info", description = "소개"),

    })
    //    @PreAuthorize("isAuthenticated()")
    @PostMapping("/join/{studyIdx}")
    public ResponseEntity<CommonResponse> joinStudyMember(@PathVariable("studyIdx") Long studyIdx, @RequestBody JoinStudyMember studyMember){
        return studyService.joinStudyMember(studyIdx, studyMember);
    }
}
