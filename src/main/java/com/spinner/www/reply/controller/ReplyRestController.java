package com.spinner.www.reply.controller;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.reply.io.ReplyCreateRequest;
import com.spinner.www.reply.io.ReplyUpdateRequest;
import com.spinner.www.reply.service.ReplyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/reply")

// TODO 스터디 게시판 분기 처리할 것(스터디 원인지 확인 등)
public class ReplyRestController {

    private final ReplyService replyService;

    /**
     * 댓글 생성
     * @param boardType String
     * @param replyRequest ReplyCreateRequestIO 댓글 요청 데이터
     * @return ResponseEntity<CommonResponse> 댓글 상세 정보
     */
    @Operation(description = "댓글을 생성합니다. <br/>" +
            "댓글이 생성된 이후, 생성된 idx와 댓글 정보를 반환합니다.<br/><br/>" +
            "<strong>[boardType]</strong> <br/>" +
            "verify : 공부인증글 <br/>" +
            "free   : 자유글",
            responses = {
                    @ApiResponse(content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "20000", description = "요청 성공"),
                    @ApiResponse(responseCode = "40101", description = "권한이 없습니다."),
                    @ApiResponse(responseCode = "40007", description = "존재하지 않는 댓글입니다."),
            })
    @PostMapping("/{boardType}")
    public ResponseEntity<CommonResponse> insert(@PathVariable("boardType") String boardType,
                                                 @RequestBody ReplyCreateRequest replyRequest) {
        return replyService.insert(boardType, replyRequest);
    }

    /**
     * 스터디 댓글 생성
     * @param boardType String
     * @param studyIdx Long
     * @param replyRequest ReplyCreateRequestIO 댓글 요청 데이터
     * @return ResponseEntity<CommonResponse> 댓글 상세 정보
     */
    @Operation(description = "스터디 댓글을 생성합니다. <br/>" +
            "댓글이 생성된 이후, 생성된 idx와 댓글 정보를 반환합니다.<br/><br/>" +
            "<strong>[boardType]</strong> <br/>" +
            "verify : 공부인증글 <br/>" +
            "free   : 자유글 <br/>" +
            "notice : 공지사항",
            responses = {
                    @ApiResponse(content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "20000", description = "요청 성공"),
                    @ApiResponse(responseCode = "40101", description = "권한이 없습니다."),
                    @ApiResponse(responseCode = "40007", description = "존재하지 않는 댓글입니다."),
            })
    @PostMapping("/studyBoard/{boardType}/{studyIdx}")
    public ResponseEntity<CommonResponse> insert(@PathVariable("boardType") String boardType,
                                                 @PathVariable("studyIdx") Long studyIdx,
                                                 @RequestBody ReplyCreateRequest replyRequest) {
        return replyService.insert(boardType, studyIdx, replyRequest);
    }

    /**
     * 댓글 수정
     * @param boardType String
     * @param replyIdx Long 댓글 idx
     * @param replyRequest ReplyUpdateRequestIO 댓글 요청 데이터
     * @return ResponseEntity<CommonResponse> 댓글 상세 정보
     */
    @Operation(description = "댓글을 수정합니다. <br/>" +
            "해당 idx의 내용을 수정합니다<br/><br/>" +
            "<strong>[boardType]</strong> <br/>" +
            "verify : 공부인증글 <br/>" +
            "free   : 자유글",
            responses = {
                    @ApiResponse(content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "20000", description = "요청 성공"),
                    @ApiResponse(responseCode = "40101", description = "권한이 없습니다."),
                    @ApiResponse(responseCode = "40301", description = "올바르지 않은 접근입니다."),
                    @ApiResponse(responseCode = "40007", description = "존재하지 않는 댓글입니다.")
            })
    @PatchMapping("/{boardType}/{replyIdx}")
    public ResponseEntity<CommonResponse> update(@PathVariable("boardType") String boardType,
                                                 @PathVariable("replyIdx") Long replyIdx,
                                                 @RequestBody ReplyUpdateRequest replyRequest) {
        return replyService.update(boardType, replyIdx, replyRequest);
    }

    /**
     * 스터디 댓글 수정
     * @param boardType String
     * @param replyIdx Long 댓글 idx
     * @param studyIdx Long 스터디 idx
     * @param replyRequest ReplyUpdateRequestIO 댓글 요청 데이터
     * @return ResponseEntity<CommonResponse> 댓글 상세 정보
     */
    @Operation(description = "스터디 댓글을 수정합니다. <br/>" +
            "해당 idx의 내용을 수정합니다<br/><br/>" +
            "<strong>[boardType]</strong> <br/>" +
            "verify : 공부인증글 <br/>" +
            "free   : 자유글",
            responses = {
                    @ApiResponse(content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "20000", description = "요청 성공"),
                    @ApiResponse(responseCode = "40101", description = "권한이 없습니다."),
                    @ApiResponse(responseCode = "40301", description = "올바르지 않은 접근입니다."),
                    @ApiResponse(responseCode = "40007", description = "존재하지 않는 댓글입니다.")
            })
    @PatchMapping( "/studyBoard/{boardType}/{studyIdx}/{replyIdx}")
    public ResponseEntity<CommonResponse> update(@PathVariable("boardType") String boardType,
                                                 @PathVariable("replyIdx") Long replyIdx,
                                                 @PathVariable("studyIdx") Long  studyIdx,
                                                 @RequestBody ReplyUpdateRequest replyRequest) {
        return replyService.update(boardType, replyIdx, studyIdx, replyRequest);
    }


    /**
     * 댓글 삭제
     * @param boardType String
     * @param replyIdx Long 댓글 idx
     * @return ResponseEntity<CommonResponse> 삭제 응답 결과
     */
    @Operation(description = "댓글을 삭제합니다. <br/>" +
            "해당 idx의 게시글을 삭제합니다. <br/><br/>" +
            "<strong>[boardType]</strong> <br/>" +
            "verify : 공부인증글 <br/>" +
            "free   : 자유글",
            responses = {
                    @ApiResponse(content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "20000", description = "요청 성공"),
                    @ApiResponse(responseCode = "40101", description = "권한이 없습니다."),
                    @ApiResponse(responseCode = "40301", description = "올바르지 않은 접근입니다."),
                    @ApiResponse(responseCode = "40007", description = "존재하지 않는 댓글입니다.")
            })
    @DeleteMapping("/{boardType}/{replyIdx}")
    public ResponseEntity<CommonResponse> delete(@PathVariable("boardType") String boardType,
                                                 @PathVariable("replyIdx") Long replyIdx) {
        return replyService.delete(boardType, replyIdx);
    }

    /**
     * 스터디 댓글 삭제
     * @param boardType String
     * @param replyIdx Long 댓글 idx
     * @param studyIdx Long 스터디 idx
     * @return ResponseEntity<CommonResponse> 삭제 응답 결과
     */
    @Operation(description = "스터디 댓글을 삭제합니다. <br/>" +
            "해당 idx의 게시글을 삭제합니다. <br/><br/>" +
            "<strong>[boardType]</strong> <br/>" +
            "verify : 공부인증글 <br/>" +
            "free   : 자유글",
            responses = {
                    @ApiResponse(content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "20000", description = "요청 성공"),
                    @ApiResponse(responseCode = "40101", description = "권한이 없습니다."),
                    @ApiResponse(responseCode = "40301", description = "올바르지 않은 접근입니다."),
                    @ApiResponse(responseCode = "40007", description = "존재하지 않는 댓글입니다.")
            })
    @DeleteMapping("/studyBoard/{boardType}/{studyIdx}/{replyIdx}")
    public ResponseEntity<CommonResponse> delete(@PathVariable("boardType") String boardType,
                                                 @PathVariable("replyIdx") Long replyIdx,
                                                 @PathVariable("studyIdx") Long  studyIdx) {
        return replyService.delete(boardType, replyIdx, studyIdx);
    }
}
