package com.spinner.www.post.service;

import com.spinner.www.common.CommonResponse;
import com.spinner.www.common.io.SearchParamRequest;
import com.spinner.www.post.entity.Post;
import com.spinner.www.post.io.PostCreateRequest;
import com.spinner.www.post.io.PostUpdateRequest;
import org.springframework.http.ResponseEntity;

public interface PostService {

    /**
     * 게시글 생성
     * @param postRequest PostCreateRequestIO 게시글 요청 데이터
     * @return ResponseEntity<CommonResponse> 게시글 상세 정보
     */
    ResponseEntity<CommonResponse> insert(PostCreateRequest postRequest);

    /**
     * 게시글 uuid로 삭제되지 않은 게시글 조회
     * @param postIdx Long 게시글 idx
     * @return ResponseEntity<CommonResponse> 게시글 상세 정보
     */
    Post findByPostIdx(Long postIdx);

    /**
     * 게시글 조회
     * @param postIdx Long 게시글 idx
     * @return ResponseEntity<CommonResponse> 게시글 상세 정보
     */
    ResponseEntity<CommonResponse> findByPostInfo(Long postIdx);

    /**
     * 게시글 목록 조회
     * @param searchRequest SearchParamRequest 검색 조건
     * @return ResponseEntity<CommonResponse> 게시글 목록
     */
//    ResponseEntity<CommonResponse> findByAll(SearchParamRequest searchRequest);

    /**
     * 게시글 수정
     * @param postIdx Long 게시글 idx
     * @param postRequest PostUpdateRequestIO 게시글 수정 데이터
     * @return ResponseEntity<CommonResponse> 게시글 상세 정보
     */
    ResponseEntity<CommonResponse> update(Long postIdx, PostUpdateRequest postRequest);

    /**
     * 게시글 삭제
     * @param postIdx Long 게시글 idx
     * @return ResponseEntity<CommonResponse> 삭제 응답 결과
     */
    ResponseEntity<CommonResponse> delete(Long postIdx);
}
