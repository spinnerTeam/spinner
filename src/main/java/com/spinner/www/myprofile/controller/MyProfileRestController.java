package com.spinner.www.myprofile.controller;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.member.io.MemberProfileUpdateRequest;
import com.spinner.www.myprofile.service.MyProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/myprofile")
public class MyProfileRestController {
    private final MyProfileService myProfileService;

    /**
     * 나의 프로필 조회
     * @return ResponseEntity<CommonResponse>
     */
    @Operation(description = "나의 프로필을 조회합니다. <br/>" +
            "nickName       : String <br/>" +
            "birth          : String YYYY-MM-DD<br/>" +
            "profileImageUrl: String<br/>" +
            "interests      : [{<br/>" +
            "&nbsp;&nbsp;&nbsp;&nbsp;idx        : String<br/>" +
            "&nbsp;&nbsp;&nbsp;&nbsp;name       : String<br/>" +
            "&nbsp;&nbsp;&nbsp;&nbsp;isSelected : boolean<br/>" +
            "}..]"
            ,
            responses = {
                    @ApiResponse(content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "20000", description = "요청 성공"),
                    @ApiResponse(responseCode = "40101", description = "권한이 없습니다."),
                    @ApiResponse(responseCode = "50001", description = "데이터를 찾을 수 없음.")
            })
    @GetMapping("")
    public ResponseEntity<CommonResponse> getMemberProfile() {
        return myProfileService.getMemberProfile();
    }

    /**
     * 나의 프로필 업데이트
     * @return ResponseEntity<CommonResponse>
     */
    @Operation(description = "나의 프로필을 수정합니다. <br/>" +
            "file(MultipartFile), <br/>" +
            "profileRequest(json) : {<br/>" +
            "&nbsp;&nbsp;nickName       : String <br/>" +
            "&nbsp;&nbsp;birth          : String YYYY-MM-DD<br/>" +
            "&nbsp;&nbsp;profileImageUrl: <b>미구현 파일 업로드 처리할 예정</b>" +
            "&nbsp;&nbsp;interests      : [{<br/>" +
            "&nbsp;&nbsp;&nbsp;&nbsp;idx        : String<br/>" +
            "&nbsp;&nbsp;&nbsp;&nbsp;isSelected : boolean<br/>" +
            "&nbsp;&nbsp;}..]" +
            "}",
            responses = {
                    @ApiResponse(content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "20000", description = "요청 성공"),
                    @ApiResponse(responseCode = "40101", description = "권한이 없습니다."),
                    @ApiResponse(responseCode = "50001", description = "데이터를 찾을 수 없음.")
            })
    @PatchMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommonResponse> updateMemberProfile(@RequestPart(value="profileRequest") MemberProfileUpdateRequest profileRequest
                                                             ,@RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        return myProfileService.updateMemberProfile(profileRequest, file);
    }

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
        return myProfileService.getSliceOfMemberBoard(idx, size);
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
        return myProfileService.getSliceOfLikedBoard(idx, size);
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
        return myProfileService.getSliceOfBookmarkedBoard(idx, size);
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
    @GetMapping("/hot/board/{boardType}")
    public ResponseEntity<CommonResponse> findByAll(@PathVariable("boardType") String boardType,
                                                    @RequestParam(value = "idx", required = false) Long idx,
                                                    @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        return myProfileService.getSliceOfHotBoard(boardType, idx, size);
    }
}
