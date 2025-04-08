package com.spinner.www.study.service;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.constants.CommonResultCode;
import com.spinner.www.member.dto.SessionInfo;
import com.spinner.www.member.entity.Member;
import com.spinner.www.member.service.MemberService;
import com.spinner.www.study.constants.StudyMemberStatus;
import com.spinner.www.study.entity.Study;
import com.spinner.www.study.repository.StudyMemberRepo;
import com.spinner.www.study.entity.StudyMember;
import com.spinner.www.study.repository.StudyQueryRepo;
import com.spinner.www.study.repository.StudyRepo;
import com.spinner.www.util.ResponseVOUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudyMemberServiceImpl implements StudyMemberService{

    private final StudyMemberRepo studyMemberRepo;
    private final SessionInfo sessionInfo;
    private final MemberService memberService;
    private final StudyQueryRepo studyQueryRepo;
    private final StudyRepo studyRepo;

    /**
     * 스터디 멤버 저장
     * @param studyMember  StudyMember
     */
    @Override
    public void saveStudyMember(StudyMember studyMember) {
        studyMemberRepo.save(studyMember);
    }

    /**
     * memberIdx 별 스터디 멤버 조회
     * @param member Member
     * @param studyIdx Long
     * @return StudyMember
     */
    @Override
    public StudyMember getStudyMember(Member member ,Long studyIdx) {
        return studyMemberRepo.findByMemberAndStudyMemberIdx(member ,studyIdx);
    }

    /**
     * 스터디 가입 여부
     * @param studyMemberIdx Long
     * @param member Member
     * @return boolean
     */
    @Override
    public boolean existsByStudyMemberIdxAndMember(Long studyMemberIdx, Member member) {
        return studyMemberRepo.existsByStudyMemberIdxAndMember(studyMemberIdx, member);
    }

    /**
     * 나의 스터디 (참여스터디, 가입대기스터디, 종료 스터디)
     * @param memberIdx Long
     * @param studyMemberStatus String
     * @return ResponseEntity<CommonResponse>
     */
    @Override
    public ResponseEntity<CommonResponse> findByStudyMemberStatusAndMember(String studyMemberStatus, Long memberIdx) {
        // 상태값
        StudyMemberStatus status = StudyMemberStatus.valueOf(studyMemberStatus);
        // 회원
        Member member = memberService.getMember(memberIdx);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(studyQueryRepo.joinedStudy(status, member)), HttpStatus.OK);
    }

    /**
     * 스터디 멤버관리(신청인원, 참여인원)
     * @param studyIdx Long
     * @param studyStatus String
     * @return ResponseEntity<CommonResponse>
     */
    @Override
    public ResponseEntity<CommonResponse> findByStudyAndStudyStatus(Long studyIdx, String studyStatus) {
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(studyQueryRepo.pendingStudyMember(studyIdx, studyStatus)), HttpStatus.OK);
    }

    /**
     *  studyMember 객체 조회
     * @param member Member
     * @param study Study
     * @return StudyMember
     */
    private StudyMember getStudyMember(Member member, Study study){
        return studyMemberRepo.findByMemberAndStudy(member, study);
    }

    /**
     * 스터디 가입 승인
     * @param studyIdx
     * @param memberIdx
     * @return
     */
    @Override
    @Transactional
    public ResponseEntity<CommonResponse> updateStudyMember(Long studyIdx, Long memberIdx) {

        // 스터디 조회
        Optional<Study> study = studyRepo.findById(studyIdx);
        if (study.isEmpty()){
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.NOT_FOUND_STUDY),HttpStatus.OK);
        }

        Member member = memberService.getMember(memberIdx);
        StudyMember studyMember = getStudyMember(member, study.get());
        studyMember.updateJoinStudyMember(StudyMemberStatus.APPROVED);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse("승인이 완료되었습니다."), HttpStatus.OK);
    }

    /**
     * 스터디 가입취소
     * @param studyIdx Long
     * @return ResponseEntity<CommonResponse>
     */
    @Override
    public ResponseEntity<CommonResponse> cancelStudy(Long studyIdx) {

        Optional<Study> study = studyRepo.findById(studyIdx);
        if (study.isEmpty()){
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.NOT_FOUND_STUDY),HttpStatus.OK);
        }

        Member member = memberService.getMember(sessionInfo.getMemberIdx());
        if(member == null){
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.NO_LOGIN),HttpStatus.OK);
        }

        // 가입신청 했는지
        Optional<StudyMember> studyMember = studyMemberRepo.findByMemberAndStudyAndStudyMemberStatus(member, study.get(), StudyMemberStatus.WAITING);

        // 가입취소
        if(studyMember.isEmpty()){
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(5005, "스터디 가입 이력이 없습니다."),HttpStatus.OK);
        }
        studyMemberRepo.deleteById(studyMember.get().getStudyMemberIdx());
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse("가입취소 완료되었습니다."),HttpStatus.OK);
    }

    /**
     * 스터디 가입 거절
     * @param studyIdx Long
     * @param memberIdx Long
     * @return ResponseEntity<CommonResponse>
     */
    @Override
    public ResponseEntity<CommonResponse> cancelStudyMember(Long studyIdx, Long memberIdx) {

        Optional<Study> study = studyRepo.findById(studyIdx);
        if (study.isEmpty()){
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.NOT_FOUND_STUDY),HttpStatus.OK);
        }

        Member member = memberService.getMember(memberIdx);
        if(member == null){
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.DATA_NOT_FOUND),HttpStatus.OK);
        }
        StudyMember studyMember = getStudyMember(member, study.get());
        studyMemberRepo.deleteById(studyMember.getStudyMemberIdx());
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse("가입거절 완료되었습니다."),HttpStatus.OK);
    }
}
