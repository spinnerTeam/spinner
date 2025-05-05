package com.spinner.www.profile.controller;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.member.io.MemberProfileUpdateRequest;
import com.spinner.www.profile.service.ProfileService;
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
@RequestMapping("/profiles")
public class ProfilesRestController {
    private final ProfileService profileService;

    /**
     * 나의 프로필 조회
     *
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
    @GetMapping("/me")
    public ResponseEntity<CommonResponse> getMemberProfile() {
        return profileService.getMemberProfile();
    }

    /**
     * 나의 프로필 업데이트
     *
     * @return ResponseEntity<CommonResponse>
     */
    @Operation(description = "나의 프로필을 수정합니다. <br/>" +
            "file(MultipartFile), <br/>" +
            "profileRequest(json) : {<br/>" +
            "&nbsp;&nbsp;nickName       : String <br/>" +
            "&nbsp;&nbsp;birth          : String YYYY-MM-DD<br/>" +
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
    @PatchMapping(value = "/me", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommonResponse> updateMemberProfile(@RequestPart(value = "profileRequest") MemberProfileUpdateRequest profileRequest
            , @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        return profileService.updateMemberProfile(profileRequest, file);
    }

    /**
     * 다른 사용자 프로필 조회
     * @param memberIdx Long 조회할 사용자의 idx
     * @return ResponseEntity<CommonResponse>
     */
    @Operation(description = "다른 사용자의 프로필을 조회합니다. <br/>" +
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
    @GetMapping("/{memberIdx}")
    public ResponseEntity<CommonResponse> getMemberProfile(@PathVariable(value = "memberIdx") Long memberIdx) {
        return profileService.getMemberProfile(memberIdx);
    }

    /**
     * 내가 작성한 게시글 목록 조회
     *
     * @param idx  Long
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
    @GetMapping("/me/board")
    public ResponseEntity<CommonResponse> findAllMemberBoards(@RequestParam(value = "idx", required = false) Long idx,
                                                              @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        return profileService.getSliceOfMemberBoard(idx, size);
    }

    /**
     * 다른 사람이 작성한 게시글 목록 조회
     * @param memberIdx Long 조회할 사용자의 idx
     * @param idx  Long
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
    @GetMapping("/{memberIdx}/board")
    public ResponseEntity<CommonResponse> findAllMemberBoards(@PathVariable(value = "memberIdx") Long memberIdx,
                                                              @RequestParam(value = "idx", required = false) Long idx,
                                                              @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        return profileService.getSliceOfMemberBoard(idx, size, memberIdx);
    }

    /**
     * 내가 좋아요를 누른 게시글 목록 조회
     *
     * @param idx  Long
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
    @GetMapping("/me/likedBoard")
    public ResponseEntity<CommonResponse> findAllLikedBoards(@RequestParam(value = "idx", required = false) Long idx,
                                                             @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        return profileService.getSliceOfLikedBoard(idx, size);
    }

    /**
     * 북마크한 게시글 목록 조회
     *
     * @param idx  Long
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
    @GetMapping("/me/bookmarkedBoard")
    public ResponseEntity<CommonResponse> findAllBookmarkedBoards(@RequestParam(value = "idx", required = false) Long idx,
                                                                  @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        return profileService.getSliceOfBookmarkedBoard(idx, size);
    }

}
