package com.spinner.www.board.controller;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.common.io.SearchParamRequest;
import com.spinner.www.board.io.BoardCreateRequest;
import com.spinner.www.board.io.BoardUpdateRequest;
import com.spinner.www.board.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(description = "게시글을 생성합니다. <br/>" +
            "게시글이 생성된 이후, 생성된 idx와 게시글 정보를 반환합니다.<br/><br/>" +
            "<strong>[boardType]</strong> <br/>" +
            "verify : 공부인증글 <br/>" +
            "free   : 자유글",
            responses = {
                    @ApiResponse(content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "20000", description = "요청 성공"),
                    @ApiResponse(responseCode = "40101", description = "권한이 없습니다."),
            })
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
    @Operation(description = "게시글을 조회합니다. <br/>" +
            "idx에 해당하는 게시글 정보를 반환합니다. <br/><br/>" +
            "<strong>[boardType]</strong> <br/>" +
            "verify : 공부인증글 <br/>" +
            "free   : 자유글",
            responses = {
                    @ApiResponse(content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "20000", description = "요청 성공"),
                    @ApiResponse(responseCode = "50001", description = "데이터를 찾을 수 없음.")
            })
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
    @Operation(description = "게시글 목록을 조회합니다. <br/>" +
            "해당 idx부터 size까지 게시글의 목록을 출력합니다 <br/>" +
            "keyword에 값이 있을 시 keyword에 해당하는 제목과 작성자를 출력합니다.<br/><br/>" +
            "<strong>[boardType]</strong> <br/>" +
            "verify : 공부인증글 <br/>" +
            "free   : 자유글",
            responses = {
                    @ApiResponse(content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "20000", description = "요청 성공"),
                    @ApiResponse(responseCode = "50001", description = "데이터를 찾을 수 없음.")
            })
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
    @Operation(description = "게시글을 수정합니다. <br/>" +
            "해당 idx의 제목과 내용을 수정합니다<br/><br/>" +
            "<strong>[boardType]</strong> <br/>" +
            "verify : 공부인증글 <br/>" +
            "free   : 자유글",
            responses = {
                    @ApiResponse(content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "20000", description = "요청 성공"),
                    @ApiResponse(responseCode = "40101", description = "권한이 없습니다."),
                    @ApiResponse(responseCode = "40301", description = "올바르지 않은 접근입니다."),
                    @ApiResponse(responseCode = "50001", description = "데이터를 찾을 수 없음.")
            })
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
    @Operation(description = "게시글을 삭제합니다. <br/>" +
            "해당 idx의 게시글을 삭제합니다. <br/><br/>" +
            "<strong>[boardType]</strong> <br/>" +
            "verify : 공부인증글 <br/>" +
            "free   : 자유글",
            responses = {
                    @ApiResponse(content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "20000", description = "요청 성공"),
                    @ApiResponse(responseCode = "40101", description = "권한이 없습니다."),
                    @ApiResponse(responseCode = "40301", description = "올바르지 않은 접근입니다."),
                    @ApiResponse(responseCode = "50001", description = "데이터를 찾을 수 없음.")
            })
    @DeleteMapping("/{boardType}/{boardIdx}")
    public ResponseEntity<CommonResponse> delete(@PathVariable("boardType") String boardType, @PathVariable("boardIdx") Long boardIdx) {
        return boardService.delete(boardType, boardIdx);
    }

    /**
     * 좋아요
     * @param boardType String
     * @param boardIdx Long 게시글 요청 데이터
     * @return ResponseEntity<CommonResponse> 게시글 상세 정보
     */
    @Operation(description = "게시글에 좋아요를 누릅니다. <br/>" +
            "기존에 좋아요를 누른 상태가 아닐 시 좋아요가 됩니다 <br/>" +
            "기존에 좋아요를 누른 상태 일시 좋아요가 취소됩니다 <br/><br/>" +
            "<strong>[boardType]</strong> <br/>" +
            "verify : 공부인증글 <br/>" +
            "free   : 자유글",
            responses = {
                    @ApiResponse(content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "20000", description = "요청 성공"),
                    @ApiResponse(responseCode = "40101", description = "권한이 없습니다."),
                    @ApiResponse(responseCode = "50001", description = "데이터를 찾을 수 없음.")
            })
    @PostMapping("/{boardType}/like/{boardIdx}")
    public ResponseEntity<CommonResponse> like(@PathVariable("boardType") String boardType, @PathVariable("boardIdx") Long boardIdx) {
        return boardService.upsertLike(boardType, boardIdx);
    }

}
