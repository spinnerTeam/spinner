package com.spinner.www.board.service;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.common.io.SearchParamRequest;
import com.spinner.www.board.entity.Board;
import com.spinner.www.board.io.BoardCreateRequest;
import com.spinner.www.board.io.BoardUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface BoardService {

    /**
     * 게시글 생성
     * @param boardType String 게시판 타입
     * @param boardRequest BoardCreateRequestIO 게시글 요청 데이터
     * @return ResponseEntity<CommonResponse> 게시글 상세 정보
     */
    ResponseEntity<CommonResponse> insert(String boardType, BoardCreateRequest boardRequest, List<MultipartFile> files);

    /**
     * 게시글 uuid로 삭제되지 않은 게시글
     * @param codeIdx Long 게시판 타입
     * @param boardIdx Long 게시글 idx
     * @return ResponseEntity<CommonResponse> 게시글 상세 정보
     */
    Board findByBoardIdx(Long codeIdx, Long boardIdx);

    /**
     * 게시글 조회
     * @param boardType String 게시판 타입
     * @param boardIdx Long 게시글 idx
     * @return ResponseEntity<CommonResponse> 게시글 상세 정보
     */
    ResponseEntity<CommonResponse> findByBoardInfo(String boardType, Long boardIdx);

    /**
     * 게시글 목록 조회
     * @param boardType String 게시판 타입
     * @param idx Long 조회 시작 idx
     * @param size int 조회할 목록 갯수
     * @param keyword String 조회할 키워드
     * @return ResponseEntity<CommonResponse> 게시글 목록
     */
    ResponseEntity<CommonResponse> getSliceOfBoard(String boardType, Long idx, int size, String keyword);

    /**
     * 게시글 수정
     * @param boardType String 게시판 타입
     * @param boardIdx Long 게시글 idx
     * @param boardRequest BoardUpdateRequestIO 게시글 수정 데이터
     * @return ResponseEntity<CommonResponse> 게시글 상세 정보
     */
    ResponseEntity<CommonResponse> update(String boardType, Long boardIdx, BoardUpdateRequest boardRequest);

    /**
     * 게시글 삭제
     * @param boardType String 게시판 타입
     * @param boardIdx Long 게시글 idx
     * @return ResponseEntity<CommonResponse> 삭제 응답 결과
     */
    ResponseEntity<CommonResponse> delete(String boardType, Long boardIdx);

    /**
     * 파일 서버 업로드
     * @param files List<MultipartFile>
     * @return ResponseEntity<CommonResponse>
     */
    Map<String, String> uploadBoardFiles(List<MultipartFile> files) throws IOException;

    /**
     * 좋아요 생성 또는 업데이트
     * @param boardType String 게시판 타입
     * @param boardIdx Long 게시글 idx
     * @return ResponseEntity<CommonResponse> 삭제 응답 결과
     */
    ResponseEntity<CommonResponse> upsertLike(String boardType, Long boardIdx);
}
