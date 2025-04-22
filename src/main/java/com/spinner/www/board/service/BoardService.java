package com.spinner.www.board.service;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.board.entity.Board;
import com.spinner.www.board.io.BoardCreateRequest;
import com.spinner.www.board.io.BoardUpdateRequest;
import com.spinner.www.file.entity.Files;
import com.spinner.www.member.entity.Member;
import com.spinner.www.study.entity.Study;
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
     * @param files List<MultipartFile> 멀티파일 리스트
     * @return ResponseEntity<CommonResponse> 게시글 상세 정보
     */
    ResponseEntity<CommonResponse> insert(String boardType, BoardCreateRequest boardRequest, List<MultipartFile> files);

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
     * 게시글 조회
     * @param boardIdx Long 게시글 idx
     * @return ResponseEntity<CommonResponse> 게시글 상세 정보
     */
    Board getBoardOrThrow(Long boardIdx);

    /**
     * 게시글 조회
     * @param boardIdx Long 게시글 idx
     * @param codeIdx Long 게시판 타입 idx
     * @return ResponseEntity<CommonResponse> 게시글 상세 정보
     */
    Board getBoardOrThrow(Long boardIdx, Long codeIdx);

    /**
     * 스터디 게시글 조회
     * @param boardIdx Long 게시글 idx
     * @param codeIdx Long 게시판 타입 idx
     * @param study Study 스터디 객체
     * @return ResponseEntity<CommonResponse> 게시글 상세 정보
     */
    Board getBoardOrThrow(Long boardIdx, Long codeIdx, Study study);

    /**
     * 게시글 조회
     * @param boardType String 게시판 타입
     * @param boardIdx Long 게시글 idx
     * @return ResponseEntity<CommonResponse> 게시글 상세 정보
     */
    ResponseEntity<CommonResponse> findByBoardInfo(String boardType, Long boardIdx);

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
     * @return ResponseEntity<CommonResponse> 게시글 목록
     */
    ResponseEntity<CommonResponse> getSliceOfBoard(String boardType, Long idx, int size, String keyword);

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
     * 내가 작성한 게시글 목록 조회
     * @param idx Long 조회 시작 idx
     * @param size int 조회할 목록 갯수
     * @return ResponseEntity<CommonResponse> 게시글 목록
     */
    ResponseEntity<CommonResponse> getSliceOfMemberBoard(Long idx, int size);

    /**
     * 내가 작성한 게시글 목록 조회
     * @param idx Long 조회 시작 idx
     * @param size int 조회할 목록 갯수
     * @param studyIdx Long 조회할 스터디 idx
     * @return ResponseEntity<CommonResponse> 게시글 목록
     */
    ResponseEntity<CommonResponse> getSliceOfMemberBoard(Long idx, int size, Long studyIdx);

    /**
     * 내가 좋아요를 누른 게시글 목록 조회
     * @param idx Long 조회 시작 idx
     * @param size int 조회할 목록 갯수
     * @return ResponseEntity<CommonResponse> 게시글 목록
     */
    ResponseEntity<CommonResponse> getSliceOfLikedBoard(Long idx, int size);

    /**
     * 내가 좋아요를 누른 게시글 목록 조회
     * @param idx Long 조회 시작 idx
     * @param size int 조회할 목록 갯수
     * @param studyIdx Long 조회할 스터디 idx
     * @return ResponseEntity<CommonResponse> 게시글 목록
     */
    ResponseEntity<CommonResponse> getSliceOfLikedBoard(Long idx, int size, Long studyIdx);

    /**
     * 북마크한 게시글 목록 조회
     * @param idx Long 조회 시작 idx
     * @param size int 조회할 목록 갯수
     * @return ResponseEntity<CommonResponse> 게시글 목록
     */
    ResponseEntity<CommonResponse> getSliceOfBookmarkedBoard(Long idx, int size);

    /**
     * 북마크한 게시글 목록 조회
     * @param idx Long 조회 시작 idx
     * @param size int 조회할 목록 갯수
     * @param studyIdx Long 조회할 스터디 idx
     * @return ResponseEntity<CommonResponse> 게시글 목록
     */
    ResponseEntity<CommonResponse> getSliceOfBookmarkedBoard(Long idx, int size, Long studyIdx);

    /**
     * 인기글 게시글 목록 조회
     * @param boardType String 게시판 타입
     * @param idx Long 조회 시작 idx
     * @param size int 조회할 목록 갯수
     * @return ResponseEntity<CommonResponse> 게시글 목록
     */
    ResponseEntity<CommonResponse> getSliceOfHotBoard(String boardType, Long idx, int size);

    /**
     * 인기글 게시글 목록 조회
     * @param boardType String 게시판 타입
     * @param idx Long 조회 시작 idx
     * @param size int 조회할 목록 갯수
     * @param studyIdx Long 조회할 스터디 idx
     * @return ResponseEntity<CommonResponse> 게시글 목록
     */
    ResponseEntity<CommonResponse> getSliceOfHotBoard(String boardType, Long idx, int size, Long studyIdx);

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
     * 게시글 삭제
     * @param boardType String 게시판 타입
     * @param boardIdx Long 게시글 idx
     * @param studyIdx Long 스터디 idx
     * @return ResponseEntity<CommonResponse> 삭제 응답 결과
     */
    ResponseEntity<CommonResponse> delete(String boardType, Long boardIdx, Long studyIdx);

    /**
     * 파일 서버 업로드
     * @param files List<MultipartFile>
     * @return ResponseEntity<CommonResponse>
     */
    Map<String, String> convertFilesNameAndUrl(List<Files> files) throws IOException;

    /**
     * 내가 작성한 게시글인지 확인
     * @param boardIdx 게시글 idx
     * @param member 멤버 객체
     * @return boolean
     */
    boolean isCreatedByMember(Long boardIdx, Member member);

    /**
     * 내가 작성한 스터디 게시글인지 확인
     * @param boardIdx 게시글 idx
     * @param member 멤버 객체
     * @param study 스터디 객체
     * @return boolean
     */
    boolean isCreatedByMember(Long boardIdx, Member member, Study study);
}
