package com.spinner.www.home.service;

import com.spinner.www.common.io.CommonResponse;
import org.springframework.http.ResponseEntity;

public interface HomeService {
    /**
     * 인기글 게시글 목록 조회
     * @param boardType String 게시판 타입
     * @param idx Long 조회 시작 idx
     * @param size int 조회할 목록 갯수
     * @return ResponseEntity<CommonResponse> 게시글 목록
     */
    ResponseEntity<CommonResponse> getSliceOfHotBoard(String boardType, Long idx, int size);
}
