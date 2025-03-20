package com.spinner.www.myprofile.service;

import com.spinner.www.board.service.BoardService;
import com.spinner.www.common.io.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MyProfileServiceImpl implements MyProfileService {
    private final BoardService boardService;

    /**
     * 내가 작성한 게시글 목록 조회
     * @param idx Long 조회 시작 idx
     * @param size int 조회할 목록 갯수
     * @return ResponseEntity<CommonResponse> 게시글 목록
     */
    @Override
    public ResponseEntity<CommonResponse> getSliceOfMemberBoard(Long idx, int size) {
        return boardService.getSliceOfMemberBoard(idx,size);
    }

    /**
     * 내가 좋아요를 누른 게시글 목록 조회
     * @param idx Long 조회 시작 idx
     * @param size int 조회할 목록 갯수
     * @return ResponseEntity<CommonResponse> 게시글 목록
     */
    @Override
    public ResponseEntity<CommonResponse> getSliceOfLikedBoard(Long idx, int size) {
        return boardService.getSliceOfLikedBoard(idx,size);
    }

    /**
     * 북마크한 게시글 목록 조회
     * @param idx Long 조회 시작 idx
     * @param size int 조회할 목록 갯수
     * @return ResponseEntity<CommonResponse> 게시글 목록
     */
    @Override
    public ResponseEntity<CommonResponse> getSliceOfBookmarkedBoard(Long idx, int size) {
        return boardService.getSliceOfBookmarkedBoard(idx,size);
    }
}
