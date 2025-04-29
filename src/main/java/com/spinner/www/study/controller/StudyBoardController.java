package com.spinner.www.study.controller;

import com.spinner.www.board.io.BoardCreateRequest;
import com.spinner.www.board.io.BoardUpdateRequest;
import com.spinner.www.bookmark.service.BookmarkService;
import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.like.service.LikeService;
import com.spinner.www.reply.io.ReplyCreateRequest;
import com.spinner.www.reply.io.ReplyUpdateRequest;
import com.spinner.www.reply.service.ReplyService;
import com.spinner.www.study.service.StudyBoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/studyBoard")
public class StudyBoardController {

    private final StudyBoardService studyBoardService;

    /**
     * 스터디 게시글 생성
     * @param boardType String
     * @param studyIdx Long 조회할 스터디 idx
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
    @PostMapping("/board/{boardType}/{studyIdx}")
    public ResponseEntity<CommonResponse> insertStudy(
            @PathVariable("boardType") String boardType,
            @PathVariable("studyIdx") Long studyIdx,
            @RequestPart(value="boardRequest") BoardCreateRequest boardRequest, // JSON 데이터
            @RequestPart(value = "multiFile", required = false) List<MultipartFile> files // 파일 데이터
    ) {
        return studyBoardService.insert(boardType, studyIdx, boardRequest, files);
    }

    /**
     * 스터디 게시글 조회
     * @param boardType String
     * @param boardIdx Long 게시글 idx
     * @param studyIdx Long 조회할 스터디 idx
     * @return ResponseEntity<CommonResponse> 게시글 상세 정보
     */
    @Operation(description = "게시글을 조회합니다. <br/>" +
            "idx(pk)에 해당하는 게시글 정보를 반환합니다. <br/><br/>" +
            "<strong>[boardType]</strong> <br/>" +
            "verify : 공부인증글 <br/>" +
            "free   : 자유글 <br/>"+
            "notice : 공지사항 <br/>" ,
            responses = {
                    @ApiResponse(content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "20000", description = "요청 성공"),
                    @ApiResponse(responseCode = "50001", description = "데이터를 찾을 수 없음.")
            })
    @GetMapping("/board/{boardType}/{studyIdx}/{boardIdx}")
    public ResponseEntity<CommonResponse> findByBoardInfo(@PathVariable("boardType") String boardType,
                                                          @PathVariable("boardIdx") Long boardIdx,
                                                          @PathVariable("studyIdx") Long studyIdx) {
        return studyBoardService.findByBoardInfo(boardType, studyIdx, boardIdx);
    }


    /**
     * 스터디 게시글 목록 조회
     * @param boardType String
     * @param idx Long 조회 시작 idx
     * @param size int 조회할 목록 갯수
     * @param keyword String 조회할 키워드
     * @param studyIdx Long 조회할 스터디 idx
     * @return searchParamRequest<CommonResponse> 게시글 목록
     */
    @Operation(description = "게시글 목록을 조회합니다. <br/>" +
            "해당 idx부터 size까지 게시글의 목록을 출력합니다 <br/>" +
            "keyword에 값이 있을 시 keyword에 해당하는 제목과 작성자를 출력합니다.<br/><br/>" +
            "<strong>[boardType]</strong> <br/>" +
            "verify : 공부인증글 <br/>" +
            "free   : 자유글 <br/>"+
            "notice : 공지사항 <br/>" ,
            responses = {
                    @ApiResponse(content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "20000", description = "요청 성공"),
            })
    @GetMapping("/board/{boardType}/{studyIdx}")
    public ResponseEntity<CommonResponse> findByAll(@PathVariable("boardType") String boardType,
                                                    @PathVariable("studyIdx") Long studyIdx,
                                                    @RequestParam(value = "idx", required = false) Long idx,
                                                    @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                                                    @RequestParam(value = "keyword", required = false) String keyword) {
        return studyBoardService.getSliceOfBoard(boardType, idx, size, keyword, studyIdx);
    }

    /**
     * (스터디)게시글 수정
     * @param boardType String
     * @param boardIdx Long 게시글 idx
     * @param boardRequest BoardUpdateRequestIO 게시글 요청 데이터
     * @return ResponseEntity<CommonResponse> 게시글 상세 정보
     */
    @Operation(description = "게시글을 수정합니다. <br/>" +
            "해당 idx의 제목과 내용을 수정합니다<br/><br/>" +
            "<strong>[boardType]</strong> <br/>" +
            "verify : 공부인증글 <br/>" +
            "free   : 자유글 <br/>"+
            "notice : 공지사항 <br/>" ,
            responses = {
                    @ApiResponse(content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "20000", description = "요청 성공"),
                    @ApiResponse(responseCode = "40101", description = "권한이 없습니다."),
                    @ApiResponse(responseCode = "40301", description = "올바르지 않은 접근입니다."),
                    @ApiResponse(responseCode = "50001", description = "데이터를 찾을 수 없음.")
            })
    @PatchMapping("/board/{boardType}/{studyIdx}/{boardIdx}")
    public ResponseEntity<CommonResponse> update(@PathVariable("boardType") String boardType,
                                                 @PathVariable("boardIdx") Long boardIdx,
                                                 @PathVariable("studyIdx") Long studyIdx,
                                                 @RequestBody BoardUpdateRequest boardRequest) {
        return studyBoardService.update(boardType, boardIdx, studyIdx, boardRequest);
    }

    /**
     * (스터디)게시글 삭제
     * @param boardType String
     * @param boardIdx Long 게시글 idx
     * @return ResponseEntity<CommonResponse> 삭제 응답 결과
     */
    @Operation(description = "게시글을 삭제합니다. <br/>" +
            "해당 idx의 게시글을 삭제합니다. <br/><br/>" +
            "<strong>[boardType]</strong> <br/>" +
            "verify : 공부인증글 <br/>" +
            "free   : 자유글 <br/>"+
            "notice : 공지사항 <br/>" ,
            responses = {
                    @ApiResponse(content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "20000", description = "요청 성공"),
                    @ApiResponse(responseCode = "40101", description = "권한이 없습니다."),
                    @ApiResponse(responseCode = "40301", description = "올바르지 않은 접근입니다."),
                    @ApiResponse(responseCode = "50001", description = "데이터를 찾을 수 없음.")
            })
    @DeleteMapping("/board/{boardType}/{studyIdx}/{boardIdx}")
    public ResponseEntity<CommonResponse> delete(@PathVariable("boardType") String boardType,
                                                 @PathVariable("studyIdx") Long studyIdx,
                                                 @PathVariable("boardIdx") Long boardIdx) {
        return studyBoardService.delete(boardType, boardIdx, studyIdx);
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
    @PostMapping("/reply/{boardType}/{studyIdx}")
    public ResponseEntity<CommonResponse> insertReply(@PathVariable("boardType") String boardType,
                                                 @PathVariable("studyIdx") Long studyIdx,
                                                 @RequestBody ReplyCreateRequest replyRequest) {
        return studyBoardService.insertReply(boardType, studyIdx, replyRequest);
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
            "free   : 자유글 <br/>"+
            "notice : 공지사항 <br/>" ,
            responses = {
                    @ApiResponse(content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "20000", description = "요청 성공"),
                    @ApiResponse(responseCode = "40101", description = "권한이 없습니다."),
                    @ApiResponse(responseCode = "40301", description = "올바르지 않은 접근입니다."),
                    @ApiResponse(responseCode = "40007", description = "존재하지 않는 댓글입니다.")
            })
    @PatchMapping( "/reply/{boardType}/{studyIdx}/{replyIdx}")
    public ResponseEntity<CommonResponse> updateReply(@PathVariable("boardType") String boardType,
                                                 @PathVariable("replyIdx") Long replyIdx,
                                                 @PathVariable("studyIdx") Long  studyIdx,
                                                 @RequestBody ReplyUpdateRequest replyRequest) {
        return studyBoardService.updateReply(boardType, replyIdx, studyIdx, replyRequest);
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
            "free   : 자유글 <br/>"+
            "notice : 공지사항 <br/>" ,
            responses = {
                    @ApiResponse(content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "20000", description = "요청 성공"),
                    @ApiResponse(responseCode = "40101", description = "권한이 없습니다."),
                    @ApiResponse(responseCode = "40301", description = "올바르지 않은 접근입니다."),
                    @ApiResponse(responseCode = "40007", description = "존재하지 않는 댓글입니다.")
            })
    @DeleteMapping("/reply/{boardType}/{studyIdx}/{replyIdx}")
    public ResponseEntity<CommonResponse> deleteReply(@PathVariable("boardType") String boardType,
                                                 @PathVariable("replyIdx") Long replyIdx,
                                                 @PathVariable("studyIdx") Long  studyIdx) {
        return studyBoardService.deleteReply(boardType, replyIdx, studyIdx);
    }


    /**
     * 좋아요
     * @param boardType String
     * @param studyIdx Long
     * @param boardIdx Long 게시글 요청 데이터
     * @return ResponseEntity<CommonResponse> 게시글 상세 정보
     */
    @Operation(description = "게시글에 좋아요를 누릅니다. <br/>" +
            "기존에 좋아요를 누른 상태가 아닐 시 좋아요가 됩니다 <br/>" +
            "기존에 좋아요를 누른 상태 일시 좋아요가 취소됩니다 <br/><br/>" +
            "<strong>[boardType]</strong> <br/>" +
            "verify : 공부인증글 <br/>" +
            "free   : 자유글 <br/>"+
            "notice : 공지사항 <br/>" ,
            responses = {
                    @ApiResponse(content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "20000", description = "요청 성공"),
                    @ApiResponse(responseCode = "40101", description = "권한이 없습니다."),
                    @ApiResponse(responseCode = "50001", description = "데이터를 찾을 수 없음.")
            })
    // boardType은 엔드포인트 구색 맞추기용
    @PostMapping("/like/board/{boardType}/{studyIdx}/{boardIdx}")
    public ResponseEntity<CommonResponse> setLikeBoard(@PathVariable("boardType") String boardType,
                                                       @PathVariable("studyIdx") Long studyIdx,
                                                       @PathVariable("boardIdx") Long boardIdx) {
        return studyBoardService.upsertLikeBoard(studyIdx, boardIdx);
    }

    /**
     * 좋아요
     * @param boardType String
     * @param studyIdx Long
     * @param replyIdx Long 게시글 요청 데이터
     * @return ResponseEntity<CommonResponse> 댓글 상세 정보
     */
    @Operation(description = "댓글에 좋아요를 누릅니다. <br/>" +
            "기존에 좋아요를 누른 상태가 아닐 시 좋아요가 됩니다 <br/>" +
            "기존에 좋아요를 누른 상태 일시 좋아요가 취소됩니다 <br/><br/>" +
            "<strong>[boardType]</strong> <br/>" +
            "verify : 공부인증글 <br/>" +
            "free   : 자유글 <br/>"+
            "notice : 공지사항 <br/>" ,
            responses = {
                    @ApiResponse(content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "20000", description = "요청 성공"),
                    @ApiResponse(responseCode = "40101", description = "권한이 없습니다."),
                    @ApiResponse(responseCode = "50001", description = "데이터를 찾을 수 없음.")
            })
    @PostMapping("/like/reply/{boardType}/{studyIdx}/{boardIdx}/{replyIdx}")
    public ResponseEntity<CommonResponse> setLikeReply(@PathVariable("boardType") String boardType,
                                                       @PathVariable("studyIdx") Long studyIdx,
                                                       @PathVariable("replyIdx") Long replyIdx) {
        return studyBoardService.upsertLikeReply(studyIdx, replyIdx);
    }

    /**
     * 북마크
     * @Param boardType String 게시글 종류
     * @param boardIdx Long 게시글 요청 데이터
     * @return ResponseEntity<CommonResponse> 게시글 상세 정보
     */
    @Operation(description = "게시글에 북마크를 누릅니다. <br/>" +
            "기존에 북마크를 누른 상태가 아닐 시 북마크가 됩니다 <br/>" +
            "기존에 북마크를 누른 상태 일시 북마크가 취소됩니다 <br/><br/>" +
            "<strong>[boardType]</strong> <br/>" +
            "verify : 공부인증글 <br/>" +
            "free   : 자유글 <br/>"+
            "notice : 공지사항 <br/>" ,
            responses = {
                    @ApiResponse(content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "20000", description = "요청 성공"),
                    @ApiResponse(responseCode = "40101", description = "권한이 없습니다."),
                    @ApiResponse(responseCode = "50001", description = "데이터를 찾을 수 없음.")
            })
    @PostMapping("/bookmark/board/{boardType}/{studyIdx}/{boardIdx}")
    public ResponseEntity<CommonResponse> setBookmark(@PathVariable("studyIdx") Long studyIdx,
                                                      @PathVariable("boardIdx") Long boardIdx) {
        return studyBoardService.upsertBookmarkBoard(studyIdx, boardIdx);
    }
}
