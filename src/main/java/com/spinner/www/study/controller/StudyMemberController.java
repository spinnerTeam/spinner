package com.spinner.www.study.controller;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.study.config.annotation.StudyLeaderOnly;
import com.spinner.www.study.io.StudyMemberJoinRequest;
import com.spinner.www.study.service.StudyMemberService;
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
@RequestMapping("/study/member")
public class StudyMemberController {

    private final StudyMemberService studyMemberService;

    // 스터디 멤버 보기
    @GetMapping("/{studyIdx}")
    public ResponseEntity<CommonResponse> findStudyMember(@PathVariable("studyIdx") Long id) {
        return studyMemberService.findStudyMember(id);
    }

    // 가입 신청
    @PostMapping("/join/{studyIdx}")
    public ResponseEntity<CommonResponse> joinRequestStudyMember(
        @PathVariable("studyIdx") Long id, @RequestBody StudyMemberJoinRequest studyMemberJoinRequest) {
        return studyMemberService.joinRequestStudyMember(id, studyMemberJoinRequest);
    }

    // 스터디 리더 멤버 관리 : 가입 신청 멤버와 승인된 멤버 둘 다 보여 줌
    @StudyLeaderOnly
    @GetMapping("/manage/{studyIdx}")
    public ResponseEntity<CommonResponse> manageStudyMember(@PathVariable("studyIdx") Long id) {
        return studyMemberService.manageStudyMember(id);
    }

    // 가입 승인
    @StudyLeaderOnly
    @PutMapping("/accept/{studyIdx}/{memberIdx}")
    public ResponseEntity<CommonResponse> acceptStudyMember(@PathVariable("studyIdx") Long id, @PathVariable("memberIdx") Long memberIdx) {
        return studyMemberService.acceptStudyMember(id, memberIdx);
    }

    // 가입 거절
    @StudyLeaderOnly
    @PutMapping("/disapprove/{studyIdx}/{memberIdx}")
    public ResponseEntity<CommonResponse> disapproveStudyMember(@PathVariable("studyIdx") Long id, @PathVariable("memberIdx") Long memberIdx) {
        return studyMemberService.disapproveStudyMember(id, memberIdx);
    }

    // 스터디 탈퇴
    @DeleteMapping("/leave/{studyIdx}}")
    public ResponseEntity<CommonResponse> leaveStudyMember(@PathVariable("studyIdx") Long id) {
        return studyMemberService.leaveStudyMember(id);
    }

    // 멤버 강퇴
    @StudyLeaderOnly
    @DeleteMapping("/kick/{studyIdx}/{memberIdx}")
    public ResponseEntity<CommonResponse> kickStudyMember(@PathVariable("studyIdx") Long id, @PathVariable("memberIdx") Long memberIdx) {
        return studyMemberService.kickStudyMember(id, memberIdx);
    }

    // 방장 권한 넘기기
    @StudyLeaderOnly
    @PutMapping("/transfer/{studyIdx}/{newLeaderIdx}")
    public ResponseEntity<CommonResponse> transferLeaderStudyMember(
        @PathVariable("studyIdx") Long studyIdx,
        @PathVariable Long newLeaderIdx) {
        return studyMemberService.transferLeaderStudyMember(studyIdx, newLeaderIdx);
    }
}
