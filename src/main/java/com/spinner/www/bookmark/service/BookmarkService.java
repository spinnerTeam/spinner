package com.spinner.www.bookmark.service;

import com.spinner.www.board.entity.Board;
import com.spinner.www.bookmark.entity.Bookmark;
import com.spinner.www.common.io.CommonResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookmarkService {

    /**
     * 북마크 생성
     * @param boardIdx Long
     * @return ResponseEntity<CommonResponse> 북마크 상세 정보
     */
    ResponseEntity<CommonResponse> upsertBoard(Long boardIdx);

    /**
     * 북마크 생성
     * @param boardIdx Long
     * @return ResponseEntity<CommonResponse> 북마크 상세 정보
     */
    ResponseEntity<CommonResponse> insertBoard(Long boardIdx);

    /**
     * 북마크 업데이트
     * @param bookmark Bookmark
     * @return ResponseEntity<CommonResponse> 북마크 상세 정보
     */
    ResponseEntity<CommonResponse> update(Bookmark bookmark);

    /**
     * 게시판 기준 북마크 목록 조회
     * @param board Board
     * @return List<Bookmark> 북마크 목록
     */
    List<Bookmark> findByBoard(Board board);
}
