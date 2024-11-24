package com.spinner.www.post.controller;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.common.io.SearchParamRequest;
import com.spinner.www.post.io.PostCreateRequest;
import com.spinner.www.post.io.PostUpdateRequest;
import com.spinner.www.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/post")
public class PostRestController {

    private final PostService postService;

    /**
     * 게시글 생성
     * @param postRequest PostCreateRequestIO 게시글 요청 데이터
     * @return ResponseEntity<CommonResponse> 게시글 상세 정보
     */
    @PostMapping
    public ResponseEntity<CommonResponse> insert(@RequestBody PostCreateRequest postRequest) {
        return postService.insert(postRequest);
    }

    /**
     * 게시글 조회
     * @param postIdx Long 게시글 idx
     * @return ResponseEntity<CommonResponse> 게시글 상세 정보
     */
    @GetMapping("/{postIdx}")
    public ResponseEntity<CommonResponse> findByPostInfo(@PathVariable("postIdx") Long postIdx) {
        return postService.findByPostInfo(postIdx);
    }

    /**
     * 게시글 목록 조회
     * @param searchRequest SearchParamRequest 검색 조건
     * @return searchParamRequest<CommonResponse> 게시글 목록
     */
    @GetMapping
    public ResponseEntity<CommonResponse> findByAll(@ModelAttribute SearchParamRequest searchRequest) {
        return postService.getSliceOfPost(searchRequest);
    }

    /**
     * 게시글 수정
     * @param postIdx Long 게시글 idx
     * @param postRequest PostUpdateRequestIO 게시글 요청 데이터
     * @return ResponseEntity<CommonResponse> 게시글 상세 정보
     */
    @PatchMapping("/{postIdx}")
    public ResponseEntity<CommonResponse> update(@PathVariable("postIdx") Long postIdx, @RequestBody PostUpdateRequest postRequest) {
        return postService.update(postIdx, postRequest);
    }

    /**
     * 게시글 삭제
     * @param postIdx Long 게시글 idx
     * @return ResponseEntity<CommonResponse> 삭제 응답 결과
     */
    @DeleteMapping("/{postIdx}")
    public ResponseEntity<CommonResponse> delete(@PathVariable("postIdx") Long postIdx) {
        return postService.delete(postIdx);
    }

}
