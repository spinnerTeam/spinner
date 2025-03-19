package com.spinner.www.mypage.controller;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.mypage.service.MypageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/mypage")
public class MypageRestController {
    private final MypageService mypageService;

    /**
     * 내가 작성한 게시글 목록 조회
     * @param idx Long
     * @param size int
     * @return ResponseEntity<CommonResponse>
     */
    @Operation(description = "내가 작성한 게시글 목록을 조회합니다. <br/>" +
            "<strong>[boardType]</strong> <br/>" +
            "verify : 공부인증글 <br/>" +
            "free   : 자유글",
            responses = {
                    @ApiResponse(content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "20000", description = "요청 성공"),
                    @ApiResponse(responseCode = "40101", description = "권한이 없습니다."),
                    @ApiResponse(responseCode = "50001", description = "데이터를 찾을 수 없음.")
            })
    @GetMapping("/member/board")
    public ResponseEntity<CommonResponse> findAllMemberBoards(@RequestParam(value = "idx", required = false) Long idx,
                                                                  @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        return mypageService.getSliceOfMemberBoard(idx, size);
    }

    /**
     * 내가 좋아요를 누른 게시글 목록 조회
     * @param idx Long
     * @param size int
     * @return ResponseEntity<CommonResponse>
     */
    @Operation(description = "내가 좋아요를 누른 게시글 목록을 조회합니다. <br/>" +
            "<strong>[boardType]</strong> <br/>" +
            "verify : 공부인증글 <br/>" +
            "free   : 자유글",
            responses = {
                    @ApiResponse(content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "20000", description = "요청 성공"),
                    @ApiResponse(responseCode = "40101", description = "권한이 없습니다."),
                    @ApiResponse(responseCode = "50001", description = "데이터를 찾을 수 없음.")
            })
    @GetMapping("/like/board")
    public ResponseEntity<CommonResponse> findAllLikedBoards(@RequestParam(value = "idx", required = false) Long idx,
                                                                  @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        return mypageService.getSliceOfLikedBoard(idx, size);
    }

    /**
     * 북마크한 게시글 목록 조회
     * @param idx Long
     * @param size int
     * @return ResponseEntity<CommonResponse>
     */
    @Operation(description = "북마크한 게시글 목록을 조회합니다. <br/>" +
            "<strong>[boardType]</strong> <br/>" +
            "verify : 공부인증글 <br/>" +
            "free   : 자유글",
            responses = {
                    @ApiResponse(content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "20000", description = "요청 성공"),
                    @ApiResponse(responseCode = "40101", description = "권한이 없습니다."),
                    @ApiResponse(responseCode = "50001", description = "데이터를 찾을 수 없음.")
            })
    @GetMapping("/bookmark/board")
    public ResponseEntity<CommonResponse> findAllBookmarkedBoards(@RequestParam(value = "idx", required = false) Long idx,
                                                                  @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        return mypageService.getSliceOfBookmarkedBoard(idx, size);
    }
}
