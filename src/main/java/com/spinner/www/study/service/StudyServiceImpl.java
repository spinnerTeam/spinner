package com.spinner.www.study.service;

import static com.spinner.www.study.entity.Study.create;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.file.entity.Files;
import com.spinner.www.file.service.FileService;
import com.spinner.www.member.dto.SessionInfo;
import com.spinner.www.member.entity.Member;
import com.spinner.www.member.service.MemberService;
import com.spinner.www.study.dto.StudyCreateDto;
import com.spinner.www.study.dto.StudyUpdateDto;
import com.spinner.www.study.entity.Study;
import com.spinner.www.study.entity.StudyMember;
import com.spinner.www.study.io.StudyCreateRequest;
import com.spinner.www.study.io.StudyUpdateRequest;
import com.spinner.www.study.mapper.StudyMapper;
import com.spinner.www.study.repository.StudyMemberRepository;
import com.spinner.www.study.repository.StudyRepository;
import com.spinner.www.util.ResponseVOUtils;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyServiceImpl implements StudyService {

    private final StudyRepository studyRepository;
    private final FileService fileService;
    private final StudyMapper studyMapper;
    private final SessionInfo sessionInfo;
    private final MemberService memberService;
    private final StudyMemberRepository studyMemberRepository;

    @Override
    public ResponseEntity<CommonResponse> getStudyList() {
        return null;
    }

    @Override
    public ResponseEntity<CommonResponse> getStudy(Long id) {
        return null;
    }

    @Override
    @Transactional
    public ResponseEntity<CommonResponse> createStudy(StudyCreateRequest studyCreateRequest) {
        loginCheck();

        // 스터디 개설
        StudyCreateDto studyCreateDto = studyMapper.toStudyCreateDto(studyCreateRequest);
        Study study = create(studyCreateDto);
        studyRepository.save(study);

        // 스터디 멤버에 로그인된 유저 넣기
        Member member = memberService.getMember(sessionInfo.getMemberIdx());
        StudyMember studyMember = StudyMember.createStudyMember(study, member);
        studyMemberRepository.save(studyMember);

        // 연관관계 설정
        study.addStudyMember(studyMember);

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(study.getId()), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<CommonResponse> uploadStudyInfoFile(Long id, List<MultipartFile> files) throws IOException {
        // 파일 업로드
        Files file = fileService.uploadStudyFile(files);

        // 스터디 업데이트
        Study study = getStudyOrElseThrow(id);
        study.updateFile(file);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<CommonResponse> updateStudy(Long id,
        StudyUpdateRequest studyUpdateRequest) {

        StudyUpdateDto studyUpdateDto = studyMapper.toStudyUpdateDto(studyUpdateRequest);
        Study study = getStudyOrElseThrow(id);
        study.update(studyUpdateDto);

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<CommonResponse> deleteStudy(Long id) {
        Study study = getStudyOrElseThrow(id);
        study.softDelete();
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(), HttpStatus.OK);
    }

    private void loginCheck() {
        if (sessionInfo.getMemberIdx() == null) {
            throw new IllegalArgumentException("로그인 된 상태가 아닙니다.");
        }
    }

    private Study getStudyOrElseThrow(Long id) {
        return studyRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("스터디를 찾을 수 없습니다."));
    }
}
