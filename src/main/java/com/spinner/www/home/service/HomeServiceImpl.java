package com.spinner.www.home.service;

import com.spinner.www.board.service.BoardService;
import com.spinner.www.common.io.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService {

    private final BoardService boardService;

    /**
     * 인기글 게시글 목록 조회
     *
     * @param boardType String 게시판 타입
     * @param idx       Long 조회 시작 idx
     * @param size      int 조회할 목록 갯수
     * @return ResponseEntity<CommonResponse> 게시글 목록
     */
    @Override
    public ResponseEntity<CommonResponse> getSliceOfHotBoard(String boardType, Long idx, int size) {
        return boardService.getSliceOfHotBoard(boardType, idx, size);
    }
}
