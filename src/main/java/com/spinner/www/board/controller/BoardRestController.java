package com.spinner.www.board.controller;

import com.spinner.www.common.io.CommonResponse;
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
            "free   : 자유글 <br/>" +
            "notice : 공지사항 <br/>" +
            "게시글의 img, video 태그 src 속성에 업로드할 이미지와 비디오의 오리지널 파일명을 넣어주셔야 태그에서 사용 가능합니다. <br/>" +
            "ex) 파일명이 \"Group+7@2x (1).png\"이면 &lt;img src=\"Group+7@2x (1).png\"/&gt;로 처리",
//            "",
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
            "idx(pk)에 해당하는 게시글 정보를 반환합니다. <br/><br/>" +
            "<strong>[boardType]</strong> <br/>" +
            "verify : 공부인증글 <br/>" +
            "free   : 자유글 <br/>" +
            "notice : 공지사항 <br/>" ,
            responses = {
                    @ApiResponse(content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "20000", description = "요청 성공"),
                    @ApiResponse(responseCode = "40006", description = "존재하지 않는 게시글입니다.")
            })
    @GetMapping("/{boardType}/{boardIdx}")
    public ResponseEntity<CommonResponse> findByBoardInfo(@PathVariable("boardType") String boardType,
                                                          @PathVariable("boardIdx") Long boardIdx) {
        return boardService.findByBoardInfo(boardType, boardIdx);
    }

    /**
     * 게시글 목록 조회
     * @param boardType String
     * @param idx Long 조회 시작 idx
     * @param size int 조회할 목록 갯수
     * @param keyword String 조회할 키워드
     * @return searchParamRequest<CommonResponse> 게시글 목록
     */
    @Operation(description = "게시글 목록을 조회합니다. <br/>" +
            "해당 idx부터 size까지 게시글의 목록을 출력합니다 <br/>" +
            "keyword에 값이 있을 시 keyword에 해당하는 제목과 작성자를 출력합니다.<br/><br/>" +
            "<strong>[boardType]</strong> <br/>" +
            "verify : 공부인증글 <br/>" +
            "free   : 자유글 <br/>" +
            "notice : 공지사항 <br/>" ,
            responses = {
                    @ApiResponse(content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "20000", description = "요청 성공"),
            })
    @GetMapping("/{boardType}")
    public ResponseEntity<CommonResponse> findByAll(@PathVariable("boardType") String boardType,
                                                    @RequestParam(value = "idx", required = false) Long idx,
                                                    @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                                                    @RequestParam(value = "keyword", required = false) String keyword) {
        return boardService.getSliceOfBoard(boardType, idx, size, keyword);
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
            "free   : 자유글 <br/>" +
            "notice : 공지사항 <br/>" ,
            responses = {
                    @ApiResponse(content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "20000", description = "요청 성공"),
                    @ApiResponse(responseCode = "40101", description = "권한이 없습니다."),
                    @ApiResponse(responseCode = "40301", description = "올바르지 않은 접근입니다."),
                    @ApiResponse(responseCode = "40006", description = "존재하지 않는 게시글입니다.")
            })
    @PatchMapping("/{boardType}/{boardIdx}")
    public ResponseEntity<CommonResponse> update(@PathVariable("boardType") String boardType,
                                                 @PathVariable("boardIdx") Long boardIdx,
                                                 @RequestBody BoardUpdateRequest boardRequest) {
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
            "free   : 자유글 <br/>" +
            "notice : 공지사항 <br/>" ,
            responses = {
                    @ApiResponse(content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "20000", description = "요청 성공"),
                    @ApiResponse(responseCode = "40101", description = "권한이 없습니다."),
                    @ApiResponse(responseCode = "40301", description = "올바르지 않은 접근입니다."),
                    @ApiResponse(responseCode = "40006", description = "존재하지 않는 게시글입니다.")
            })
    @DeleteMapping("/{boardType}/{boardIdx}")
    public ResponseEntity<CommonResponse> delete(@PathVariable("boardType") String boardType,
                                                 @PathVariable("boardIdx") Long boardIdx) {
        return boardService.delete(boardType, boardIdx);
    }

    /**
     * 인기 게시글 목록 조회
     * @param boardType String
     * @param idx Long 조회 시작 idx
     * @param size int 조회할 목록 갯수
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
            })
    @GetMapping("/hot/{boardType}")
    public ResponseEntity<CommonResponse> findByAll(@PathVariable("boardType") String boardType,
                                                    @RequestParam(value = "idx", required = false) Long idx,
                                                    @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        return boardService.getSliceOfHotBoard(boardType, idx, size);
    }
}
