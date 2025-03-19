package com.spinner.www.like.service;

import com.spinner.www.board.entity.Board;
import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.like.entity.Like;
import com.spinner.www.reply.entity.Reply;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface LikeService {

    /**
     * 좋아요 생성
     * @param boardIdx Long
     * @return ResponseEntity<CommonResponse> 좋아요 상세 정보
     */
    ResponseEntity<CommonResponse> upsertBoard(Long boardIdx);

    /**
     * 좋아요 생성
     * @param replyIdx Long
     * @return ResponseEntity<CommonResponse> 좋아요 상세 정보
     */
    ResponseEntity<CommonResponse> upsertReply(Long replyIdx);

    /**
     * 좋아요 생성
     * @param boardIdx Long
     * @return ResponseEntity<CommonResponse> 좋아요 상세 정보
     */
    ResponseEntity<CommonResponse> insertBoard(Long boardIdx);

    /**
     * 좋아요 생성
     * @param replyIdx Long
     * @return ResponseEntity<CommonResponse> 좋아요 상세 정보
     */
    ResponseEntity<CommonResponse> insertReply(Long replyIdx);

    /**
     * 좋아요 업데이트
     * @param like Like
     * @return ResponseEntity<CommonResponse> 좋아요 상세 정보
     */
    ResponseEntity<CommonResponse> update(Like like);

    /**
     * 좋아요 조회
     * @param board Board
     * @return ResponseEntity<CommonResponse> 좋아요 상세 정보
     */
    List<Like> findByBoard(Board board);

    /**
     * 좋아요 조회
     * @param reply Reply
     * @return ResponseEntity<CommonResponse> 좋아요 상세 정보
     */
    List<Like>  findByReply(Reply reply);

}
