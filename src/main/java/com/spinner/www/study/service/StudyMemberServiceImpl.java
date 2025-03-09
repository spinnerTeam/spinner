package com.spinner.www.study.service;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.constants.CommonResultCode;
import com.spinner.www.member.dto.SessionInfo;
import com.spinner.www.member.entity.Member;
import com.spinner.www.member.repository.MemberRepo;
import com.spinner.www.study.constants.StudyMemberStatusType;
import com.spinner.www.study.constants.StudyStatusType;
import com.spinner.www.study.dto.StudyMemberJoinDto;
import com.spinner.www.study.dto.StudyMemberSelectDto;
import com.spinner.www.study.entity.Study;
import com.spinner.www.study.entity.StudyMember;
import com.spinner.www.study.io.StudyMemberJoinRequest;
import com.spinner.www.study.mapper.StudyMemberMapper;
import com.spinner.www.study.repository.StudyMemberQueryRepo;
import com.spinner.www.study.repository.StudyMemberRepo;
import com.spinner.www.study.repository.StudyRepo;
import com.spinner.www.util.ResponseVOUtils;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyMemberServiceImpl implements StudyMemberService {

    private final StudyMemberRepo studyMemberRepo;
    private final StudyRepo studyRepo;
    private final MemberRepo memberRepo;
    private final StudyMemberQueryRepo studyMemberQueryRepo;
    private final SessionInfo sessionInfo;
    private final StudyMemberMapper studyMemberMapper;

    @Override
    public ResponseEntity<CommonResponse> findStudyMember(Long id) {
        Study study = getStudyOrElseThrow(id);
        List<StudyMemberSelectDto> studyMemberList = studyMemberQueryRepo.findStudyMember(study);

        if (studyMemberList.isEmpty()) {
            return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse("가입된 회원이 없습니다."),
                HttpStatus.OK);
        }

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(studyMemberList),
            HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<CommonResponse> joinRequestStudyMember(Long id, StudyMemberJoinRequest studyMemberJoinRequest) {

        Study study = getStudyOrElseThrow(id);

        if (study.getStudyStatusType().equals(StudyStatusType.END)) {
            return new ResponseEntity<>(
                ResponseVOUtils.getFailResponse(CommonResultCode.BAD_REQUEST.code(),
                    "종료된 스터디입니다."),
                HttpStatus.BAD_REQUEST);
        }

        Member member = getLoginMemberOrElseThrow();
        Optional<StudyMember> joinStudyMember = studyMemberRepo.findByStudyAndMember(study, member);

        if (joinStudyMember.isPresent()) {
            if ((joinStudyMember.get().getStudyMemberStatus().equals(StudyMemberStatusType.JOIN))) {
                return new ResponseEntity<>(
                    ResponseVOUtils.getFailResponse(CommonResultCode.BAD_REQUEST.code(),
                        "이미 가입한 스터디입니다."),
                    HttpStatus.BAD_REQUEST);

            } else if (joinStudyMember.get().getStudyMemberStatus().equals(StudyMemberStatusType.WAITING)) {
                return new ResponseEntity<>(
                    ResponseVOUtils.getFailResponse(CommonResultCode.BAD_REQUEST.code(),
                        "이미 가입 요청된 스터디입니다."),
                    HttpStatus.BAD_REQUEST);
            }
        }

        StudyMemberJoinDto studyMemberJoinDto = studyMemberMapper.toStudyMemberJoinDto(
            studyMemberJoinRequest);

        StudyMember.createStudyMember(study, member, studyMemberJoinDto);

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse("가입 신청 완료"),
            HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<CommonResponse> acceptStudyMember(Long id, Long memberIdx) {
        Study study = getStudyOrElseThrow(id);
        Member member = getMemberOrElseThrow(memberIdx);
        Optional<StudyMember> joinStudyMember = studyMemberRepo.findByStudyAndMember(study, member);

        if (joinStudyMember.isPresent()) {
            if ((joinStudyMember.get().getStudyMemberStatus().equals(StudyMemberStatusType.JOIN))) {
                return new ResponseEntity<>(
                    ResponseVOUtils.getFailResponse(CommonResultCode.BAD_REQUEST.code(),
                        "이미 가입한 스터디입니다."),
                    HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(
                ResponseVOUtils.getFailResponse(CommonResultCode.BAD_REQUEST.code(),
                    "가입 신청 하고 있지 않은 이용자입니다."),
                HttpStatus.BAD_REQUEST);
        }

        joinStudyMember.get().acceptStudyMember();

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse("가입 승인 완료"),
            HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<CommonResponse> disapproveStudyMember(Long id, Long memberIdx) {
        Study study = getStudyOrElseThrow(id);
        Member member = getMemberOrElseThrow(memberIdx);
        Optional<StudyMember> joinStudyMember = studyMemberRepo.findByStudyAndMember(study, member);

        if (joinStudyMember.isPresent()) {
            if ((joinStudyMember.get().getStudyMemberStatus().equals(StudyMemberStatusType.JOIN))) {
                return new ResponseEntity<>(
                    ResponseVOUtils.getFailResponse(CommonResultCode.BAD_REQUEST.code(),
                        "이미 가입한 스터디입니다."),
                    HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(
                ResponseVOUtils.getFailResponse(CommonResultCode.BAD_REQUEST.code(),
                    "가입 신청 하고 있지 않은 이용자입니다."),
                HttpStatus.BAD_REQUEST);
        }

        joinStudyMember.get().disapproveStudyMember();

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse("가입 거절 완료"),
            HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<CommonResponse> leaveStudyMember(Long id) {
        Study study = getStudyOrElseThrow(id);
        Member member = getLoginMemberOrElseThrow();
        StudyMember joinStudyMember = studyMemberQueryRepo.findJoinStudyMember(member, study);

        if (joinStudyMember == null) {
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.FORBIDDEN),
                HttpStatus.FORBIDDEN);
        }

        joinStudyMember.leaveStudyMember();
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse("멤버 탈퇴 완료"),
            HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<CommonResponse> kickStudyMember(Long id, Long memberIdx) {
        Study study = getStudyOrElseThrow(id);
        Member member = getMemberOrElseThrow(memberIdx);
        StudyMember joinStudyMember = studyMemberRepo.findByStudyAndMember(study, member)
            .orElseThrow(() -> new IllegalArgumentException("스터디 멤버를 찾을 수 없습니다."));
        joinStudyMember.kickStudyMember();

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse("멤버 강퇴 완료"),
            HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CommonResponse> transferStudyMember(Long studyIdx, Long newLeaderIdx) {
        return null;
    }


    private Study getStudyOrElseThrow(Long id) {
        return studyRepo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("스터디를 찾을 수 없습니다."));
    }

    // 로그인 멤버 찾는 로직
    private Member getLoginMemberOrElseThrow() {
        return memberRepo.findById(sessionInfo.getMemberIdx()).orElseThrow(() ->
            new IllegalArgumentException("멤버를 찾을 수 없습니다."));
    }

    // 타인 멤버 찾는 로직
    private Member getMemberOrElseThrow(Long memberIdx) {
        return memberRepo.findById(memberIdx).orElseThrow(() ->
            new IllegalArgumentException("멤버를 찾을 수 없습니다."));
    }
}
