package com.spinner.www.profile.service;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.member.io.MemberProfileUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProfileService {

    /**
     * 내가 작성한 게시글 목록 조회
     * @param idx Long 조회 시작 idx
     * @param size int 조회할 목록 갯수
     * @return ResponseEntity<CommonResponse> 게시글 목록
     */
    ResponseEntity<CommonResponse> getSliceOfMemberBoard(Long idx, int size);

    /**
     * 멤버가 작성한 게시글 목록 조회
     * @param idx Long 조회 시작 idx
     * @param size int 조회할 목록 갯수
     * @param memberIdx Long 조회할 멤버 idx
     * @return ResponseEntity<CommonResponse> 게시글 목록
     */
    ResponseEntity<CommonResponse> getSliceOfMemberBoard(Long idx, int size, Long memberIdx);

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
     * 본인 프로필 조회
     * @return MemberProfile
     */
    ResponseEntity<CommonResponse> getMemberProfile();

    /**
     * 회원 프로필 조회
     * @param memberIdx Long 조회할 사용자의 idx
     * @return MemberProfile
     */
    ResponseEntity<CommonResponse> getMemberProfile(Long memberIdx);

    /**
     * 멤버 프로필 업데이트
     * @param memberProfileUpdateRequest MemberProfileUpdateRequest
     * @param file MultipartFile
     * @return ResponseEntity<CommonResponse>
     */
    ResponseEntity<CommonResponse> updateMemberProfile(MemberProfileUpdateRequest memberProfileUpdateRequest, MultipartFile file) throws IOException;
}
