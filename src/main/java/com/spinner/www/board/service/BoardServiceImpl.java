package com.spinner.www.board.service;

import com.spinner.www.board.constants.CommonBoardCode;
import com.spinner.www.board.io.*;
import com.spinner.www.common.entity.CommonCode;
import com.spinner.www.common.exception.BoardNotFoundException;
import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.common.service.CommonCodeService;
import com.spinner.www.common.service.ServerInfo;
import com.spinner.www.constants.CommonResultCode;
import com.spinner.www.file.entity.Files;
import com.spinner.www.file.service.FileService;
import com.spinner.www.member.dto.SessionInfo;
import com.spinner.www.member.entity.Member;
import com.spinner.www.member.service.MemberService;
import com.spinner.www.board.entity.Board;
import com.spinner.www.board.repository.BoardQueryRepo;
import com.spinner.www.board.repository.BoardRepo;
import com.spinner.www.reply.io.ReplyResponse;
import com.spinner.www.study.entity.Study;
import com.spinner.www.study.service.StudyMemberService;
import com.spinner.www.study.service.StudyService;
import com.spinner.www.util.ResponseVOUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.*;
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
    private final StudyService studyService;
    private final StudyMemberService studyMemberService;

    @Autowired
    private ServerInfo serverInfo;

    /**
     * 게시글 생성
     * @param boardType String 게시판 타입
     * @param boardRequest BoardCreateRequestIO 게시글 요청 데이터
     * @param uploadFiles List<MultipartFile> 멀티파일 리스트
     * @return ResponseEntity<CommonResponse> 게시글 상세 정보
     */
    @Override
    public ResponseEntity<CommonResponse> insert(String boardType, BoardCreateRequest boardRequest, List<MultipartFile> uploadFiles) {
        return insert(boardType, null, boardRequest, uploadFiles);
    }
    /**
     * 스터디 게시글 생성
     * @param boardType String 게시판 타입
     * @param studyIdx Long
     * @param boardRequest BoardCreateRequestIO 게시글 요청 데이터
     * @param uploadFiles List<MultipartFile> 멀티파일 리스트
     * @return ResponseEntity<CommonResponse> 게시글 상세 정보
     */
    @Override
    public ResponseEntity<CommonResponse> insert(String boardType, Long studyIdx, BoardCreateRequest boardRequest,List<MultipartFile> uploadFiles) {
        Long memberIdx = sessionInfo.getMemberIdx();
        if(Objects.isNull(memberIdx))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
        Long codeIdx = CommonBoardCode.getCode(boardType);
        Study study = Objects.isNull(studyIdx) ? null : studyService.getStudyOrThrow(studyIdx);

        String updateSrcContent;

        Member member = memberService.getMember(memberIdx);

        if(!Objects.isNull(study)) {
            boolean isStudyMember = studyMemberService.existsByStudyAndMember(study, member);
            if(!isStudyMember) {
                return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
            }
        }

        List<Files> files;
        try {
            files = fileService.uploadBoardFiles(uploadFiles);
            Map<String, String> fileMap = convertFilesNameAndUrl(files);
            updateSrcContent = updateMediaSrc(fileMap, boardRequest.getContent());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        CommonCode commonCode = commonCodeService.getCommonCode(codeIdx);
        Board board = Board.builder()
                .hitCount(0L)
                .commonCode(commonCode)
                .member(member)
                .boardTitle(boardRequest.getTitle())
                .boardContent(org.springframework.web.util.HtmlUtils.htmlEscape(updateSrcContent))
                .files(files)
                .study(study)
                .build();

        boardRepo.save(board);
        BoardResponse response = buildBoardResponse(board);

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.CREATED);
    }

    /**
     * 게시글 uuid로 삭제되지 않은 게시글 조회
     * @param boardIdx Long 게시글 idx
     * @return ResponseEntity<CommonResponse> 게시글 상세 정보
     */
    @Override
    public Board getBoardOrThrow(Long boardIdx) {
        int isNotRemove = 0;
        return boardRepo.findByBoardIdxAndBoardIsRemoved(boardIdx, isNotRemove).orElseThrow(BoardNotFoundException::new);
    }

    /**
     * 게시글 uuid로 삭제되지 않은 게시글 조회
     * @param boardIdx Long 게시글 idx
     * @param codeIdx  Long 게시판 타입
     * @return ResponseEntity<CommonResponse> 게시글 상세 정보
     */
    @Override
    public Board getBoardOrThrow(Long boardIdx, Long codeIdx) {
        Board board = getBoardOrThrow(boardIdx);
        if(!Objects.equals(board.getCommonCode().getCodeIdx(),codeIdx)) {
            throw new BoardNotFoundException();
        }
        return board;
    }

    /**
     * 스터디 게시글 조회
     * @param boardIdx Long 게시글 idx
     * @param codeIdx Long 게시판 타입
     * @param study Study 스터디 객체
     * @return ResponseEntity<CommonResponse> 게시글 상세 정보
     */
    @Override
    public Board getBoardOrThrow(Long boardIdx, Long codeIdx, Study study) {
        Board board = getBoardOrThrow(boardIdx, codeIdx);
        if(!Objects.equals(board.getStudy(), study)) {
            throw new BoardNotFoundException();
        }
        return board;
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
        Board board = getBoardOrThrow(boardIdx, codeIdx);

        board.increaseHitCount();
        BoardResponse response = buildBoardResponse(board);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.OK);
    }

    /**
     * 스터디 게시글 조회
     * @param boardType String 게시판 타입
     * @param boardIdx Long 게시글 idx
     * @param studyIdx Long 스터디 idx
     * @return ResponseEntity<CommonResponse> 게시글 상세 정보
     */
    @Override
    public ResponseEntity<CommonResponse> findByBoardInfo(String boardType, Long studyIdx, Long boardIdx) {
        Long codeIdx = CommonBoardCode.getCode(boardType);
        Study study = studyService.getStudyOrThrow(studyIdx);

        Board board = getBoardOrThrow(boardIdx, codeIdx, study);

        Long memberIdx = sessionInfo.getMemberIdx();
        Member member = memberService.getMember(memberIdx);
        boolean isStudyMember = studyMemberService.existsByStudyAndMember(study, member);
        if(!isStudyMember) {
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
        }

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
        return getSliceOfBoard(boardType, idx, size, keyword, null);
    }

    /**
     * 게시글 목록 조회
     *
     * @param boardType     String 게시판 타입
     * @param idx Long 조회 시작 idx
     * @param size int 조회할 목록 갯수
     * @param keyword String 조회할 키워드
     * @param studyIdx Long 조회할 스터디 idx
     * @return ResponseEntity<CommonResponse> 게시글 목록
     */
    @Override
    public ResponseEntity<CommonResponse> getSliceOfBoard(String boardType, Long idx, int size, String keyword, Long studyIdx) {
        Long memberIdx = sessionInfo.getMemberIdx();
        if(Objects.isNull(memberIdx))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
        Study study = !Objects.isNull(studyIdx) ? studyService.getStudyOrThrow(studyIdx) : null;

        if(!Objects.isNull(study)) {
            Member member = memberService.getMember(memberIdx);
            boolean isStudyMember = studyMemberService.existsByStudyAndMember(study, member);
            if(!isStudyMember) {
                return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
            }
        }

        Long codeIdx = CommonBoardCode.getCode(boardType);
        if(Objects.isNull(codeIdx))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.DATA_NOT_FOUND), HttpStatus.NOT_FOUND);

        List<BoardListResponse> list = this.boardQueryRepo.getSliceOfBoard(codeIdx,
                                                                            idx,
                                                                            size,
                                                                            keyword,
                                                                            memberIdx,
                                                                            study);

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
        if(Objects.isNull(memberIdx))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);

        return getSliceOfMemberBoard(idx, size, memberIdx);
    }

    /**
     * 내가 작성한 게시글 목록 조회
     * @param idx Long 조회 시작 idx
     * @param size int 조회할 목록 갯수
     * @param memberIdx Long 조회할 멤버 idx
     * @return ResponseEntity<CommonResponse> 게시글 목록
     */
    @Override
    public ResponseEntity<CommonResponse> getSliceOfMemberBoard(Long idx, int size, Long memberIdx) {
        return getSliceOfMemberBoard(idx, size, memberIdx,null);
    }

    /**
     * 내가 작성한 게시글 목록 조회
     * @param idx Long 조회 시작 idx
     * @param size int 조회할 목록 갯수
     * @param memberIdx Long 조회할 멤버 idx
     * @param studyIdx Long 조회할 스터디 idx
     * @return ResponseEntity<CommonResponse> 게시글 목록
     */
    @Override
    public ResponseEntity<CommonResponse> getSliceOfMemberBoard(Long idx, int size, Long memberIdx, Long studyIdx) {
        Study study = !Objects.isNull(studyIdx) ? studyService.getStudyOrThrow(studyIdx) : null;

        if(!Objects.isNull(study)) {
            Member member = memberService.getMember(memberIdx);
            boolean isStudyMember = studyMemberService.existsByStudyAndMember(study, member);
            if(!isStudyMember) {
                return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
            }
        }

        List<BoardListResponse> list = this.boardQueryRepo.getSliceOfMemberBoard(idx,
                                                                                size,
                                                                                memberIdx,
                                                                                study);

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
        if(Objects.isNull(memberIdx))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
        return getSliceOfLikedBoard(idx, size, memberIdx);
    }

    /**
     * 내가 좋아요를 누른 게시글 목록 조회
     * @param idx Long 조회 시작 idx
     * @param size int 조회할 목록 갯수
     * @param memberIdx Long 조회할 멤버 idx
     * @return ResponseEntity<CommonResponse> 게시글 목록
     */
    @Override
    public ResponseEntity<CommonResponse> getSliceOfLikedBoard(Long idx, int size, Long memberIdx) {
        return getSliceOfLikedBoard(idx, size, memberIdx, null);
    }

    /**
     * 내가 좋아요를 누른 게시글 목록 조회
     * @param idx Long 조회 시작 idx
     * @param size int 조회할 목록 갯수
     * @param memberIdx Long 조회할 멤버 idx
     * @param studyIdx Long 조회할 스터디 idx
     * @return ResponseEntity<CommonResponse> 게시글 목록
     */
    @Override
    public ResponseEntity<CommonResponse> getSliceOfLikedBoard(Long idx, int size, Long memberIdx, Long studyIdx) {
        Member member = memberService.getMember(memberIdx);
        Study study = !Objects.isNull(studyIdx) ? studyService.getStudyOrThrow(studyIdx) : null;

        if(!Objects.isNull(study)) {
            boolean isStudyMember = studyMemberService.existsByStudyAndMember(study, member);
            if(!isStudyMember) {
                return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
            }
        }


        List<BoardListResponse> list = this.boardQueryRepo.getSliceOfLikedBoard(idx,
                size,
                memberIdx,
                study);

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
    public ResponseEntity<CommonResponse> getSliceOfBookmarkedBoard(Long idx, int size) {Long memberIdx = sessionInfo.getMemberIdx();
        if(Objects.isNull(memberIdx))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);

        return getSliceOfBookmarkedBoard(idx, size, memberIdx);
    }

    /**
     * 북마크한 게시글 목록 조회
     * @param idx Long 조회 시작 idx
     * @param size int 조회할 목록 갯수
     * @param memberIdx Long 조회할 멤버 idx
     * @return ResponseEntity<CommonResponse> 게시글 목록
     */
    @Override
    public ResponseEntity<CommonResponse> getSliceOfBookmarkedBoard(Long idx, int size, Long memberIdx) {
        return getSliceOfBookmarkedBoard(idx, size, memberIdx, null);
    }

    /**
     * 북마크한 게시글 목록 조회
     * @param idx Long 조회 시작 idx
     * @param size int 조회할 목록 갯수,
     * @param studyIdx Long 조회할 스터디 idx
     * @param memberIdx Long 조회할 멤버 idx
     * @return ResponseEntity<CommonResponse> 게시글 목록
     */
    @Override
    public ResponseEntity<CommonResponse> getSliceOfBookmarkedBoard(Long idx, int size, Long memberIdx, Long studyIdx) {
        Member member = memberService.getMember(memberIdx);
        Study study = !Objects.isNull(studyIdx) ? studyService.getStudyOrThrow(studyIdx) : null;

        if(!Objects.isNull(study)) {
            boolean isStudyMember = studyMemberService.existsByStudyAndMember(study, member);
            if(!isStudyMember) {
                return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
            }
        }

        List<BoardListResponse> list = this.boardQueryRepo.getSliceOfBookmarkedBoard(idx,
                size,
                memberIdx,
                study);

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
        return getSliceOfHotBoard(boardType, idx, size, null);
    }

    /**
     * 인기글 게시글 목록 조회
     * @param boardType String 게시판 타입
     * @param idx Long 조회 시작 idx
     * @param size int 조회할 목록 갯수
     * @param studyIdx Long 조회할 스터디 idx
     * @return ResponseEntity<CommonResponse> 게시글 목록
     */
    @Override
    public ResponseEntity<CommonResponse> getSliceOfHotBoard(String boardType, Long idx, int size, Long studyIdx) {
        Long memberIdx = sessionInfo.getMemberIdx();
        if(Objects.isNull(memberIdx))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
        Member member = memberService.getMember(memberIdx);
        Study study = !Objects.isNull(studyIdx) ? studyService.getStudyOrThrow(studyIdx) : null;

        if(!Objects.isNull(study)) {
            boolean isStudyMember = studyMemberService.existsByStudyAndMember(study, member);
            if(!isStudyMember) {
                return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
            }
        }

        Long codeIdx = CommonBoardCode.getCode(boardType);
        if(Objects.isNull(codeIdx))
            throw new BoardNotFoundException();

        List<BoardListResponse> list = this.boardQueryRepo.getSliceOfHotBoard(codeIdx,
                                                                                idx,
                                                                                size,
                                                                                memberIdx,
                                                                                study);

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
        boolean isAuth = this.isCreatedByMember(boardIdx, member);
        if(!isAuth) {
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.FORBIDDEN), HttpStatus.FORBIDDEN);
        }

        Board board = this.getBoardOrThrow(boardIdx, codeIdx);


        board.update(boardRequest.getTitle(), org.springframework.web.util.HtmlUtils.htmlEscape(boardRequest.getContent()));
        BoardResponse response = buildBoardResponse(board);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.OK);
    }

    /**
     * 게시글 수정
     * @param boardType String 게시판 타입
     * @param boardIdx Long 게시글 idx
     * @param studyIdx Long 스터디 idx
     * @param boardRequest BoardUpdateRequestIO 게시글 수정 데이터
     * @return ResponseEntity<CommonResponse> 게시글 상세 정보
     */
    @Override
    @Transactional
    public ResponseEntity<CommonResponse> update(String boardType, Long boardIdx, Long studyIdx, BoardUpdateRequest boardRequest) {
        Long codeIdx = CommonBoardCode.getCode(boardType);

        Long memberIdx = sessionInfo.getMemberIdx();
        if (Objects.isNull(memberIdx))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);

        Member member = memberService.getMember(memberIdx);

        Study study = studyService.getStudyOrThrow(studyIdx);

        boolean isAuth = this.isCreatedByMember(boardIdx, member, study);
        if(!isAuth) {
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.FORBIDDEN), HttpStatus.FORBIDDEN);
        }

        boolean isStudyMember = studyMemberService.existsByStudyAndMember(study, member);
        if(!isStudyMember) {
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
        }

        Board board = this.getBoardOrThrow(boardIdx, codeIdx, study);


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
        if(Objects.isNull(memberIdx))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);

        Member member = memberService.getMember(memberIdx);
        boolean isAuth = this.isCreatedByMember(boardIdx, member);
        if(!isAuth) {
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
        }

        Board board = this.getBoardOrThrow(boardIdx, codeIdx);

        board.delete();

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(), HttpStatus.OK);
    }

    /**
     * 게시글 삭제
     * @param boardType String 게시판 타입
     * @param boardIdx Long 게시글 idx
     * @param studyIdx Long 스터디 idx
     * @return ResponseEntity<CommonResponse> 삭제 응답 결과
     */
    @Override
    @Transactional
    public ResponseEntity<CommonResponse> delete(String boardType, Long boardIdx, Long studyIdx) {
        Long codeIdx = CommonBoardCode.getCode(boardType);
        Long memberIdx = sessionInfo.getMemberIdx();
        Member member = memberService.getMember(memberIdx);

        Study study = studyService.getStudyOrThrow(studyIdx);

        boolean isAuth = this.isCreatedByMember(boardIdx, member, study);
        if(!isAuth) {
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
        }

        boolean isStudyMember = studyMemberService.existsByStudyAndMember(study, member);
        if(!isStudyMember) {
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
        }

        Board board = this.getBoardOrThrow(boardIdx, codeIdx, study);

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
        List<ReplyResponse> replyResponses = new ArrayList<>();
        long likeCount = 0L;
        List<BoardFileResponse> files = new ArrayList<>();
        boolean isLiked = false;
        boolean isBookmarked = false;

        if(!Objects.isNull(board.getReplies()))
            replyResponses = board.getReplies().stream()
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

        if(!Objects.isNull(board.getReplies()))
            likeCount = board.getLikes().stream()
                    .filter(like -> like.getLikeIsLiked() == 1).count();

        if(!Objects.isNull(board.getFiles()))
            files = board.getFiles().stream()
                                            .map(file -> BoardFileResponse.builder()
                                                    .idx(file.getFileIdx())
                                                    .fileName(file.getFileOriginName())
                                                    .fileType(file.getCommonCode().getCodeName())
                                                    .fileUrl(serverInfo.getServerUrlWithPort() + serverInfo.getFilePath() +file.getFileIdx())
                                                    .build()).collect(Collectors.toList());

        if(!Objects.isNull(board.getLikes()))
            isLiked = board.getLikes().stream()
                    .anyMatch(like -> like.getMember().getMemberIdx().equals(memberIdx) && like.getLikeIsLiked() == 1);


        if(!Objects.isNull(board.getBookmarks()))
            isBookmarked = board.getBookmarks().stream()
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
                .files(files)
                .build();
    }

    /**
     * 파일 서버 업로드
     *
     * @param files MultipartFile
     * @return ResponseEntity<CommonResponse>
     */
    @Override
    public Map<String, String> convertFilesNameAndUrl(List<Files> files) throws IOException {
        if(Objects.isNull(files) || files.isEmpty()) return null;
        Map<String, String> fileMap = new HashMap<>();
        for (Files file : files) {
            String originalName = file.getFileOriginName();
            Long fileIdx = file.getFileIdx();
            fileMap.put(originalName, serverInfo.getServerUrlWithPort() + serverInfo.getFilePath() +fileIdx);
        }
        return fileMap;
    }

    /**
     * 내가 작성한 게시글인지 확인
     * @param boardIdx 게시글 idx
     * @param member 멤버 객체
     * @return boolean
     */
    @Override
    public boolean isCreatedByMember(Long boardIdx, Member member) {
        Board board = this.getBoardOrThrow(boardIdx);
        return board.getMember().getMemberIdx().equals(member.getMemberIdx());
    }

    /**
     * 내가 작성한 스터디 게시글인지 확인
     * @param boardIdx 게시글 idx
     * @param member 멤버 객체
     * @param study 스터디 객체
     * @return boolean
     */
    @Override
    public boolean isCreatedByMember(Long boardIdx, Member member, Study study) {
        Board board = this.getBoardOrThrow(boardIdx);
        return board.getMember().getMemberIdx().equals(member.getMemberIdx()) && board.getStudy().getStudyIdx().equals(study.getStudyIdx());
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
