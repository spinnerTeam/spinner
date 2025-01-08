package com.spinner.www.reply.controller;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.reply.io.ReplyCreateRequest;
import com.spinner.www.reply.io.ReplyUpdateRequest;
import com.spinner.www.reply.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/reply")
public class ReplyRestController {

    private final ReplyService replyService;

    /**
     * 댓글 생성
     * @param boardType String
     * @param replyRequest ReplyCreateRequestIO 댓글 요청 데이터
     * @return ResponseEntity<CommonResponse> 댓글 상세 정보
     */
    @PostMapping("/{boardType}")
    public ResponseEntity<CommonResponse> insert(@PathVariable("boardType") String boardType, @RequestBody ReplyCreateRequest replyRequest) {
        return replyService.insert(boardType, replyRequest);
    }


    /**
     * 댓글 수정
     * @param boardType String
     * @param replyIdx Long 댓글 idx
     * @param replyRequest ReplyUpdateRequestIO 댓글 요청 데이터
     * @return ResponseEntity<CommonResponse> 댓글 상세 정보
     */
    @PatchMapping("/{boardType}/{replyIdx}")
    public ResponseEntity<CommonResponse> update(@PathVariable("boardType") String boardType, @PathVariable("replyIdx") Long replyIdx, @RequestBody ReplyUpdateRequest replyRequest) {
        return replyService.update(boardType, replyIdx, replyRequest);
    }


    /**
     * 댓글 삭제
     * @param boardType String
     * @param replyIdx Long 댓글 idx
     * @return ResponseEntity<CommonResponse> 삭제 응답 결과
     */
    @DeleteMapping("/{boardType}/{replyIdx}")
    public ResponseEntity<CommonResponse> delete(@PathVariable("boardType") String boardType, @PathVariable("replyIdx") Long replyIdx) {
        return replyService.delete(boardType, replyIdx);
    }

}
