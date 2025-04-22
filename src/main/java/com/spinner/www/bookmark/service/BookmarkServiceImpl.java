package com.spinner.www.bookmark.service;

import com.spinner.www.board.entity.Board;
import com.spinner.www.board.service.BoardService;
import com.spinner.www.bookmark.entity.Bookmark;
import com.spinner.www.bookmark.io.BookmarkBoardResponse;
import com.spinner.www.bookmark.mapper.BookmarkMapper;
import com.spinner.www.bookmark.repository.BookmarkRepo;
import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.constants.CommonResultCode;
import com.spinner.www.member.dto.SessionInfo;
import com.spinner.www.member.entity.Member;
import com.spinner.www.member.service.MemberService;
import com.spinner.www.util.ResponseVOUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class BookmarkServiceImpl implements BookmarkService {

    private final SessionInfo sessionInfo;
    private final BookmarkRepo bookmarkRepo;
    private final MemberService memberService;
    private final BoardService boardService;
    private final BookmarkMapper bookmarkMapper;

    /**
     * 북마크 생성 또는 업데이트
     * @param boardIdx Long
     * @return ResponseEntity<CommonResponse> 북마크 상세 정보
     */
    @Override
    public ResponseEntity<CommonResponse> upsertBoard(Long boardIdx) {
        Long memberIdx = sessionInfo.getMemberIdx();
        if (Objects.isNull(memberIdx))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);

        Member member = memberService.getMember(memberIdx);
        if (Objects.isNull(member))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);

        Board board = boardService.getBoardOrThrow(boardIdx);

        if (Objects.isNull(board))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.DATA_NOT_FOUND), HttpStatus.NOT_FOUND);

        List<Bookmark> bookmarks = this.findByBoard(board);

        Bookmark bookmark = bookmarks.stream().filter(val -> val.getMember().getMemberIdx().equals(memberIdx)).findFirst().orElse(null);

        if(Objects.isNull(bookmark)) {
            return this.insertBoard(boardIdx);
        }

        return this.update(bookmark);
    }

    /**
     * 북마크 생성
     * @param boardIdx Long
     * @return ResponseEntity<CommonResponse> 북마크 상세 정보
     */
    @Override
    public ResponseEntity<CommonResponse> insertBoard(Long boardIdx) {
        Long memberIdx = sessionInfo.getMemberIdx();
        if (Objects.isNull(memberIdx))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);

        Member member = memberService.getMember(memberIdx);
        if (Objects.isNull(member))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);

        Board board = boardService.getBoardOrThrow(boardIdx);
        Bookmark bookmark = Bookmark.builder()
                .member(member)
                .board(board)
                .isBookmarked(1)
                .build();

        bookmarkRepo.save(bookmark);

        BookmarkBoardResponse response = bookmarkMapper.bookmarkToBookmarkBoardResponse(bookmark);

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.CREATED);
    }

    /**
     * 북마크 수정
     * @param bookmark Bookmark
     * @return ResponseEntity<CommonResponse> 북마크 상세 정보
     */
    @Override
    public ResponseEntity<CommonResponse> update(Bookmark bookmark) {
        Long memberIdx = sessionInfo.getMemberIdx();
        if (Objects.isNull(memberIdx))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);

        Member member = memberService.getMember(memberIdx);
        if (Objects.isNull(member))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);

        bookmark.update();
        bookmarkRepo.save(bookmark);

        if(Objects.isNull(bookmark.getBoard())){
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.DATA_NOT_FOUND), HttpStatus.NOT_FOUND);
        }else {

            BookmarkBoardResponse response = bookmarkMapper.bookmarkToBookmarkBoardResponse(bookmark);

            return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.OK);
        }
    }

    /**
     * 게시판 기준 북마크 목록 조회
     * @param board Board
     * @return List<Bookmark> 북마크 목록
     */
    @Override
    public List<Bookmark> findByBoard(Board board) {
        return bookmarkRepo.findByBoard(board);
    }
}
