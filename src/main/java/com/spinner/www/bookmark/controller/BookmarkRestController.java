package com.spinner.www.bookmark.controller;

import com.spinner.www.bookmark.service.BookmarkService;
import com.spinner.www.common.io.CommonResponse;
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
@RequestMapping("/bookmark")
@PreAuthorize("@sessionInfo.memberIdx != null")
public class BookmarkRestController {

    private final BookmarkService bookmarkService;

    /**
     * 북마크
     * @param boardIdx Long 게시글 요청 데이터
     * @return ResponseEntity<CommonResponse> 게시글 상세 정보
     */
    @Operation(description = "게시글에 북마크를 누릅니다. <br/>" +
            "기존에 북마크를 누른 상태가 아닐 시 북마크가 됩니다 <br/>" +
            "기존에 북마크를 누른 상태 일시 북마크가 취소됩니다 <br/><br/>" +
            "<strong>[boardType]</strong> <br/>" +
            "verify : 공부인증글 <br/>" +
            "free   : 자유글",
            responses = {
                    @ApiResponse(content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "20000", description = "요청 성공"),
                    @ApiResponse(responseCode = "40101", description = "권한이 없습니다."),
                    @ApiResponse(responseCode = "40006", description = "존재하지 않는 게시글입니다.")
            })
    @PostMapping("/board/{boardType}/{boardIdx}")
    public ResponseEntity<CommonResponse> setBookmark(@PathVariable("boardIdx") Long boardIdx) {
        return bookmarkService.upsertBoard(boardIdx);
    }
}
