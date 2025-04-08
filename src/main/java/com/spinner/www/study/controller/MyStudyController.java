package com.spinner.www.study.controller;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.study.service.StudyMemberService;
import com.spinner.www.study.service.StudyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/myStudy")
public class MyStudyController {

    private final StudyMemberService studyMemberService;

    /**
     * 나의 스터디 (참여스터디, 가입대기스터디, 종료 스터디)
     * @param memberIdx
     * @param studyStatus
     * @return
     */
    @Operation(
            summary = "나의 스터디 API (MPG_002)"
    )
    @Parameters({
            @Parameter(name = "studyStatus", description = "WAITING 승인대기, APPROVED 승인"),
            @Parameter(name = "memberIdx", description = "멤버 PK")
    })
    //    @PreAuthorize("isAuthenticated()")
    @GetMapping("/joined/{studyStatus}/{memberIdx}")
    public ResponseEntity<CommonResponse> getJoinedStudy(
            @PathVariable("memberIdx") Long memberIdx,
            @PathVariable("studyStatus") String studyStatus){

        return studyMemberService.findByStudyMemberStatusAndMember(studyStatus, memberIdx);
    }

    /**
     * 스터디 가입취소 & 스터디 가입 거절
     * @param studyIdx Long
     * @return ResponseEntity<CommonResponse>
     */
    @DeleteMapping({"/cancel/{studyIdx}", "/cancel/{studyIdx}/{memberIdx}"})
    public ResponseEntity<CommonResponse> cancelStudy(
            @PathVariable("studyIdx") Long studyIdx,
            @PathVariable(value = "memberIdx", required = false)  Long memberIdx){
        // 가입거절
        if(memberIdx != null){
            return studyMemberService.cancelStudyMember(studyIdx, memberIdx);
        }
        // 가입취소
        return studyMemberService.cancelStudy(studyIdx);
    }

    /**
     * 스터디 멤버관리(신청인원, 참여인원)
     * @param studyIdx Long
     * @param studyStatus String
     * @return ResponseEntity<CommonResponse>
     */
    @Operation(
            summary = "스터디 멤버 관리 조회 API"
    )
    @Parameters({
            @Parameter(name = "studyIdx", description = "스터디 PK"),
            @Parameter(name = "studyStatus", description = "WAITING 승인대기, APPROVED 승인")
    })
    //    @PreAuthorize("isAuthenticated()")
    @GetMapping("/studies/{studyIdx}/members/{studyStatus}")
    public ResponseEntity<CommonResponse> getPendingStudyMember(@PathVariable("studyIdx") Long studyIdx, @PathVariable("studyStatus") String studyStatus){
        return studyMemberService.findByStudyAndStudyStatus(studyIdx, studyStatus);
    }

    /**
     * 스터디 가입 승인
     * @param studyIdx
     * @param memberIdx
     * @return
     */
    @Operation(
            summary = "스터디 멤버 승인 API"
    )
    @Parameters({
            @Parameter(name = "studyIdx", description = "스터디 PK"),
            @Parameter(name = "memberIdx", description = "회원 PK")
    })
    @PostMapping("/joined/{studyIdx}/{memberIdx}")
    public ResponseEntity<CommonResponse> joinedStudy(@PathVariable("studyIdx") Long studyIdx, @PathVariable("memberIdx") Long memberIdx){
        return studyMemberService.updateStudyMember(studyIdx, memberIdx);
    }
}
