package com.spinner.www.study.service;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.constants.CommonResultCode;
import com.spinner.www.file.entity.Files;
import com.spinner.www.file.service.FileService;
import com.spinner.www.member.dto.SessionInfo;
import com.spinner.www.study.io.CreateStudy;
import com.spinner.www.study.repository.StudyRepo;
import com.spinner.www.util.ResponseVOUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudyServiceImpl implements StudyService {

    private final SessionInfo sessionInfo;
    private final StudyRepo studyRepo;
    private final FileService fileService;

    /**
     * 스터디 생성
     * @param createStudy CreateStudy
     * @return ResponseEntity<CommonResponse>
     */
    @Override
    public ResponseEntity<CommonResponse> createStudy(CreateStudy createStudy) throws IOException {

        if(sessionInfo.getMemberIdx() == null){
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
        }

        String studyName = createStudy.getStudyName().replaceAll("\\s+", "");

        // 스터디 이름 (최소 3글자 ~ 10자 이내 생성)
        if(!invalidateStudyName(studyName)){
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.INVALID_STUDY_NAME_FORMAT), HttpStatus.UNAUTHORIZED);
        }

        // 중복검사
        if(!duplicateStudyName(studyName)){
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.DUPLICATE), HttpStatus.UNAUTHORIZED);
        }

        // 인원수 : 최소 2~15명
        if(createStudy.getStudyMaxPeople()> 15 || createStudy.getStudyMaxPeople() < 2){
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.INVALID_STUDY_MEMBER_COUNT), HttpStatus.UNAUTHORIZED);
        }

        // 스터디 소개 10자 이상 ~ 100자 이내
        if(!invalidateStudyInfo(createStudy.getStudyInfo().trim())){
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.INVALID_STUDY_INTRO_LENGTH), HttpStatus.BAD_REQUEST);
        }

        // 스터디 디비 저장

        // 스터디 멤버 방장 저장

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

        //

        return null;
    }

    /**
     * 스터디 이름 유효성 검사
     * @param name String
     * @return Boolean
     */
    private Boolean invalidateStudyName(String name){
        // 숫자,영어,한글 포함 3자~10자 , 특수문자 포함 x
        String pattern = "[a-zA-Z가-힣0-9]{3,10}$";
        if(!pattern.matches(name)){
            return false;
        }
        return true;
    }

    /**
     * 스터디 이름 중복검사
     * @param name
     * @return
     */
    private Boolean duplicateStudyName(String name){
        if(!studyRepo.existsByStudyName(name)){
            return false;
        }
        return true;
    }

    private Boolean invalidateStudyInfo(String studyInfo){
        // 스터디 소개 최소 10자이상 최대 100자 이내

        if (studyInfo.length() < 10 || studyInfo.length() > 100) {
            return false;
        }
        return true;
    }
}
