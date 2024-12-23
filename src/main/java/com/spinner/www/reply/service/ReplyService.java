package com.spinner.www.reply.service;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.reply.entity.Reply;
import com.spinner.www.reply.io.ReplyCreateRequest;
import com.spinner.www.reply.io.ReplyUpdateRequest;
import org.springframework.http.ResponseEntity;

public interface ReplyService {

    /**
     * 댓글 생성
     * @param replyRequest ReplyCreateRequestIO 댓글 요청 데이터
     * @return ResponseEntity<CommonResponse> 댓글 상세 정보
     */
    ResponseEntity<CommonResponse> insert(ReplyCreateRequest replyRequest);

    /**
     * 댓글 uuid로 삭제되지 않은 댓글 조회
     * @param replyIdx Long 댓글 idx
     * @return ResponseEntity<CommonResponse> 댓글 상세 정보
     */
    Reply findByReplyIdx(Long replyIdx);


    /**
     * 댓글 수정
     * @param replyIdx Long 댓글 idx
     * @param replyRequest ReplyUpdateRequestIO 댓글 수정 데이터
     * @return ResponseEntity<CommonResponse> 댓글 상세 정보
     */
    ResponseEntity<CommonResponse> update(Long replyIdx, ReplyUpdateRequest replyRequest);

    /**
     * 댓글 삭제
     * @param replyIdx Long 댓글 idx
     * @return ResponseEntity<CommonResponse> 삭제 응답 결과
     */
    ResponseEntity<CommonResponse> delete(Long replyIdx);
}
