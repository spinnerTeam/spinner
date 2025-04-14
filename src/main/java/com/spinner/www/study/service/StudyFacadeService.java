package com.spinner.www.study.service;


import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.study.io.CreateStudy;
import com.spinner.www.study.io.JoinStudyMember;
import com.spinner.www.study.io.UpdateStudyIo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StudyFacadeService {

    /**
     * 스터디 생성
     * @param createStudy CreateStudy
     * @return ResponseEntity<CommonResponse>
     */
    ResponseEntity<CommonResponse> createStudy(CreateStudy createStudy) throws IOException;

    /**
     * 스터디 수정
     * @param updateStudy UpdateStudyIo
     * @return ResponseEntity<CommonResponse>
     */
    ResponseEntity<CommonResponse> updateStudy(UpdateStudyIo updateStudy);

    /**
     * 스터디 파일 수정
     * @param studyIdx Long
     * @param file MultipartFile
     * @return ResponseEntity<CommonResponse>
     */
    ResponseEntity<CommonResponse> updateStrudyFile(Long studyIdx, MultipartFile file) throws IOException;

    /**
     * 스터디 삭제
     * @param studyIdx Long
     * @return ResponseEntity<CommonResponse>
     */
    ResponseEntity<CommonResponse> deleteStudy(Long studyIdx);

    /**
     * 스터디 가입 신청
     * @param studyIdx Long
     * @param studyMember JoinStudyMember
     * @return ResponseEntity<CommonResponse>
     */
    ResponseEntity<CommonResponse> joinStudyMember(Long studyIdx, JoinStudyMember studyMember);

    /**
     * 나의 스터디 (참여스터디, 가입대기스터디, 종료 스터디)
     * @param memberIdx Long
     * @param studyMemberStatus String
     * @return  ResponseEntity<CommonResponse>
     */
    ResponseEntity<CommonResponse> findByStudyMemberStatusAndMember(String studyMemberStatus, Long memberIdx);

    /**
     * 스터디 멤버관리(신청인원, 참여인원)
     * @param studyIdx Long
     * @param studyStatus String
     * @return ResponseEntity<CommonResponse>
     */
    ResponseEntity<CommonResponse> findByStudyAndStudyStatus(Long studyIdx, String studyStatus);

    /**
     * 스터디 가입 승인
     * @param studyIdx
     * @param memberIdx
     * @return
     */
    ResponseEntity<CommonResponse> updateStudyMember(Long studyIdx, Long memberIdx);

    /**
     * 스터디 가입취소
     * @param studyIdx Long
     * @return ResponseEntity<CommonResponse>
     */
    ResponseEntity<CommonResponse> cancelStudy(Long studyIdx);

    /**
     * 스터디 가입 거절
     * @param studyIdx Long
     * @param memberIdx Long
     * @return ResponseEntity<CommonResponse>
     */
    ResponseEntity<CommonResponse> cancelStudyMember(Long studyIdx, Long memberIdx);

    /**
     * 스터디 찾기 (회원 관심사별 랜덤조회)
     * @return ResponseEntity<CommonResponse>
     */
    ResponseEntity<CommonResponse> getSearchTopic();

    /**
     * 상세보기
     * @param studyIdx Long
     * @return ResponseEntity<CommonResponse>
     */
    ResponseEntity<CommonResponse> getStudyDetail(Long studyIdx);
}
