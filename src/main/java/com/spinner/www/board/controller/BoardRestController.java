package com.spinner.www.board.controller;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.common.io.SearchParamRequest;
import com.spinner.www.board.io.BoardCreateRequest;
import com.spinner.www.board.io.BoardUpdateRequest;
import com.spinner.www.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/board")
public class BoardRestController {

    private final BoardService boardService;

    /**
     * 게시글 생성
     * @param boardRequest BoardCreateRequestIO 게시글 요청 데이터
     * @return ResponseEntity<CommonResponse> 게시글 상세 정보
     */
    @PostMapping
    public ResponseEntity<CommonResponse> insert(@RequestBody BoardCreateRequest boardRequest) {
        return boardService.insert(boardRequest);
    }

    /**
     * 게시글 조회
     * @param boardIdx Long 게시글 idx
     * @return ResponseEntity<CommonResponse> 게시글 상세 정보
     */
    @GetMapping("/{boardIdx}")
    public ResponseEntity<CommonResponse> findByBoardInfo(@PathVariable("boardIdx") Long boardIdx) {
        return boardService.findByBoardInfo(boardIdx);
    }

    /**
     * 게시글 목록 조회
     * @param searchRequest SearchParamRequest 검색 조건
     * @return searchParamRequest<CommonResponse> 게시글 목록
     */
    @GetMapping
    public ResponseEntity<CommonResponse> findByAll(@ModelAttribute SearchParamRequest searchRequest) {
        return boardService.getSliceOfBoard(searchRequest);
    }

    /**
     * 게시글 수정
     * @param boardIdx Long 게시글 idx
     * @param boardRequest BoardUpdateRequestIO 게시글 요청 데이터
     * @return ResponseEntity<CommonResponse> 게시글 상세 정보
     */
    @PatchMapping("/{boardIdx}")
    public ResponseEntity<CommonResponse> update(@PathVariable("boardIdx") Long boardIdx, @RequestBody BoardUpdateRequest boardRequest) {
        return boardService.update(boardIdx, boardRequest);
    }

    /**
     * 게시글 삭제
     * @param boardIdx Long 게시글 idx
     * @return ResponseEntity<CommonResponse> 삭제 응답 결과
     */
    @DeleteMapping("/{boardIdx}")
    public ResponseEntity<CommonResponse> delete(@PathVariable("boardIdx") Long boardIdx) {
        return boardService.delete(boardIdx);
    }

}
