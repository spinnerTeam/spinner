package com.spinner.www.study.service;

import com.spinner.www.board.io.BoardCreateRequest;
import com.spinner.www.board.io.BoardUpdateRequest;
import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.reply.io.ReplyCreateRequest;
import com.spinner.www.reply.io.ReplyUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StudyBoardService {
    /**
     * 스터디 게시글 생성
     * @param boardType String 게시판 타입
     * @param studyIdx Long
     * @param boardRequest BoardCreateRequestIO 게시글 요청 데이터
     * @param files List<MultipartFile> 멀티파일 리스트
     * @return ResponseEntity<CommonResponse> 게시글 상세 정보
     */
    ResponseEntity<CommonResponse> insert(String boardType, Long studyIdx, BoardCreateRequest boardRequest, List<MultipartFile> files);

    /**
     * 스터디 게시글 조회
     * @param boardType String 게시판 타입
     * @param boardIdx Long 게시글 idx
     * @param studyIdx Long 스터디 idx
     * @return ResponseEntity<CommonResponse> 게시글 상세 정보
     */
    ResponseEntity<CommonResponse> findByBoardInfo(String boardType, Long studyIdx, Long boardIdx);

    /**
     * 게시글 목록 조회
     * @param boardType String 게시판 타입
     * @param idx Long 조회 시작 idx
     * @param size int 조회할 목록 갯수
     * @param keyword String 조회할 키워드
     * @param studyIdx Long 조회할 스터디 idx
     * @return ResponseEntity<CommonResponse> 게시글 목록
     */
    ResponseEntity<CommonResponse> getSliceOfBoard(String boardType, Long idx, int size, String keyword, Long studyIdx);

    /**
     * 게시글 수정
     * @param boardType String 게시판 타입
     * @param boardIdx Long 게시글 idx
     * @param studyIdx Long 스터디 idx
     * @param boardRequest BoardUpdateRequestIO 게시글 수정 데이터
     * @return ResponseEntity<CommonResponse> 게시글 상세 정보
     */
    ResponseEntity<CommonResponse> update(String boardType, Long boardIdx, Long studyIdx, BoardUpdateRequest boardRequest);

    /**
     * 게시글 삭제
     * @param boardType String 게시판 타입
     * @param boardIdx Long 게시글 idx
     * @param studyIdx Long 스터디 idx
     * @return ResponseEntity<CommonResponse> 삭제 응답 결과
     */
    ResponseEntity<CommonResponse> delete(String boardType, Long boardIdx, Long studyIdx);

    /**
     * 스터디 댓글 생성
     * @param boardType String
     * @param studyIdx Long 스터디 idx
     * @param replyRequest ReplyCreateRequestIO 댓글 요청 데이터
     * @return ResponseEntity<CommonResponse> 댓글 상세 정보
     */
    ResponseEntity<CommonResponse> insertReply(String boardType, Long studyIdx, ReplyCreateRequest replyRequest);

    /**
     * 댓글 수정
     * @param boardType String
     * @param replyIdx Long 댓글 idx
     * @param studyIdx Long 스터디 idx
     * @param replyRequest ReplyUpdateRequestIO 댓글 수정 데이터
     * @return ResponseEntity<CommonResponse> 댓글 상세 정보
     */
    ResponseEntity<CommonResponse> updateReply(String boardType, Long replyIdx, Long studyIdx, ReplyUpdateRequest replyRequest);

    /**
     * 댓글 삭제
     * @param boardType String
     * @param replyIdx Long 댓글 idx
     * @param studyIdx Long 스터디 idx
     * @return ResponseEntity<CommonResponse> 삭제 응답 결과
     */
    ResponseEntity<CommonResponse> deleteReply(String boardType, Long replyIdx, Long studyIdx);

    /**
     * 좋아요 생성
     * @param studyIdx Long
     * @param boardIdx Long
     * @return ResponseEntity<CommonResponse> 좋아요 상세 정보
     */
    ResponseEntity<CommonResponse> upsertLikeBoard(Long studyIdx, Long boardIdx);

    /**
     * 좋아요 생성
     * @param studyIdx Long
     * @param replyIdx Long
     * @return ResponseEntity<CommonResponse> 좋아요 상세 정보
     */
    ResponseEntity<CommonResponse> upsertLikeReply(Long studyIdx, Long replyIdx);

    /**
     * 북마크 생성
     * @param studyIdx Long
     * @param boardIdx Long
     * @return ResponseEntity<CommonResponse> 북마크 상세 정보
     */
    ResponseEntity<CommonResponse> upsertBookmarkBoard(Long studyIdx, Long boardIdx);
}

