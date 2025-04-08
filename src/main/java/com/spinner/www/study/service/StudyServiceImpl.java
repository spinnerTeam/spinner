package com.spinner.www.study.service;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.constants.CommonResultCode;
import com.spinner.www.study.entity.Study;
import com.spinner.www.study.repository.StudyRepo;
import com.spinner.www.util.ResponseVOUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class StudyServiceImpl implements StudyService {

    private final StudyRepo studyRepo;

    /**
     * 스터디 이름 유효성 검사
     * @param name String
     * @return Boolean
     */
    @Override
    public Boolean invalidateStudyName(String name){
        // 숫자,영어,한글 포함 3자~10자 , 특수문자 포함 x
        String pattern = "^[a-zA-Z가-힣0-9]{3,10}$";
        return Pattern.matches(pattern, name);
    }

    /**
     * 스터디 이름 중복검사
     * @param name String
     * @return Boolean
     */
    @Override
    public Boolean duplicateStudyName(String name){
        return studyRepo.existsByStudyName(name);
    }

    /**
     * 스터디명 유효성 검사
     * @param studyInfo String
     * @return Boolean
     */
    public Boolean invalidateStudyInfo(String studyInfo){
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
    @Override
    public ResponseEntity<CommonResponse> invalidateStudy(String studyName, Integer studyMaxPeople, String studyInfo, String type){
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
     * 스터디 저장
     * @param study Study
     */
    @Override
    public void saveStudy(Study study) {
        studyRepo.save(study);
    }

    /**
     * Optional Study 조회
     * @param studyIdx Long
     * @return Optional<Study>
     */
    @Override
    public Optional<Study> getStudy(Long studyIdx) {
        return studyRepo.findById(studyIdx);
    }
}
