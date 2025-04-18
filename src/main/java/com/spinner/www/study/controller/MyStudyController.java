package com.spinner.www.study.controller;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.study.service.StudyFacadeService;
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

    private final StudyFacadeService studyFacadeService;

    /**
     * 나의 스터디 (참여스터디, 가입대기스터디, 종료 스터디)
     * @param memberIdx Long
     * @param studyStatus String
     * @return ResponseEntity<CommonResponse>
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

        return studyFacadeService.findByStudyMemberStatusAndMember(studyStatus, memberIdx);
    }

    /**
     * 스터디 가입취소 & 스터디 가입 거절
     * @param studyIdx Long
     * @return ResponseEntity<CommonResponse>
     */
    @Operation(
            summary = "스터디 가입취소 & 스터디 가입 거절 API"
    )
    @Parameters({
            @Parameter(name = "studyIdx", description = "스터디 PK"),
            @Parameter(name = "memberIdx", description = "memberIdx PK (필수값 x)")
    })
    @DeleteMapping({"/cancel/{studyIdx}", "/cancel/{studyIdx}/{memberIdx}"})
    public ResponseEntity<CommonResponse> cancelStudy(
            @PathVariable("studyIdx") Long studyIdx,
            @PathVariable(value = "memberIdx", required = false)  Long memberIdx){
        // 가입거절
        if(memberIdx != null){
            return studyFacadeService.cancelStudyMember(studyIdx, memberIdx);
        }
        // 가입취소
        return studyFacadeService.cancelStudy(studyIdx);
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
        return studyFacadeService.findByStudyAndStudyStatus(studyIdx, studyStatus);
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
        return studyFacadeService.updateStudyMember(studyIdx, memberIdx);
    }

    /**
     * mst_009_스터디 멤버 조회
     * @param studyIdx Long
     * @return ResponseEntity<CommonResponse>
     */
    @Operation(
            summary = "스터디 멤버 조회 API (mst_009_스터디 멤버)"
    )
    @Parameters({
            @Parameter(name = "studyIdx", description = "스터디 PK")
    })
    @GetMapping("/{studyIdx}/studyMembers")
    public ResponseEntity<CommonResponse> getStudyMembers(@PathVariable("studyIdx") Long studyIdx){
        return studyFacadeService.findStudyMembersByStudyIdx(studyIdx);
    }

    /**
     * [스터디 방장 위임 API]
     *
     * 스터디의 기존 방장이 특정 스터디 멤버에게 방장 권한을 위임합니다.
     *
     * <주의>
     * - 호출자는 반드시 현재 스터디의 방장이어야 합니다.
     * - 위임 대상은 해당 스터디에 참여 중인 멤버여야 합니다.
     *
     * @param studyIdx         방장을 위임할 스터디의 ID
     * @param studyMemberIdx   방장 권한을 넘겨받을 스터디 멤버 ID
     * @return                 권한 위임 성공 여부
     */
    @Operation(
            summary = "스터디 방장 권한 위임 API"
    )
    @Parameters({
            @Parameter(name = "studyIdx", description = "스터디 PK"),
            @Parameter(name = "studyMemberIdx", description = "위임받을 멤버 PK")
    })
    @PutMapping("/studies/{studyIdx}/transfer-leader/{studyMemberIdx}")
    public ResponseEntity<CommonResponse> transferLeader(@PathVariable("studyIdx") Long studyIdx, @PathVariable("studyMemberIdx") Long studyMemberIdx){
        return studyFacadeService.transferLeader(studyIdx, studyMemberIdx);
    }
}
