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
import com.spinner.www.study.dto.StudyCreateDto;
import com.spinner.www.study.dto.StudyMemgberCreateDto;
import com.spinner.www.study.dto.StudyUpdateDto;
import com.spinner.www.study.entity.Study;
import com.spinner.www.study.entity.StudyMember;
import com.spinner.www.study.io.CreateStudy;
import com.spinner.www.study.io.JoinStudyMember;
import com.spinner.www.study.io.UpdateStudyIo;
import com.spinner.www.study.repository.StudyRepo;
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
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class StudyServiceImpl implements StudyService {

    private final SessionInfo sessionInfo;
    private final StudyRepo studyRepo;
    private final FileService fileService;
    private final MemberService memberService;
    private final StudyTopicService studyTopicService;
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
        ResponseEntity<CommonResponse> result = invalidateStudy(createStudy.getStudyName(), createStudy.getStudyMaxPeople(), createStudy.getStudyInfo(), "create");
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
        studyRepo.save(study);

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
        Optional<Study> study = studyRepo.findById(updateStudy.getStudyIdx());
        if(study.isEmpty()){
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.NOT_FOUND_STUDY), HttpStatus.UNAUTHORIZED);
        }

        // 스터디 유효성 검사
        ResponseEntity<CommonResponse> response = invalidateStudy(updateStudy.getStudyName(), updateStudy.getStudyMaxPeople(), updateStudy.getStudyInfo(), "update");
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
     * 스터디 이름 유효성 검사
     * @param name String
     * @return Boolean
     */
    private Boolean invalidateStudyName(String name){
        // 숫자,영어,한글 포함 3자~10자 , 특수문자 포함 x
        String pattern = "^[a-zA-Z가-힣0-9]{3,10}$";
        return Pattern.matches(pattern, name);
    }

    /**
     * 스터디 이름 중복검사
     * @param name
     * @return Boolean
     */
    private Boolean duplicateStudyName(String name){
        return studyRepo.existsByStudyName(name);
    }

    /**
     * 스터디명 유효성 검사
     * @param studyInfo String
     * @return Boolean
     */
    private Boolean invalidateStudyInfo(String studyInfo){
        // 스터디 소개 최소 10자이상 최대 100자 이내
        if (studyInfo.length() < 10 || studyInfo.length() > 100) {
            return false;
        }
        return true;
    }

    /**
     * 스터디 유호성 검사
     * @param studyName String
     * @param studyMaxPeople Integer
     * @param studyInfo String
     * @param type String
     * @return ResponseEntity<CommonResponse>
     */
    private ResponseEntity<CommonResponse> invalidateStudy(String studyName, Integer studyMaxPeople, String studyInfo, String type){
        // 스터디 이름 (최소 3글자 ~ 10자 이내 생성)
        if(!invalidateStudyName(studyName)){
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.INVALID_STUDY_NAME_FORMAT), HttpStatus.UNAUTHORIZED);
        }

        // 중복검사
        if(duplicateStudyName(studyName)){
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.DUPLICATE_STUDY_NAME), HttpStatus.UNAUTHORIZED);
        }

        // 스터디 소개 10자 이상 ~ 100자 이내
        if(!invalidateStudyInfo(studyInfo.trim())){
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.INVALID_STUDY_INTRO_LENGTH), HttpStatus.BAD_REQUEST);
        }

        // 인원수 : 최소 2~15명
        if(studyMaxPeople> 15 || studyMaxPeople < 2){
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.INVALID_STUDY_MEMBER_COUNT), HttpStatus.UNAUTHORIZED);
        }

        // 기존 인원과 숫자가 맞지 않을 때 (스터디 가입 이후 추가 예정)
        if(type.equals("update")){

        }

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

        Optional<Study> study = studyRepo.findById(studyIdx);
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
        Optional<Study> study = studyRepo.findById(studyIdx);
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
        Optional<Study> study = studyRepo.findById(studyIdx);
        if(study.isEmpty()){
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.DATA_NOT_FOUND), HttpStatus.NOT_FOUND);
        }

        // 가입인사 빈칸이면 리턴
        if(studyMember.getInfo().equals("") || studyMember.getInfo() == null){
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(50001,"가입소개를 적어주세요."), HttpStatus.BAD_REQUEST);
        }
        Member member = memberService.getMember(sessionInfo.getMemberIdx());

        // 가입신청 중복검사
        if(isStudyMember(studyIdx,member)){
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
     * 스터디 가입 여부
     * @param studyIdx Long
     * @param member Member
     * @return boolean
     */
    private boolean isStudyMember(Long studyIdx ,Member member){
        return studyMemberService.existsByStudyMemberIdxAndMember(studyIdx, member);
    }
}
