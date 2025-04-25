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

    /**
     * studyIdx로 스터디 조회
     * @param studyIdx
     * @return
     */
    ResponseEntity<CommonResponse> findStudyMembersByStudyIdx(Long studyIdx);

    /**
     * 스터디 탈퇴시키기
     * @param studyIdx Long
     * @param studyMemberIdx Long
     * @return ResponseEntity<CommonResponse>
     */
    ResponseEntity<CommonResponse> deleteStudyMember(Long studyIdx, Long studyMemberIdx);

    /**
     * 사용자가 해당 스터디에서 탈퇴를 요청합니다.
     *
     * @param studyIdx 탈퇴할 스터디의 고유 ID
     * @return 탈퇴 성공 여부를 담은 응답 객체
     */
    ResponseEntity<CommonResponse> withdrawStudy(Long studyIdx);

    /**
     * 스터디 방장이 다른 스터디 멤버에게 방장 권한을 위임합니다.
     *
     * @param studyIdx 권한을 위임할 스터디의 고유 ID
     * @param studyMemberIdx 방장 권한을 넘길 대상 스터디 멤버 ID
     * @return 권한 위임 성공 여부를 담은 응답 객체
     */
    ResponseEntity<CommonResponse> transferLeader(Long studyIdx, Long studyMemberIdx);

    /**
     * 엘라스틱 서치에 전체 데이터 수동으로 업로드됩니다.
     * @return 엘라스틱서치에 업로드 후 성공 여부를 담은 응답객체
     */
    ResponseEntity<CommonResponse> initElasticsearch();
}
