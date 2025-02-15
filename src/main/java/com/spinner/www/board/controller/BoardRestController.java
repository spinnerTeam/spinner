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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/board")
public class BoardRestController {

    private final BoardService boardService;

    /**
     * 게시글 생성
     * @param boardType String
     * @param boardRequest BoardCreateRequestIO 게시글 요청 데이터
     * @return ResponseEntity<CommonResponse> 게시글 상세 정보
     */
    @PostMapping("/{boardType}")
    public ResponseEntity<CommonResponse> insert(
            @PathVariable("boardType") String boardType,
            @RequestPart(value="boardRequest") BoardCreateRequest boardRequest, // JSON 데이터
            @RequestPart(value = "multiFile", required = false) List<MultipartFile> files) { // 파일 데이터
        return boardService.insert(boardType, boardRequest, files);
    }

    /**
     * 게시글 조회
     * @param boardType String
     * @param boardIdx Long 게시글 idx
     * @return ResponseEntity<CommonResponse> 게시글 상세 정보
     */
    @GetMapping("/{boardType}/{boardIdx}")
    public ResponseEntity<CommonResponse> findByBoardInfo(@PathVariable("boardType") String boardType, @PathVariable("boardIdx") Long boardIdx) {
        return boardService.findByBoardInfo(boardType, boardIdx);
    }

    /**
     * 게시글 목록 조회
     * @param boardType String
     * @param searchRequest SearchParamRequest 검색 조건
     * @return searchParamRequest<CommonResponse> 게시글 목록
     */
    @GetMapping("/{boardType}")
    public ResponseEntity<CommonResponse> findByAll(@PathVariable("boardType") String boardType, @ModelAttribute SearchParamRequest searchRequest) {
        return boardService.getSliceOfBoard(boardType, searchRequest);
    }

    /**
     * 게시글 수정
     * @param boardType String
     * @param boardIdx Long 게시글 idx
     * @param boardRequest BoardUpdateRequestIO 게시글 요청 데이터
     * @return ResponseEntity<CommonResponse> 게시글 상세 정보
     */
    @PatchMapping("/{boardType}/{boardIdx}")
    public ResponseEntity<CommonResponse> update(@PathVariable("boardType") String boardType, @PathVariable("boardIdx") Long boardIdx, @RequestBody BoardUpdateRequest boardRequest) {
        return boardService.update(boardType, boardIdx, boardRequest);
    }

    /**
     * 게시글 삭제
     * @param boardType String
     * @param boardIdx Long 게시글 idx
     * @return ResponseEntity<CommonResponse> 삭제 응답 결과
     */
    @DeleteMapping("/{boardType}/{boardIdx}")
    public ResponseEntity<CommonResponse> delete(@PathVariable("boardType") String boardType, @PathVariable("boardIdx") Long boardIdx) {
        return boardService.delete(boardType, boardIdx);
    }

//    @PostMapping("/{boardType}/upload")
//    public ResponseEntity<CommonResponse> uploadFile(@RequestParam("multiFile") List<MultipartFile> files) throws IOException {
//        return boardService.uploadBoardFile(files);
//    }

    /**
     * 좋아요
     * @param boardType String
     * @param boardIdx Long 게시글 요청 데이터
     * @return ResponseEntity<CommonResponse> 게시글 상세 정보
     */
    @PostMapping("/{boardType}/like/{boardIdx}")
    public ResponseEntity<CommonResponse> like(@PathVariable("boardType") String boardType, @PathVariable("boardIdx") Long boardIdx) {
        return boardService.upsertLike(boardType, boardIdx);
    }

}
