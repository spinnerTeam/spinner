package com.spinner.www.reply.service;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.reply.entity.Reply;
import com.spinner.www.reply.io.ReplyCreateRequest;
import com.spinner.www.reply.io.ReplyUpdateRequest;
import com.spinner.www.study.entity.Study;
import org.springframework.http.ResponseEntity;

public interface ReplyService {

    /**
     * 댓글 생성
     * @param boardType String
     * @param replyRequest ReplyCreateRequestIO 댓글 요청 데이터
     * @return ResponseEntity<CommonResponse> 댓글 상세 정보
     */
    ResponseEntity<CommonResponse> insert(String boardType, ReplyCreateRequest replyRequest);

    /**
     * 스터디 댓글 생성
     * @param boardType String
     * @param studyIdx Long 스터디 idx
     * @param replyRequest ReplyCreateRequestIO 댓글 요청 데이터
     * @return ResponseEntity<CommonResponse> 댓글 상세 정보
     */
    ResponseEntity<CommonResponse> insert(String boardType, Long studyIdx, ReplyCreateRequest replyRequest);

    /**
     * 댓글 조회
     * @param replyIdx Long 댓글 idx
     * @return ResponseEntity<CommonResponse> 댓글 상세 정보
     */
    Reply getReplyOrThrow(Long replyIdx);

    /**
     * 댓글 조회
     * @param replyIdx Long 댓글 idx
     * @param codeIdx Long 게시판 타입 idx
     * @return ResponseEntity<CommonResponse> 댓글 상세 정보
     */
    Reply getReplyOrThrow(Long replyIdx, Long codeIdx);

    /**
     * 댓글 조회
     * @param replyIdx Long 댓글 idx
     * @param codeIdx Long 게시판 타입 idx
     * @param study Study 스터디 객체
     * @return ResponseEntity<CommonResponse> 댓글 상세 정보
     */
    Reply getReplyOrThrow(Long replyIdx, Long codeIdx, Study study);


    /**
     * 댓글 수정
     * @param boardType String
     * @param replyIdx Long 댓글 idx
     * @param replyRequest ReplyUpdateRequestIO 댓글 수정 데이터
     * @return ResponseEntity<CommonResponse> 댓글 상세 정보
     */
    ResponseEntity<CommonResponse> update(String boardType, Long replyIdx, ReplyUpdateRequest replyRequest);

    /**
     * 댓글 수정
     * @param boardType String
     * @param replyIdx Long 댓글 idx
     * @param studyIdx Long 스터디 idx
     * @param replyRequest ReplyUpdateRequestIO 댓글 수정 데이터
     * @return ResponseEntity<CommonResponse> 댓글 상세 정보
     */
    ResponseEntity<CommonResponse> update(String boardType, Long replyIdx, Long studyIdx, ReplyUpdateRequest replyRequest);

    /**
     * 댓글 삭제
     * @param boardType String
     * @param replyIdx Long 댓글 idx
     * @return ResponseEntity<CommonResponse> 삭제 응답 결과
     */
    ResponseEntity<CommonResponse> delete(String boardType, Long replyIdx);

    /**
     * 댓글 삭제
     * @param boardType String
     * @param replyIdx Long 댓글 idx
     * @param studyIdx Long 스터디 idx
     * @return ResponseEntity<CommonResponse> 삭제 응답 결과
     */
    ResponseEntity<CommonResponse> delete(String boardType, Long replyIdx, Long studyIdx);
}
