package com.spinner.www.study.controller;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.study.config.annotation.StudyLeaderOnly;
import com.spinner.www.study.service.StudyMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/study/member")
public class StudyMemberController {

    // 스터디원 조회
    private final StudyMemberService studyMemberService;

    // 가입 신청 멤버와 승인된 멤버 둘 다 보여 줌
    @GetMapping("/{studyidx}")
    public ResponseEntity<CommonResponse> findStudyMember(@PathVariable("studyidx") Long id) {
        return studyMemberService.findStudyMember(id);
    }

    // 가입 신청
    @PostMapping("/join/{studyidx}")
    public ResponseEntity<CommonResponse> joinRequestStudyMember(
        @PathVariable("studyidx") Long id) {
        return studyMemberService.joinRequestStudyMember(id);
    }

    // 가입 승인
    @StudyLeaderOnly
    @PutMapping("/accept/{studyidx}")
    public ResponseEntity<CommonResponse> acceptStudyMember(@PathVariable("studyidx") Long id) {
        return studyMemberService.acceptStudyMember(id);
    }

    // 가입 거절
    @StudyLeaderOnly
    @PutMapping("/disapprove/{studyidx}")
    public ResponseEntity<CommonResponse> disapproveStudyMember(@PathVariable("studyidx") Long id) {
        return studyMemberService.disapproveStudyMember(id);
    }

    // 스터디 탈퇴
    @DeleteMapping("/leave/{studyidx}")
    public ResponseEntity<CommonResponse> leaveStudyMember(@PathVariable("studyidx") Long id) {
        return studyMemberService.leaveStudyMember(id);
    }

    // 멤버 강퇴
    @StudyLeaderOnly
    @DeleteMapping("/kick/{studyidx}")
    public ResponseEntity<CommonResponse> kickStudyMember(@PathVariable("studyidx") Long id) {
        return studyMemberService.kickStudyMember(id);
    }

    // 방장 권한 넘기기
    @StudyLeaderOnly
    @PutMapping("/transfer/{studyidx}/{newleaderidx}")
    public ResponseEntity<CommonResponse> transferStudyMember(
        @PathVariable("studyidx") Long studyidx,
        @PathVariable Long newleaderidx) {
        return studyMemberService.transferStudyMember(studyidx, newleaderidx);
    }
}
