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
     * @param replyRequest ReplyCreateRequestIO 댓글 요청 데이터
     * @return ResponseEntity<CommonResponse> 댓글 상세 정보
     */
    @PostMapping
    public ResponseEntity<CommonResponse> insert(@RequestBody ReplyCreateRequest replyRequest) {
        return replyService.insert(replyRequest);
    }


    /**
     * 댓글 수정
     * @param replyIdx Long 댓글 idx
     * @param replyRequest ReplyUpdateRequestIO 댓글 요청 데이터
     * @return ResponseEntity<CommonResponse> 댓글 상세 정보
     */
    @PatchMapping("/{replyIdx}")
    public ResponseEntity<CommonResponse> update(@PathVariable("replyIdx") Long replyIdx, @RequestBody ReplyUpdateRequest replyRequest) {
        return replyService.update(replyIdx, replyRequest);
    }


    /**
     * 댓글 삭제
     * @param replyIdx Long 댓글 idx
     * @return ResponseEntity<CommonResponse> 삭제 응답 결과
     */
    @DeleteMapping("/{replyIdx}")
    public ResponseEntity<CommonResponse> delete(@PathVariable("replyIdx") Long replyIdx) {
        return replyService.delete(replyIdx);
    }

}
