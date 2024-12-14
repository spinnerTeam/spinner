package com.spinner.www.board.service;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.common.io.SearchParamRequest;
import com.spinner.www.constants.CommonResultCode;
import com.spinner.www.member.dto.SessionInfo;
import com.spinner.www.member.entity.Member;
import com.spinner.www.member.service.MemberService;
import com.spinner.www.board.dto.BoardGetDto;
import com.spinner.www.board.entity.Board;
import com.spinner.www.board.io.BoardCreateRequest;
import com.spinner.www.board.io.BoardListResponse;
import com.spinner.www.board.io.BoardResponse;
import com.spinner.www.board.io.BoardUpdateRequest;
import com.spinner.www.board.mapper.BoardMapper;
import com.spinner.www.board.repository.BoardQueryRepo;
import com.spinner.www.board.repository.BoardRepo;
import com.spinner.www.util.ResponseVOUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

    private final SessionInfo sessionInfo;
    private final BoardRepo boardRepo;
    private final BoardQueryRepo boardQueryRepo;
    private final MemberService memberService;
    private final BoardMapper boardMapper;
    /**
     * 게시글 생성
     * @param boardRequest BoardCreateRequestIO 게시글 요청 데이터
     * @return ResponseEntity<CommonResponse> 게시글 상세 정보
     */
    @Override
    public ResponseEntity<CommonResponse> insert(BoardCreateRequest boardRequest) {
        Long memberIdx = sessionInfo.getMemberIdx();

        if (Objects.isNull(memberIdx))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);

        Member member = memberService.getMember(memberIdx);

        Board board = Board.builder()
                .member(member)
                .boardTitle(boardRequest.getTitle())
                .boardContent(boardRequest.getContent())
                .build();

        boardRepo.save(board);
        BoardResponse response = BoardResponse.builder()
                .nickName(member.getMemberNickname())
                .idx(board.getBoardIdx())
                .title(board.getBoardTitle())
                .content(board.getBoardContent())
                .createdDate(board.getCreatedDate())
                .modifiedDate(board.getModifiedDate())
                .build();

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.CREATED);
    }

    /**
     * 게시글 uuid로 삭제되지 않은 게시글 조회
     * @param boardIdx Long 게시글 idx
     * @return ResponseEntity<CommonResponse> 게시글 상세 정보
     */
    @Override
    public Board findByBoardIdx(Long boardIdx) {
        int isNotRemove = 0;
        return boardRepo.findByBoardIdxAndBoardIsRemoved(boardIdx, isNotRemove).orElse(null);
    }

    /**
     * 게시글 조회
     * @param boardIdx Long 게시글 idx
     * @return ResponseEntity<CommonResponse> 게시글 상세 정보
     */
    @Override
    public ResponseEntity<CommonResponse> findByBoardInfo(Long boardIdx) {
        Board board= this.findByBoardIdx(boardIdx);

        if (Objects.isNull(board))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.DATA_NOT_FOUND), HttpStatus.NOT_FOUND);

        BoardGetDto dto = boardMapper.boardToBoardGetDto(board);
//        BoardResponse response = BoardResponse.builder()
//                                .nickName(board.getMember().getMemberNickname())
//                                .idx(board.getBoardIdx())
//                                .title(board.getBoardTitle())
//                                .content(board.getBoardContent())
//                                .createdDate(board.getCreatedDate())
//                                .modifiedDate(board.getModifiedDate())
//                                .build();

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(dto), HttpStatus.OK);
    }

    /**
     * 게시글 목록 조회
     * @param searchRequest SearchParamRequest 검색 조건
     * @return ResponseEntity<CommonResponse> 게시글 목록
     */
    @Override
    public ResponseEntity<CommonResponse> getSliceOfBoard(SearchParamRequest searchRequest) {
        List<BoardListResponse> list = this.boardQueryRepo.getSliceOfBoard(searchRequest.getIdx(),
                                                                        searchRequest.getSize(),
                                                                        searchRequest.getKeyword());
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(list), HttpStatus.OK);
    }


    /**
     * 게시글 수정
     * @param boardIdx Long 게시글 idx
     * @param boardRequest BoardUpdateRequestIO 게시글 수정 데이터
     * @return ResponseEntity<CommonResponse> 게시글 상세 정보
     */
    @Override
    @Transactional
    public ResponseEntity<CommonResponse> update(Long boardIdx, BoardUpdateRequest boardRequest) {
        Long memberIdx = sessionInfo.getMemberIdx();
        Member member = memberService.getMember(memberIdx);
        Board board= this.findByBoardIdx(boardIdx);

        if (Objects.isNull(memberIdx))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);

        if (Objects.isNull(board))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.DATA_NOT_FOUND), HttpStatus.NOT_FOUND);

        if (!Objects.equals(board.getMember(), member))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.FORBIDDEN), HttpStatus.FORBIDDEN);

        board.update(boardRequest.getTitle(), boardRequest.getContent());

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(board), HttpStatus.OK);
    }

    /**
     * 게시글 삭제
     * @param boardIdx Long 게시글 idx
     * @return ResponseEntity<CommonResponse> 삭제 응답 결과
     */
    @Override
    @Transactional
    public ResponseEntity<CommonResponse> delete(Long boardIdx) {
        Long memberIdx = sessionInfo.getMemberIdx();
        Member member = memberService.getMember(memberIdx);
        Board board= this.findByBoardIdx(boardIdx);

        if (Objects.isNull(memberIdx))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);

        if (Objects.isNull(board))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.DATA_NOT_FOUND), HttpStatus.NOT_FOUND);

        if (!Objects.equals(board.getMember(), member))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.FORBIDDEN), HttpStatus.FORBIDDEN);

        board.delete();

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(), HttpStatus.OK);
    }
}
