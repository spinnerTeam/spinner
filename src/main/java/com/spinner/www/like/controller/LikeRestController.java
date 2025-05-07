package com.spinner.www.like.controller;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.like.service.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/like")
@PreAuthorize("@sessionInfo.memberIdx != null")
public class LikeRestController {

    private final LikeService likeService;

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
                    @ApiResponse(responseCode = "40006", description = "존재하지 않는 게시글입니다.")
            })
    // boardType은 엔드포인트 구색 맞추기용
    @PostMapping("/board/{boardType}/{boardIdx}")
    public ResponseEntity<CommonResponse> setLikeBoard(@PathVariable("boardType") String boardType,
                                                       @PathVariable("boardIdx") Long boardIdx) {
        return likeService.upsertBoard(boardIdx);
    }

    /**
     * 좋아요
     * @param boardType String
     * @param replyIdx Long 게시글 요청 데이터
     * @return ResponseEntity<CommonResponse> 댓글 상세 정보
     */
    @Operation(description = "댓글에 좋아요를 누릅니다. <br/>" +
            "기존에 좋아요를 누른 상태가 아닐 시 좋아요가 됩니다 <br/>" +
            "기존에 좋아요를 누른 상태 일시 좋아요가 취소됩니다 <br/><br/>" +
            "<strong>[boardType]</strong> <br/>" +
            "verify : 공부인증글 <br/>" +
            "free   : 자유글",
            responses = {
                    @ApiResponse(content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "20000", description = "요청 성공"),
                    @ApiResponse(responseCode = "40101", description = "권한이 없습니다."),
                    @ApiResponse(responseCode = "40007", description = "존재하지 않는 댓글입니다."),
                    @ApiResponse(responseCode = "40006", description = "존재하지 않는 게시글입니다.")
            })
    @PostMapping("/reply/{boardType}/{replyIdx}")
    public ResponseEntity<CommonResponse> setLikeReply(@PathVariable("boardType") String boardType,
                                                       @PathVariable("replyIdx") Long replyIdx) {
        return likeService.upsertReply(replyIdx);
    }



}
