package com.spinner.www.study.service;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.member.entity.Member;
import com.spinner.www.study.entity.Study;
import com.spinner.www.study.io.CreateStudy;
import com.spinner.www.study.io.JoinStudyMember;
import com.spinner.www.study.io.UpdateStudyIo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface StudyService {


    /**
     * 스터디 이름 유효성 검사
     * @param name String
     * @return Boolean
     */
    Boolean invalidateStudyName(String name);

    /**
     * 스터디 이름 중복검사
     * @param name
     * @return Boolean
     */
    Boolean duplicateStudyName(String name);

    /**
     * 스터디명 유효성 검사
     * @param studyInfo String
     * @return Boolean
     */
    Boolean invalidateStudyInfo(String studyInfo);

    /**
     * 스터디 유호성 검사
     * @param studyName String
     * @param studyMaxPeople Integer
     * @param studyInfo String
     * @param type String
     * @return ResponseEntity<CommonResponse>
     */
    ResponseEntity<CommonResponse> invalidateStudy(String studyName, Integer studyMaxPeople, String studyInfo, String type);

    /**
     * 스터디 저장
     * @param study Study
     */
    void saveStudy(Study study);

    /**
     * Optional Study 조회
     * @param studyIdx Long
     * @return Optional<Study>
     */
    Optional<Study> getStudy(Long studyIdx);
}
