package com.spinner.www.study.service;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.member.entity.Member;
import com.spinner.www.study.constants.StudyMemberStatus;
import com.spinner.www.study.dto.*;
import com.spinner.www.study.entity.Study;
import com.spinner.www.study.io.CreateStudy;
import com.spinner.www.study.io.JoinStudyMember;
import com.spinner.www.study.io.UpdateStudyIo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
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
     * Study 조회
     * @param studyIdx Long
     * @return 스터디가 존재하면 Study 리턴
     */
    Study getStudyOrThrow(Long studyIdx);

    /**
     * 나의 스터디 (참여스터디, 가입대기스터디, 종료 스터디)
     * @param studyMemberStatus StudyMemberStatus
     * @param member Member
     * @return List<MyStudyListDto>
     */
    List<MyStudyListDto> joinedStudy(StudyMemberStatus studyMemberStatus, Member member);

    /**
     * 스터디 멤버관리 (신청인원, 참여인원)
     * @param studyIdx Long
     * @param studyStatus String
     * @return List<PendingStudyMemberDto>
     */
    List<PendingStudyMemberDto> pendingStudyMember(Long studyIdx, String studyStatus);

    /**
     * 관심분야 별 가입가능 스터디 조회 (랜덤 노출)
     * @param codeList List<Long>
     * @return List<StudyListDto>
     */
    List<StudyListDto> findInterestCodeByStudy(List<Long> codeList);

    /**
     * 스터디 상세조회
     * @param studyIdx Long
     * @return StudyDetailDto
     */
    StudyDetailDto getStudyDetail(Long studyIdx);

    List<StudyDocument> initElasticsearch();
}
