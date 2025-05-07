package com.spinner.www.reply.service;

import com.spinner.www.board.constants.CommonBoardCode;
import com.spinner.www.common.exception.ForbiddenException;
import com.spinner.www.common.exception.NotFoundException;
import com.spinner.www.common.exception.UnauthorizedException;
import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.constants.CommonResultCode;
import com.spinner.www.member.dto.SessionInfo;
import com.spinner.www.member.entity.Member;
import com.spinner.www.member.service.MemberService;
import com.spinner.www.board.entity.Board;
import com.spinner.www.board.service.BoardService;
import com.spinner.www.reply.dto.ReplyCreateDto;
import com.spinner.www.reply.dto.ReplyUpdateDto;
import com.spinner.www.reply.entity.Reply;
import com.spinner.www.reply.io.ReplyCreateRequest;
import com.spinner.www.reply.io.ReplyResponse;
import com.spinner.www.reply.io.ReplyUpdateRequest;
import com.spinner.www.reply.mapper.ReplyMapper;
import com.spinner.www.reply.repository.ReplyRepo;
import com.spinner.www.study.entity.Study;
import com.spinner.www.study.service.StudyMemberService;
import com.spinner.www.study.service.StudyService;
import com.spinner.www.util.ResponseVOUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class ReplyServiceImpl implements ReplyService {

    private final SessionInfo sessionInfo;
    private final ReplyRepo replyRepo;
    private final MemberService memberService;
    private final BoardService boardService;
    private final ReplyMapper replyMapper;
    private final StudyService studyService;
    private final StudyMemberService studyMemberService;
    /**
     * 댓글 생성
     * @param boardType String
     * @param replyRequest ReplyCreateRequestIO 댓글 요청 데이터
     * @return ResponseEntity<CommonResponse> 댓글 상세 정보
     */
    @Override
    public ResponseEntity<CommonResponse> insert(String boardType, ReplyCreateRequest replyRequest) {
        return this.insert(boardType, null, replyRequest);
    }

    /**
     * 댓글 생성
     * @param boardType String
     * @param studyIdx Long 스터디 idx
     * @param replyRequest ReplyCreateRequestIO 댓글 요청 데이터
     * @return ResponseEntity<CommonResponse> 댓글 상세 정보
     */
    @Override
    public ResponseEntity<CommonResponse> insert(String boardType, Long studyIdx, ReplyCreateRequest replyRequest) {
        Long codeIdx = CommonBoardCode.getCode(boardType);
        Long memberIdx = sessionInfo.getMemberIdx();

        Member member = memberService.getMember(memberIdx);

        Study study = Objects.isNull(studyIdx) ? null : studyService.getStudyOrThrow(studyIdx);

        if(!Objects.isNull(study)) {
            boolean isStudyMember = studyMemberService.existsByStudyAndMember(study, member);
            if(!isStudyMember) {
                throw new UnauthorizedException();
            }
        }

        Board board = boardService.getBoardOrThrow(replyRequest.getBoardIdx(), codeIdx);
        if (Objects.isNull(board))
            throw new NotFoundException(CommonResultCode.NOT_FOUND_BOARD);

        ReplyCreateDto replyDto = replyMapper.replyCreateRequestToReplyCreateDto(replyRequest);
        Long replyParentIdx = Objects.isNull(replyDto.getParentIdx()) ? null :replyDto.getParentIdx();

        Reply reply = Reply.builder()
                .member(member)
                .board(board)
                .replyParentIdx(replyParentIdx)
                .replyContent(replyDto.getReplyContent())
                .build();

        replyRepo.save(reply);
        ReplyResponse response = ReplyResponse.builder()
                .nickname(member.getMemberNickname())
                .idx(reply.getReplyIdx())
                .content(reply.getReplyContent())
                .createdDate(reply.getCreatedDate())
                .modifiedDate(reply.getModifiedDate())
                .build();

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.CREATED);
    }


    @Override
    public Reply getReplyOrThrow(Long replyIdx) {
        int isNotRemove = 0;
        return replyRepo.findByReplyIdxAndReplyIsRemoved(replyIdx, isNotRemove).orElseThrow(() -> new NotFoundException(CommonResultCode.NOT_FOUND_REPLY));
    }

    /**
     * 댓글 조회
     * @param replyIdx Long 댓글 idx
     * @param codeIdx Long 게시판 타입 idx
     * @return ResponseEntity<CommonResponse> 댓글 상세 정보
     */
    @Override
    public Reply getReplyOrThrow(Long replyIdx, Long codeIdx) {
        Reply reply = this.getReplyOrThrow(replyIdx);
        if(!Objects.equals(reply.getBoard().getCommonCode().getCodeIdx(), codeIdx)) throw new NotFoundException(CommonResultCode.NOT_FOUND_REPLY);
        return reply;
    }

    /**
     * 댓글 조회
     * @param replyIdx Long 댓글 idx
     * @param codeIdx Long 게시판 타입 idx
     * @param study Study 스터디 객체
     * @return ResponseEntity<CommonResponse> 댓글 상세 정보
     */
    @Override
    public Reply getReplyOrThrow(Long replyIdx, Long codeIdx, Study study) {
        Reply reply = this.getReplyOrThrow(replyIdx, codeIdx);
        if(!Objects.equals(reply.getBoard().getStudy(), study)) throw new NotFoundException(CommonResultCode.NOT_FOUND_REPLY);
        return reply;
    }

    /**
     * 댓글 수정
     * @param boardType String
     * @param replyIdx Long 댓글 idx
     * @param replyRequest ReplyUpdateRequestIO 댓글 수정 데이터
     * @return ResponseEntity<CommonResponse> 댓글 상세 정보
     */
    @Override
    @Transactional
    public ResponseEntity<CommonResponse> update(String boardType, Long replyIdx, ReplyUpdateRequest replyRequest) {
        Long codeIdx = CommonBoardCode.getCode(boardType);
        Long memberIdx = sessionInfo.getMemberIdx();

        Member member = memberService.getMember(memberIdx);

        Reply reply = this.getReplyOrThrow(replyIdx, codeIdx);

        if (!Objects.equals(reply.getMember(), member))
            throw new ForbiddenException();

        Board board = reply.getBoard();
        if (Objects.isNull(board))
            throw new NotFoundException(CommonResultCode.NOT_FOUND_BOARD);

        ReplyUpdateDto replyDto = replyMapper.replyUpdateRequestToReplyUpdateDto(replyRequest);
        reply.update(replyDto.getReplyContent());
        ReplyResponse response = ReplyResponse.builder()
                .nickname(member.getMemberNickname())
                .idx(reply.getReplyIdx())
                .content(reply.getReplyContent())
                .createdDate(reply.getCreatedDate())
                .modifiedDate(reply.getModifiedDate())
                .build();

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.OK);
    }

    /**
     * 댓글 수정
     * @param boardType String
     * @param replyIdx Long 댓글 idx
     * @param studyIdx Long 스터디 idx
     * @param replyRequest ReplyUpdateRequestIO 댓글 수정 데이터
     * @return ResponseEntity<CommonResponse> 댓글 상세 정보
     */
    @Override
    @Transactional
    public ResponseEntity<CommonResponse> update(String boardType, Long replyIdx, Long studyIdx, ReplyUpdateRequest replyRequest) {
        Long codeIdx = CommonBoardCode.getCode(boardType);
        Long memberIdx = sessionInfo.getMemberIdx();

        Member member = memberService.getMember(memberIdx);
        Study study = studyService.getStudyOrThrow(studyIdx);

        boolean isStudyMember = studyMemberService.existsByStudyAndMember(study, member);
        if(!isStudyMember) {
            throw new UnauthorizedException();
        }

        Reply reply = this.getReplyOrThrow(replyIdx, codeIdx, study);

        if (!Objects.equals(reply.getMember(), member))
            throw new ForbiddenException();

        Board board = reply.getBoard();
        if (Objects.isNull(board))
            throw new NotFoundException(CommonResultCode.NOT_FOUND_BOARD);

        ReplyUpdateDto replyDto = replyMapper.replyUpdateRequestToReplyUpdateDto(replyRequest);
        reply.update(replyDto.getReplyContent());
        ReplyResponse response = ReplyResponse.builder()
                .nickname(member.getMemberNickname())
                .idx(reply.getReplyIdx())
                .content(reply.getReplyContent())
                .createdDate(reply.getCreatedDate())
                .modifiedDate(reply.getModifiedDate())
                .build();

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.OK);
    }

    /**
     * 댓글 삭제
     * @param boardType String
     * @param replyIdx Long 댓글 idx
     * @return ResponseEntity<CommonResponse> 삭제 응답 결과
     */
    @Override
    @Transactional
    public ResponseEntity<CommonResponse> delete(String boardType, Long replyIdx) {
        Long codeIdx = CommonBoardCode.getCode(boardType);
        Long memberIdx = sessionInfo.getMemberIdx();

        Member member = memberService.getMember(memberIdx);

        Reply reply= this.getReplyOrThrow(replyIdx, codeIdx);

        if (Objects.isNull(reply))
            throw new NotFoundException(CommonResultCode.NOT_FOUND_BOARD);

        if (!Objects.equals(reply.getMember(), member))
            throw new ForbiddenException();


        Board board = boardService.getBoardOrThrow(reply.getBoard().getBoardIdx(), codeIdx);
        if (Objects.isNull(board) || !board.getCommonCode().getCodeIdx().equals(codeIdx))
            throw new NotFoundException(CommonResultCode.NOT_FOUND_BOARD);

        reply.delete();

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(), HttpStatus.OK);
    }

    /**
     * 댓글 삭제
     * @param boardType String
     * @param replyIdx Long 댓글 idx
     * @param studyIdx Long 스터디 idx
     * @return ResponseEntity<CommonResponse> 삭제 응답 결과
     */
    @Override
    @Transactional
    public ResponseEntity<CommonResponse> delete(String boardType, Long replyIdx, Long studyIdx) {
        Long codeIdx = CommonBoardCode.getCode(boardType);
        Long memberIdx = sessionInfo.getMemberIdx();

        Member member = memberService.getMember(memberIdx);

        Study study = studyService.getStudyOrThrow(studyIdx);

        boolean isStudyMember = studyMemberService.existsByStudyAndMember(study, member);
        if(!isStudyMember) {
            throw new UnauthorizedException();
        }

        Reply reply= this.getReplyOrThrow(replyIdx, codeIdx, study);

        if (Objects.isNull(reply))
            throw new NotFoundException(CommonResultCode.NOT_FOUND_BOARD);

        if (!Objects.equals(reply.getMember(), member))
            throw new ForbiddenException();


        Board board = boardService.getBoardOrThrow(reply.getBoard().getBoardIdx(), codeIdx);
        if (Objects.isNull(board) || !board.getCommonCode().getCodeIdx().equals(codeIdx))
            throw new NotFoundException(CommonResultCode.NOT_FOUND_BOARD);

        reply.delete();

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(), HttpStatus.OK);
    }

}
