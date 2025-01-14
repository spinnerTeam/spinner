package com.spinner.www.reply.service;

import com.spinner.www.board.constants.CommonBoardCode;
import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.constants.CommonResultCode;
import com.spinner.www.like.service.LikeService;
import com.spinner.www.member.dto.SessionInfo;
import com.spinner.www.member.entity.Member;
import com.spinner.www.member.service.MemberService;
import com.spinner.www.board.entity.Board;
import com.spinner.www.board.service.BoardService;
import com.spinner.www.reply.dto.ReplyCreateDto;
import com.spinner.www.reply.dto.ReplyUpdateDto;
import com.spinner.www.reply.entity.Reply;
import com.spinner.www.reply.io.ReplyCreateRequest;
import com.spinner.www.reply.io.ReplyUpdateRequest;
import com.spinner.www.reply.mapper.ReplyMapper;
import com.spinner.www.reply.repository.ReplyRepo;
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
    private final LikeService likeService;
    /**
     * 댓글 생성
     * @param boardType String
     * @param replyRequest ReplyCreateRequestIO 댓글 요청 데이터
     * @return ResponseEntity<CommonResponse> 댓글 상세 정보
     */
    @Override
    public ResponseEntity<CommonResponse> insert(String boardType, ReplyCreateRequest replyRequest) {
        Long codeIdx = CommonBoardCode.getCode(boardType);
        Long memberIdx = sessionInfo.getMemberIdx();
        Board board = boardService.findByBoardIdx(codeIdx, replyRequest.getBoardIdx());
        ReplyCreateDto replyDto = replyMapper.replyCreateRequestToReplyCreateDto(replyRequest);
        Long replyParentIdx = Objects.isNull(replyDto.getParentIdx()) ? null :replyDto.getParentIdx();

        if (Objects.isNull(memberIdx))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);

        if (Objects.isNull(board))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.DATA_NOT_FOUND), HttpStatus.NOT_FOUND);

        Member member = memberService.getMember(memberIdx);

        Reply reply = Reply.builder()
                .member(member)
                .boardIdx(board.getBoardIdx())
                .replyParentIdx(replyParentIdx)
                .replyContent(replyDto.getReplyContent())
                .build();

        replyRepo.save(reply);

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(), HttpStatus.CREATED);
    }


    @Override
    public Reply findByReplyIdx(Long replyIdx) {
        int isNotRemove = 0;
        return replyRepo.findByReplyIdxAndReplyIsRemoved(replyIdx, isNotRemove).orElse(null);
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
        Reply reply= this.findByReplyIdx(replyIdx);
        Long boardIdx = reply.getBoardIdx();
        Board board = boardService.findByBoardIdx(codeIdx, boardIdx);
        ReplyUpdateDto replyDto = replyMapper.replyUpdateRequestToReplyUpdateDto(replyRequest);

        if (Objects.isNull(memberIdx))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);

        if (Objects.isNull(reply))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.DATA_NOT_FOUND), HttpStatus.NOT_FOUND);

        if (Objects.isNull(board))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.DATA_NOT_FOUND), HttpStatus.NOT_FOUND);

        if (!Objects.equals(reply.getMember(), member))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.FORBIDDEN), HttpStatus.FORBIDDEN);

        reply.update(replyDto.getReplyContent());

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(), HttpStatus.OK);
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
        Reply reply= this.findByReplyIdx(replyIdx);
        Board board = boardService.findByBoardIdx(codeIdx, reply.getBoardIdx());

        if (Objects.isNull(memberIdx))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);

        if (Objects.isNull(reply) || Objects.isNull(board) || !board.getCodeIdx().equals(codeIdx))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.DATA_NOT_FOUND), HttpStatus.NOT_FOUND);

        if (!Objects.equals(reply.getMember(), member))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.FORBIDDEN), HttpStatus.FORBIDDEN);

        reply.delete();

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(), HttpStatus.OK);
    }



    /**
     * 좋아요 생성 또는 업데이트
     *
     * @param boardType String 게시판 타입
     * @param replyIdx  Long 댓글 idx
     * @return ResponseEntity<CommonResponse> 삭제 응답 결과
     */
    @Override
    public ResponseEntity<CommonResponse> upsertLike(String boardType, Long replyIdx) {
        Long codeIdx = CommonBoardCode.getCode(boardType);
        Long memberIdx = sessionInfo.getMemberIdx();
        Member member = memberService.getMember(memberIdx);
        Reply reply= this.findByReplyIdx(replyIdx);
        Board board = boardService.findByBoardIdx(codeIdx, reply.getBoardIdx());

        if (Objects.isNull(memberIdx))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);

        if (Objects.isNull(board))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.DATA_NOT_FOUND), HttpStatus.NOT_FOUND);

        if (!Objects.equals(board.getMember(), member))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.FORBIDDEN), HttpStatus.FORBIDDEN);

        return likeService.upsertReply(replyIdx);
    }
}
