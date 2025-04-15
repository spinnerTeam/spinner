package com.spinner.www.study.service;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.common.service.StudyTopicService;
import com.spinner.www.constants.CommonResultCode;
import com.spinner.www.file.entity.Files;
import com.spinner.www.file.service.FileService;
import com.spinner.www.member.dto.SessionInfo;
import com.spinner.www.member.entity.Member;
import com.spinner.www.member.service.MemberService;
import com.spinner.www.study.constants.StudyMemberRole;
import com.spinner.www.study.constants.StudyMemberStatus;
import com.spinner.www.study.dto.*;
import com.spinner.www.study.entity.Study;
import com.spinner.www.study.entity.StudyMember;
import com.spinner.www.study.io.CreateStudy;
import com.spinner.www.study.io.JoinStudyMember;
import com.spinner.www.study.io.UpdateStudyIo;
import com.spinner.www.util.ResponseVOUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudyFacadeServiceImpl implements StudyFacadeService{

    private final SessionInfo sessionInfo;
    private final StudyService studyService;
    private final FileService fileService;
    private final StudyTopicService studyTopicService;
    private final MemberService memberService;
    private final StudyMemberService studyMemberService;

    /**
     * 스터디 생성
     * @param createStudy CreateStudy
     * @return ResponseEntity<CommonResponse>
     */
    @Override
    @Transactional
    public ResponseEntity<CommonResponse> createStudy(CreateStudy createStudy) throws IOException {

        // 로그인 체크 > 추 후 삭제 예정
        if(sessionInfo.getMemberIdx() == null){
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
        }
        String studyName = createStudy.getStudyName().replaceAll("\\s+", "");

        // 스터디 유효성 검사
        ResponseEntity<CommonResponse> result = studyService.invalidateStudy(createStudy.getStudyName(), createStudy.getStudyMaxPeople(), createStudy.getStudyInfo(), "create");
        if(!result.getStatusCode().is2xxSuccessful()){
            return result;
        }

        // 스터디 파일 저장
        Files files = null;
        if(createStudy.getFile() != null && !createStudy.getFile().isEmpty()){
            ResponseEntity<CommonResponse> response = fileService.uploadFiles(createStudy.getFile());
            List<Long> idxs = (List<Long>) response.getBody().getResults();
            files = fileService.getFiles(idxs.get(0));
        } else {
            // 기본이미지
            files = fileService.getFiles(114L);
        }

        // 스터디 디비 저장
        StudyCreateDto studyCreateDto = new StudyCreateDto(
                createStudy.getStudyName(),
                files,
                createStudy.getStudyInfo(),
                createStudy.getStudyMaxPeople(),
                studyTopicService.getStudyTopicByStudyTopicIdx(createStudy.getStudyTopicIdx()).get()
        );

        Study study = Study.insertStudy(studyCreateDto);
        studyService.saveStudy(study);

        // 스터디 멤버 방장 저장
        StudyMemgberCreateDto studyMemgberCreateDto = new StudyMemgberCreateDto(
                StudyMemberRole.LEADER,
                StudyMemberStatus.APPROVED,
                false,
                null,
                study,
                memberService.getMember(sessionInfo.getMemberIdx())
        );

        // 스터디 멤버 저장
        StudyMember studyMember = StudyMember.insertStudyMember(studyMemgberCreateDto);
        studyMemberService.saveStudyMember(studyMember);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(), HttpStatus.CREATED);
    }

    /**
     * 스터디 수정
     * @param updateStudy UpdateStudyIo
     * @return ResponseEntity<CommonResponse>
     */
    @Override
    @Transactional
    public ResponseEntity<CommonResponse> updateStudy(UpdateStudyIo updateStudy) {
        // 로그인 체크 > 추 후 삭제 예정
        if(sessionInfo.getMemberIdx() == null){
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
        }

        // 스터디 존재 여부
        Optional<Study> study = studyService.getStudy(updateStudy.getStudyIdx());
        if(study.isEmpty()){
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.NOT_FOUND_STUDY), HttpStatus.UNAUTHORIZED);
        }

        // 스터디 유효성 검사
        ResponseEntity<CommonResponse> response = studyService.invalidateStudy(updateStudy.getStudyName(), updateStudy.getStudyMaxPeople(), updateStudy.getStudyInfo(), "update");
        if(!response.getStatusCode().is2xxSuccessful()){
            return response;
        }

        StudyUpdateDto studyUpdateDto = new StudyUpdateDto(
                updateStudy.getStudyName(),
                updateStudy.getStudyInfo(),
                updateStudy.getStudyMaxPeople(),
                studyTopicService.getStudyTopicByStudyTopicIdx(updateStudy.getStudyTopicIdx()).get()
        );
        // 수정내역 저장
        study.get().updateStudy(studyUpdateDto);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse("스터디 수정이 완료되었습니다."), HttpStatus.OK);
    }

    /**
     * 스터디 이미지 수정
     * @param studyIdx Long
     * @param file MultipartFile
     * @return ResponseEntity<CommonResponse>
     */
    @Override
    @Transactional
    public ResponseEntity<CommonResponse> updateStrudyFile(Long studyIdx, MultipartFile file) throws IOException {

        Optional<Study> study = studyService.getStudy(studyIdx);
        if(study.isEmpty()){
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.NOT_FOUND_STUDY), HttpStatus.NOT_FOUND);
        }

        // 기존 이미지 삭제
        fileService.deleteFile(study.get().getFiles().getFileIdx());

        // 스터디 파일 저장
        Files files = null;
        if(file != null && !file.isEmpty()){
            ResponseEntity<CommonResponse> response = fileService.uploadFiles(file);
            List<Long> idxs = (List<Long>) response.getBody().getResults();
            files = fileService.getFiles(idxs.get(0));
        } else {
            // 기본이미지
            files = fileService.getFiles(114L);
        }
        study.get().updateStudyFile(files);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse("스터시 이미지 수정이 완료되었습니다."), HttpStatus.OK);
    }

    /**
     * 스터디 소프트 삭제
     * @param studyIdx Long
     * @return ResponseEntity<CommonResponse>
     */
    @Override
    @Transactional
    public ResponseEntity<CommonResponse> deleteStudy(Long studyIdx) {

        // 스터디 존재하는지
        Optional<Study> study = studyService.getStudy(studyIdx);
        if(study.isEmpty()){
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.NOT_FOUND_STUDY), HttpStatus.NOT_FOUND);
        }

        StudyMember studyMember = studyMemberService.getStudyMember(memberService.getMember(sessionInfo.getMemberIdx()) , studyIdx);
        // 스터디 멤버 맞는지
        if(studyMember == null){
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }

        // 스터디 장 맞는지
        if(studyMember.getStudyMemberRole().equals(StudyMemberRole.LEADER)){
            // 상태값 삭제로 변경
            study.get().softDeleteStudy();
        }

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse("스터디 삭제가 완료되었습니다."), HttpStatus.OK);
    }

    /**
     * 스터디 가입 신청
     * @param studyIdx Long
     * @param studyMember JoinStudyMember
     * @return ResponseEntity<CommonResponse>
     */
    @Override
    public ResponseEntity<CommonResponse> joinStudyMember(Long studyIdx, JoinStudyMember studyMember) {

        // 스터디 존재하는지
        Optional<Study> study = studyService.getStudy(studyIdx);
        if(study.isEmpty()){
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.DATA_NOT_FOUND), HttpStatus.NOT_FOUND);
        }

        // 가입인사 빈칸이면 리턴
        if(studyMember.getInfo().equals("") || studyMember.getInfo() == null){
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(50001,"가입소개를 적어주세요."), HttpStatus.BAD_REQUEST);
        }
        Member member = memberService.getMember(sessionInfo.getMemberIdx());

        // 가입신청 중복검사
        if(studyMemberService.isStudyMember(study.get(),member)){
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(50003,"이미 가입된 스터디입니다."), HttpStatus.BAD_REQUEST);
        }

        // 가입신청
        StudyMemgberCreateDto studyMemgberCreateDto = new StudyMemgberCreateDto(
                StudyMemberRole.MEMBER,
                StudyMemberStatus.WAITING,
                false,
                studyMember.getInfo(),
                study.get(),
                member
        );
        studyMemberService.saveStudyMember(StudyMember.insertStudyMember(studyMemgberCreateDto));
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse("가입신청이 완료되었습니다."), HttpStatus.OK);
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
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(studyService.joinedStudy(status, member)), HttpStatus.OK);
    }

    /**
     * 스터디 멤버관리(신청인원, 참여인원)
     * @param studyIdx Long
     * @param studyStatus String
     * @return ResponseEntity<CommonResponse>
     */
    @Override
    public ResponseEntity<CommonResponse> findByStudyAndStudyStatus(Long studyIdx, String studyStatus) {
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(studyService.pendingStudyMember(studyIdx, studyStatus)), HttpStatus.OK);
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
        Optional<Study> study = studyService.getStudy(studyIdx);
        if (study.isEmpty()){
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.NOT_FOUND_STUDY),HttpStatus.OK);
        }

        Member member = memberService.getMember(memberIdx);
        StudyMember studyMember = studyMemberService.getStudyMember(member, study.get());
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

        Optional<Study> study = studyService.getStudy(studyIdx);
        if (study.isEmpty()){
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.NOT_FOUND_STUDY),HttpStatus.OK);
        }

        Member member = memberService.getMember(sessionInfo.getMemberIdx());
        if(member == null){
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.NO_LOGIN),HttpStatus.OK);
        }

        // 가입신청 했는지
        Optional<StudyMember> studyMember = studyMemberService.findByMemberAndStudyAndStudyMemberStatus(member, study.get(), StudyMemberStatus.WAITING);

        // 가입취소
        if(studyMember.isEmpty()){
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(5005, "스터디 가입 이력이 없습니다."),HttpStatus.OK);
        }
        studyMemberService.deleteById(studyMember.get().getStudyMemberIdx());
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

        Optional<Study> study = studyService.getStudy(studyIdx);
        if (study.isEmpty()){
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.NOT_FOUND_STUDY),HttpStatus.OK);
        }

        Member member = memberService.getMember(memberIdx);
        if(member == null){
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.DATA_NOT_FOUND),HttpStatus.OK);
        }
        StudyMember studyMember = studyMemberService.getStudyMember(member, study.get());
        studyMemberService.deleteById(studyMember.getStudyMemberIdx());
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse("가입거절 완료되었습니다."),HttpStatus.OK);
    }

    /**
     * 스터디 찾기 (회원 관심사별 랜덤조회)
     * @return ResponseEntity<CommonResponse>
     */
    @Override
    public ResponseEntity<CommonResponse> getSearchTopic() {

        // 관심분야 조회
        List<Long> longList = studyTopicService.findInterestCodeIdxByMember(sessionInfo.getMemberIdx());

        // 관심분야별 랜덤으로 스터디 노출
        List<StudyListDto> studyListDtos = studyService.findInterestCodeByStudy(longList);
        for(StudyListDto dto : studyListDtos){
            // 스터디원 수
            Integer memberIdxCount = memberService.getStudyMemberCountByStudyIdx(dto.getStudyIdx());
            // 게시글 갯수 추가 예정
            dto.addMemberIdxCountAndBoardIdxCount(memberIdxCount, 0);
        }

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(studyListDtos), HttpStatus.CREATED);
    }

    /**
     * 상세보기
     * @param studyIdx Long
     * @return ResponseEntity<CommonResponse>
     */
    @Override
    public ResponseEntity<CommonResponse> getStudyDetail(Long studyIdx) {
        StudyDetailDto studyDetailDto = studyService.getStudyDetail(studyIdx);

        // 스터디 멤버 리스트
        List<StudyJoinMemberDto> studyJoinMemberDtos = studyMemberService.getApprovedStudyMembers(studyIdx);

        // 스터디 가입 회원 수
        Long studyMemberCount = studyMemberService.countApprovedMembers(studyIdx);
        Member member = memberService.getMember(sessionInfo.getMemberIdx());

        // 조회 한 유저의 가입 상태
        Optional<Study> study = studyService.getStudy(studyIdx);
        boolean studyJoinStatus = studyMemberService.isStudyMember(study.get(), member);

        // 스터디 게시글 갯수 추가 예정
        studyDetailDto.setMembersAndCounts(studyJoinMemberDtos, studyMemberCount, studyJoinStatus);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(studyDetailDto), HttpStatus.CREATED);
    }
    /**
     * studyIdx로 스터디 조회
     * @param studyIdx
     * @return
     */
    @Override
    public ResponseEntity<CommonResponse> findStudyMembersByStudyIdx(Long studyIdx) {

        List<StudyJoinMemberDto> req = studyMemberService.getApprovedStudyMembers(studyIdx);

        Member member = memberService.getMember(sessionInfo.getMemberIdx());
        StudyMember studyMember = studyMemberService.getStudyMember(member, studyIdx);

        if(studyMember != null){
            // 리스트에서 나는 삭제
            req.removeIf(dto -> dto.getStudyMemberIdx().equals(studyMember.getStudyMemberIdx()));
        }

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(req), HttpStatus.CREATED);
    }
}
