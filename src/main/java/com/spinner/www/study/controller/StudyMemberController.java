package com.spinner.www.study.controller;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.study.io.JoinStudyMember;
import com.spinner.www.study.service.StudyFacadeService;
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

    private final StudyFacadeService studyFacadeService;

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
        return studyFacadeService.joinStudyMember(studyIdx, studyMember);
    }


    @Operation(
            summary = "스터디 탈퇴시키기 API"
    )
    @Parameters({
            @Parameter(name = "studyIdx", description = "스터디 PK"),
            @Parameter(name = "studyMemberIdx", description = "스터디 멤버 PK")
    })
    //    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/studies/{studyIdx}/members/{studyMemberIdx}")
    public ResponseEntity<CommonResponse> withdrawStudyMember(@PathVariable("studyIdx") Long studyIdx, @PathVariable("studyMemberIdx") Long studyMemberIdx){
        return studyFacadeService.deleteStudyMember(studyIdx, studyMemberIdx);
    }

    @Operation(
            summary = "스터디 탈퇴 API"
    )
    @Parameters({
            @Parameter(name = "studyIdx", description = "스터디 PK")
    })
    //    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/studies/{studyIdx}/members")
    public ResponseEntity<CommonResponse> withdrawStudy(@PathVariable("studyIdx") Long studyIdx){
        return studyFacadeService.withdrawStudy(studyIdx);
    }
}
