package com.spinner.www.myprofile.service;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.member.io.MemberProfileUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface MyProfileService {

    /**
     * 내가 작성한 게시글 목록 조회
     * @param idx Long 조회 시작 idx
     * @param size int 조회할 목록 갯수
     * @return ResponseEntity<CommonResponse> 게시글 목록
     */
    ResponseEntity<CommonResponse> getSliceOfMemberBoard(Long idx, int size);

    /**
     * 내가 좋아요를 누른 게시글 목록 조회
     * @param idx Long 조회 시작 idx
     * @param size int 조회할 목록 갯수
     * @return ResponseEntity<CommonResponse> 게시글 목록
     */
    ResponseEntity<CommonResponse> getSliceOfLikedBoard(Long idx, int size);

    /**
     * 북마크한 게시글 목록 조회
     * @param idx Long 조회 시작 idx
     * @param size int 조회할 목록 갯수
     * @return ResponseEntity<CommonResponse> 게시글 목록
     */
    ResponseEntity<CommonResponse> getSliceOfBookmarkedBoard(Long idx, int size);

    /**
     * 인기글 게시글 목록 조회
     * @param boardType String 조회할 게시판의 타입
     * @param idx Long 조회 시작 idx
     * @param size int 조회할 목록 갯수
     * @return ResponseEntity<CommonResponse> 게시글 목록
     */
    ResponseEntity<CommonResponse> getSliceOfHotBoard(String boardType, Long idx, int size);


    /**
     * idx로 회원 프로필 조회
     * @return MemberProfile
     */
    ResponseEntity<CommonResponse> getMemberProfile();

    /**
     * 멤버 프로필 업데이트
     * @param memberProfileUpdateRequest MemberProfileUpdateRequest
     * @param file MultipartFile
     * @return ResponseEntity<CommonResponse>
     */
    ResponseEntity<CommonResponse> updateMemberProfile(MemberProfileUpdateRequest memberProfileUpdateRequest, MultipartFile file) throws IOException;
}
