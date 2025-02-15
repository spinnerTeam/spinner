package com.spinner.www.board.service;

import com.spinner.www.board.constants.CommonBoardCode;
import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.common.io.SearchParamRequest;
import com.spinner.www.constants.CommonResultCode;
import com.spinner.www.file.service.FileService;
import com.spinner.www.like.service.LikeService;
import com.spinner.www.member.dto.SessionInfo;
import com.spinner.www.member.entity.Member;
import com.spinner.www.member.service.MemberService;
import com.spinner.www.board.entity.Board;
import com.spinner.www.board.io.BoardCreateRequest;
import com.spinner.www.board.io.BoardListResponse;
import com.spinner.www.board.io.BoardResponse;
import com.spinner.www.board.io.BoardUpdateRequest;
import com.spinner.www.board.mapper.BoardMapper;
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
    private final LikeService likeService;
//    private final BoardMapper boardMapper;

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

        try {
            Map<String, String> fileMap = uploadBoardFiles(files);
            updateSrcContent = updateMediaSrc(fileMap, boardRequest.getContent());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Member member = memberService.getMember(memberIdx);

        Board board = Board.builder()
                .codeIdx(codeIdx)
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
                .createdDate(board.getCreatedDate())
                .modifiedDate(board.getModifiedDate())
                .build();

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.CREATED);
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
        return boardRepo.findByCodeIdxAndBoardIdxAndBoardIsRemoved(codeIdx, boardIdx, isNotRemove).orElse(null);
    }

    /**
     * 게시글 조회
     *
     * @param boardType String 게시판 타입
     * @param boardIdx  Long 게시글 idx
     * @return ResponseEntity<CommonResponse> 게시글 상세 정보
     */
    @Override
    public ResponseEntity<CommonResponse> findByBoardInfo(String boardType, Long boardIdx) {
        Long codeIdx = CommonBoardCode.getCode(boardType);
        Board board = findByBoardIdx(codeIdx, boardIdx);
        BoardResponse response = buildBoardResponse(board);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.OK);
    }

    /**
     * 게시글 목록 조회
     *
     * @param boardType     String 게시판 타입
     * @param searchRequest SearchParamRequest 검색 조건
     * @return ResponseEntity<CommonResponse> 게시글 목록
     */
    @Override
    public ResponseEntity<CommonResponse> getSliceOfBoard(String boardType, SearchParamRequest searchRequest) {
        Long codeIdx = CommonBoardCode.getCode(boardType);
        List<BoardListResponse> list = this.boardQueryRepo.getSliceOfBoard(codeIdx,
                searchRequest.getIdx(),
                searchRequest.getSize(),
                searchRequest.getKeyword());
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
        Member member = memberService.getMember(memberIdx);
        Board board = this.findByBoardIdx(codeIdx, boardIdx);

        if (Objects.isNull(memberIdx))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);

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
        Member member = memberService.getMember(memberIdx);
        Board board = this.findByBoardIdx(codeIdx, boardIdx);

        if (Objects.isNull(memberIdx))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);

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

        return BoardResponse.builder()
                .idx(board.getBoardIdx())
                .nickname(board.getMember().getMemberNickname())
                .title(board.getBoardTitle())
                .content(org.springframework.web.util.HtmlUtils.htmlUnescape(board.getBoardContent()))
                .replies(replyResponses)
                .likeCount(likeCount)
                .isLiked(isLiked)
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

    /**
     * 좋아요 생성 또는 업데이트
     *
     * @param boardType String 게시판 타입
     * @param boardIdx  Long 게시글 idx
     * @return ResponseEntity<CommonResponse> 삭제 응답 결과
     */
    @Override
    public ResponseEntity<CommonResponse> upsertLike(String boardType, Long boardIdx) {
        Long codeIdx = CommonBoardCode.getCode(boardType);
        Long memberIdx = sessionInfo.getMemberIdx();
        Member member = memberService.getMember(memberIdx);
        Board board = this.findByBoardIdx(codeIdx, boardIdx);

        if (Objects.isNull(memberIdx))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);

        if (Objects.isNull(board))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.DATA_NOT_FOUND), HttpStatus.NOT_FOUND);

        if (!Objects.equals(board.getMember(), member))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.FORBIDDEN), HttpStatus.FORBIDDEN);

        return likeService.upsertBoard(boardIdx);
    }
}
