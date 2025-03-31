package com.spinner.www.board.service;

import com.spinner.www.board.constants.CommonBoardCode;
import com.spinner.www.common.entity.CommonCode;
import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.common.service.CommonCodeService;
import com.spinner.www.constants.CommonResultCode;
import com.spinner.www.file.service.FileService;
import com.spinner.www.member.dto.SessionInfo;
import com.spinner.www.member.entity.Member;
import com.spinner.www.member.service.MemberService;
import com.spinner.www.board.entity.Board;
import com.spinner.www.board.io.BoardCreateRequest;
import com.spinner.www.board.io.BoardListResponse;
import com.spinner.www.board.io.BoardResponse;
import com.spinner.www.board.io.BoardUpdateRequest;
import com.spinner.www.board.repository.BoardQueryRepo;
import com.spinner.www.board.repository.BoardRepo;
import com.spinner.www.reply.io.ReplyResponse;
import com.spinner.www.util.ResponseVOUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

    private final SessionInfo sessionInfo;
    private final BoardRepo boardRepo;
    private final BoardQueryRepo boardQueryRepo;
    private final MemberService memberService;
    private final FileService fileService;
    private final CommonCodeService commonCodeService;

    /**
     * 게시글 생성
     *
     * @param boardRequest BoardCreateRequestIO 게시글 요청 데이터
     * @return ResponseEntity<CommonResponse> 게시글 상세 정보
     */
    @Override
    public ResponseEntity<CommonResponse> insert(String boardType, BoardCreateRequest boardRequest, List<MultipartFile> files) {
        Long memberIdx = sessionInfo.getMemberIdx();
        Long codeIdx = CommonBoardCode.getCode(boardType);
        String updateSrcContent;

        if (Objects.isNull(memberIdx))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);

        Member member = memberService.getMember(memberIdx);
        if (Objects.isNull(member))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);

        try {

            Map<String, String> fileMap = uploadBoardFiles(files);
            updateSrcContent = updateMediaSrc(fileMap, boardRequest.getContent());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        CommonCode commonCode = commonCodeService.getComonCode(codeIdx);
        Board board = Board.builder()
                .hitCount(0L)
                .commonCode(commonCode)
                .member(member)
                .boardTitle(boardRequest.getTitle())
                .boardContent(org.springframework.web.util.HtmlUtils.htmlEscape(updateSrcContent))
                .build();

        boardRepo.save(board);
        BoardResponse response = BoardResponse.builder()
                .nickname(member.getMemberNickname())
                .idx(board.getBoardIdx())
                .title(board.getBoardTitle())
                .content(org.springframework.web.util.HtmlUtils.htmlUnescape(board.getBoardContent()))
                .hitCount(board.getHitCount())
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
     * 게시글 uuid로 삭제되지 않은 게시글 조회
     *
     * @param codeIdx  Long 게시판 타입
     * @param boardIdx Long 게시글 idx
     * @return ResponseEntity<CommonResponse> 게시글 상세 정보
     */
    @Override
    public Board findByBoardIdx(Long codeIdx, Long boardIdx) {
        int isNotRemove = 0;
        CommonCode commoncode = commonCodeService.getComonCode(codeIdx);
        return boardRepo.findByCommonCodeAndBoardIdxAndBoardIsRemoved(commoncode, boardIdx, isNotRemove).orElse(null);
    }

    /**
     * 게시글 조회
     *
     * @param boardType String 게시판 타입
     * @param boardIdx  Long 게시글 idx
     * @return ResponseEntity<CommonResponse> 게시글 상세 정보
     */
    @Override
    @Transactional
    public ResponseEntity<CommonResponse> findByBoardInfo(String boardType, Long boardIdx) {
        Long codeIdx = CommonBoardCode.getCode(boardType);
        Board board = findByBoardIdx(codeIdx, boardIdx);

        if (board == null)
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.DATA_NOT_FOUND), HttpStatus.NOT_FOUND);

        board.increaseHitCount();
        BoardResponse response = buildBoardResponse(board);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.OK);
    }


    /**
     * 게시글 목록 조회
     *
     * @param boardType     String 게시판 타입
     * @param idx Long 조회 시작 idx
     * @param size int 조회할 목록 갯수
     * @param keyword String 조회할 키워드
     * @return ResponseEntity<CommonResponse> 게시글 목록
     */
    @Override
    public ResponseEntity<CommonResponse> getSliceOfBoard(String boardType, Long idx, int size, String keyword) {
        Long memberIdx = sessionInfo.getMemberIdx();

        // HACK 로그인 상태가 아닐 시 -1을 멤버 아이디로 줌
        if (Objects.isNull(memberIdx))
            memberIdx = -1L;
        Long codeIdx = CommonBoardCode.getCode(boardType);
        if(Objects.isNull(codeIdx))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.DATA_NOT_FOUND), HttpStatus.NOT_FOUND);

        List<BoardListResponse> list = this.boardQueryRepo.getSliceOfBoard(codeIdx,
                                                                            idx,
                                                                            size,
                                                                            keyword,
                                                                            memberIdx);

        if(!list.isEmpty())
            list.forEach(result -> result.setContent(org.springframework.web.util.HtmlUtils.htmlUnescape(result.getContent())));

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(list), HttpStatus.OK);
    }


    /**
     * 북마크한 게시글 목록 조회
     * @param idx Long 조회 시작 idx
     * @param size int 조회할 목록 갯수
     * @return ResponseEntity<CommonResponse> 게시글 목록
     */
    @Override
    public ResponseEntity<CommonResponse> getSliceOfBookmarkedBoard(Long idx, int size) {

        Long memberIdx = sessionInfo.getMemberIdx();
        if (Objects.isNull(memberIdx))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);

        Member member = memberService.getMember(memberIdx);
        if (Objects.isNull(member))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);

        List<BoardListResponse> list = this.boardQueryRepo.getSliceOfBookmarkedBoard(idx,
                size,
                memberIdx);

        if(!list.isEmpty())
            list.forEach(result -> result.setContent(org.springframework.web.util.HtmlUtils.htmlUnescape(result.getContent())));

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(list), HttpStatus.OK);
    }


    /**
     * 내가 좋아요를 누른 게시글 목록 조회
     * @param idx Long 조회 시작 idx
     * @param size int 조회할 목록 갯수
     * @return ResponseEntity<CommonResponse> 게시글 목록
     */
    @Override
    public ResponseEntity<CommonResponse> getSliceOfLikedBoard(Long idx, int size) {
        Long memberIdx = sessionInfo.getMemberIdx();
        if (Objects.isNull(memberIdx))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);

        Member member = memberService.getMember(memberIdx);
        if (Objects.isNull(member))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);

        List<BoardListResponse> list = this.boardQueryRepo.getSliceOfLikedBoard(idx,
                                                                                size,
                                                                                memberIdx);

        if(!list.isEmpty())
            list.forEach(result -> result.setContent(org.springframework.web.util.HtmlUtils.htmlUnescape(result.getContent())));

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(list), HttpStatus.OK);
    }


    /**
     * 내가 작성한 게시글 목록 조회
     * @param idx Long 조회 시작 idx
     * @param size int 조회할 목록 갯수
     * @return ResponseEntity<CommonResponse> 게시글 목록
     */
    @Override
    public ResponseEntity<CommonResponse> getSliceOfMemberBoard(Long idx, int size) {

        Long memberIdx = sessionInfo.getMemberIdx();
        if (Objects.isNull(memberIdx))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);

        Member member = memberService.getMember(memberIdx);
        if (Objects.isNull(member))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);

        List<BoardListResponse> list = this.boardQueryRepo.getSliceOfMemberBoard(idx,
                size,
                memberIdx);

        if(!list.isEmpty())
            list.forEach(result -> result.setContent(org.springframework.web.util.HtmlUtils.htmlUnescape(result.getContent())));

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(list), HttpStatus.OK);
    }

    /**
     * 인기글 게시글 목록 조회
     * @param boardType String 게시판 타입
     * @param idx Long 조회 시작 idx
     * @param size int 조회할 목록 갯수
     * @return ResponseEntity<CommonResponse> 게시글 목록
     */
    public ResponseEntity<CommonResponse> getSliceOfHotBoard(String boardType, Long idx, int size) {
        Long memberIdx = sessionInfo.getMemberIdx();
        if (Objects.isNull(memberIdx))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);

        Member member = memberService.getMember(memberIdx);
        if (Objects.isNull(member))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);

        Long codeIdx = CommonBoardCode.getCode(boardType);
        if(Objects.isNull(codeIdx))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.DATA_NOT_FOUND), HttpStatus.NOT_FOUND);

        List<BoardListResponse> list = this.boardQueryRepo.getSliceOfHotBoard(codeIdx,
                                                                                idx,
                                                                                size,
                                                                                memberIdx);

        if(!list.isEmpty())
            list.forEach(result -> result.setContent(org.springframework.web.util.HtmlUtils.htmlUnescape(result.getContent())));

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(list), HttpStatus.OK);
    }


    /**
     * 게시글 수정
     *
     * @param boardType    String 게시판 타입
     * @param boardIdx     Long 게시글 idx
     * @param boardRequest BoardUpdateRequestIO 게시글 수정 데이터
     * @return ResponseEntity<CommonResponse> 게시글 상세 정보
     */
    @Override
    @Transactional
    public ResponseEntity<CommonResponse> update(String boardType, Long boardIdx, BoardUpdateRequest boardRequest) {
        Long codeIdx = CommonBoardCode.getCode(boardType);

        Long memberIdx = sessionInfo.getMemberIdx();
        if (Objects.isNull(memberIdx))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);

        Member member = memberService.getMember(memberIdx);
        if (Objects.isNull(member))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);

        Board board = this.findByBoardIdx(codeIdx, boardIdx);
        if (Objects.isNull(board))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.DATA_NOT_FOUND), HttpStatus.NOT_FOUND);

        if (!Objects.equals(board.getMember(), member))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.FORBIDDEN), HttpStatus.FORBIDDEN);

        board.update(boardRequest.getTitle(), org.springframework.web.util.HtmlUtils.htmlEscape(boardRequest.getContent()));
        BoardResponse response = buildBoardResponse(board);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.OK);

    }

    /**
     * 게시글 삭제
     *
     * @param boardType String 게시판 타입
     * @param boardIdx  Long 게시글 idx
     * @return ResponseEntity<CommonResponse> 삭제 응답 결과
     */
    @Override
    @Transactional
    public ResponseEntity<CommonResponse> delete(String boardType, Long boardIdx) {
        Long codeIdx = CommonBoardCode.getCode(boardType);
        Long memberIdx = sessionInfo.getMemberIdx();
        if (Objects.isNull(memberIdx))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);

        Member member = memberService.getMember(memberIdx);
        if (Objects.isNull(member))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);

        Board board = this.findByBoardIdx(codeIdx, boardIdx);
        if (Objects.isNull(board))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.DATA_NOT_FOUND), HttpStatus.NOT_FOUND);

        if (!Objects.equals(board.getMember(), member))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.FORBIDDEN), HttpStatus.FORBIDDEN);

        board.delete();

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(), HttpStatus.OK);
    }

    /**
     * BoardResponse 빌더 함수(너무 길어서 별도로 작성)
     *
     * @param board Board
     * @return BoardResponse
     */
    public BoardResponse buildBoardResponse(Board board) {
        Long memberIdx = sessionInfo.getMemberIdx();

        List<ReplyResponse> replyResponses = board.getReplies().stream()
                .filter(reply -> reply.getReplyIsRemoved() == 0 || !reply.getChildReplies().isEmpty())
                .map(reply -> ReplyResponse.builder()
                        .idx(reply.getReplyIdx())
                        .nickname(reply.getMember().getMemberNickname())
                        .content(reply.getReplyIsRemoved() == 0 ? reply.getReplyContent() : "삭제된 댓글입니다.")
                        .likeCount(reply.getLikes().stream().filter(like -> like.getLikeIsLiked() == 1).count())
                        .isLiked(reply.getLikes().stream().anyMatch(like -> like.getMember().getMemberIdx().equals(memberIdx) && like.getLikeIsLiked() == 1))
                        .childReplies(reply.getChildReplies().stream()
                                .filter(childReply -> childReply.getReplyIsRemoved() == 0)
                                .map(childReply -> ReplyResponse.builder()
                                        .idx(childReply.getReplyIdx())
                                        .nickname(childReply.getMember().getMemberNickname())
                                        .content(childReply.getReplyContent())
                                        .likeCount(childReply.getLikes().stream().filter(like -> like.getLikeIsLiked() == 1).count())
                                        .isLiked(childReply.getLikes().stream().anyMatch(like -> like.getMember().getMemberIdx().equals(memberIdx) && like.getLikeIsLiked() == 1))
                                        .build())
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());

        Long likeCount = board.getLikes().stream()
                .filter(like -> like.getLikeIsLiked() == 1).count();

        boolean isLiked = board.getLikes().stream()
                .anyMatch(like -> like.getMember().getMemberIdx().equals(memberIdx) && like.getLikeIsLiked() == 1);
        boolean isBookmarked = board.getBookmarks().stream()
                .anyMatch(bookmark -> bookmark.getMember().getMemberIdx().equals(memberIdx) && bookmark.getIsBookmarked() == 1);

        return BoardResponse.builder()
                .idx(board.getBoardIdx())
                .boardName(board.getCommonCode().getCodeName())
                .nickname(board.getMember().getMemberNickname())
                .title(board.getBoardTitle())
                .content(org.springframework.web.util.HtmlUtils.htmlUnescape(board.getBoardContent()))
                .replies(replyResponses)
                .likeCount(likeCount)
                .liked(isLiked)
                .hitCount(board.getHitCount())
                .bookmarked(isBookmarked)
                .build();
    }

    /**
     * 파일 서버 업로드
     *
     * @param files MultipartFile
     * @return ResponseEntity<CommonResponse>
     */
    @Override
    public Map<String, String> uploadBoardFiles(List<MultipartFile> files) throws IOException {
        if(files == null) return null;
        return fileService.uploadBoardFiles(files);
    }

    public String updateMediaSrc(Map<String, String> fileUrlMap, String htmlText) {
        if (fileUrlMap == null || fileUrlMap.isEmpty() || htmlText == null) return htmlText;
        String updateHtmlText = htmlText;

        String regex = "<(img|video)[^>]+src=[\"']([^\"']+)[\"']";
        Matcher matcher= Pattern.compile(regex, Pattern.CASE_INSENSITIVE).matcher(htmlText);

        while (matcher.find()) {
            if(fileUrlMap.containsKey(matcher.group(2)))
                updateHtmlText = updateHtmlText.replace(matcher.group(2), fileUrlMap.get(matcher.group(2)));
        }

        return updateHtmlText;
    }
}
