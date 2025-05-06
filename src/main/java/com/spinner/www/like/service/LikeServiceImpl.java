package com.spinner.www.like.service;

import com.spinner.www.board.entity.Board;
import com.spinner.www.board.service.BoardService;
import com.spinner.www.common.exception.ForbiddenException;
import com.spinner.www.common.exception.NotFoundException;
import com.spinner.www.common.exception.UnauthorizedException;
import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.constants.CommonResultCode;
import com.spinner.www.like.entity.Like;
import com.spinner.www.like.io.LikeBoardResponse;
import com.spinner.www.like.io.LikeReplyResponse;
import com.spinner.www.like.mapper.LikeMapper;
import com.spinner.www.like.repository.LikeRepo;
import com.spinner.www.member.dto.SessionInfo;
import com.spinner.www.member.entity.Member;
import com.spinner.www.member.service.MemberService;
import com.spinner.www.reply.entity.Reply;
import com.spinner.www.reply.service.ReplyService;
import com.spinner.www.study.entity.Study;
import com.spinner.www.study.service.StudyMemberService;
import com.spinner.www.study.service.StudyService;
import com.spinner.www.util.ResponseVOUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class LikeServiceImpl implements LikeService {

    private final SessionInfo sessionInfo;
    private final LikeRepo likeRepo;
    private final MemberService memberService;
    private final ReplyService replyService;
    private final BoardService boardService;
    private final LikeMapper likeMapper;
    private final StudyService studyService;
    private final StudyMemberService studyMemberService;
    /**
     * 좋아요 생성 또는 업데이트
     * @param boardIdx Long
     * @return ResponseEntity<CommonResponse> 좋아요 상세 정보
     */
    @Override
    public ResponseEntity<CommonResponse> upsertBoard(Long boardIdx) {
        Long memberIdx = sessionInfo.getMemberIdx();
        Member member = memberService.getMember(memberIdx);

        Board board = boardService.getBoardOrThrow(boardIdx);
        if (Objects.isNull(board))
            throw new NotFoundException(CommonResultCode.NOT_FOUND_BOARD);

        List<Like> likes = this.findByBoard(board);

        Like like = likes.stream().filter(val -> val.getMember().equals(member)).findFirst().orElse(null);

        if(Objects.isNull(like)) {
            return this.insertBoard(boardIdx);
        }

        return this.update(like);
    }

    /**
     * 좋아요 생성
     * @param studyIdx Long
     * @param boardIdx Long
     * @return ResponseEntity<CommonResponse> 좋아요 상세 정보
     */
    @Override
    public ResponseEntity<CommonResponse> upsertBoard(Long studyIdx, Long boardIdx) {
        Long memberIdx = sessionInfo.getMemberIdx();
        Member member = memberService.getMember(memberIdx);

        Study study = Objects.isNull(studyIdx) ? null : studyService.getStudyOrThrow(studyIdx);

        if(!Objects.isNull(study)) {
            boolean isStudyMember = studyMemberService.existsByStudyAndMember(study, member);
            if(!isStudyMember) {
                throw new UnauthorizedException();
            }
        }

        Board board = boardService.getBoardOrThrow(boardIdx);
        if (Objects.isNull(board))
            throw new NotFoundException(CommonResultCode.NOT_FOUND_BOARD);

        List<Like> likes = this.findByBoard(board);

        Like like = likes.stream().filter(val -> val.getMember().equals(member)).findFirst().orElse(null);

        if(Objects.isNull(like)) {
            return this.insertBoard(boardIdx);
        }

        return this.update(like);
    }

    /**
     * 좋아요 생성 또는 업데이트
     * @param studyIdx Long
     * @param replyIdx Long
     * @return ResponseEntity<CommonResponse> 좋아요 상세 정보
     */
    @Override
    public ResponseEntity<CommonResponse> upsertReply(Long studyIdx, Long replyIdx) {
        Long memberIdx = sessionInfo.getMemberIdx();
        Member member = memberService.getMember(memberIdx);

        Reply reply= replyService.getReplyOrThrow(replyIdx);
        if (Objects.isNull(reply))
            throw new NotFoundException(CommonResultCode.NOT_FOUND_REPLY);

        Study study = Objects.isNull(studyIdx) ? null : studyService.getStudyOrThrow(studyIdx);

        if(!Objects.isNull(study)) {
            boolean isStudyMember = studyMemberService.existsByStudyAndMember(study, member);
            if(!isStudyMember) {
                throw new UnauthorizedException();
            }
        }

        Board board = boardService.getBoardOrThrow(reply.getBoard().getBoardIdx());
        if (Objects.isNull(board))
            throw new NotFoundException(CommonResultCode.NOT_FOUND_BOARD);

        List<Like> likes = this.findByReply(reply);

        Like like = likes.stream().filter(val -> val.getMember().equals(member)).findFirst().orElse(null);

        if(Objects.isNull(like)) {
            return this.insertReply(replyIdx);
        }

        return this.update(like);
    }

    /**
     * 좋아요 생성 또는 업데이트
     * @param replyIdx Long
     * @return ResponseEntity<CommonResponse> 좋아요 상세 정보
     */
    @Override
    public ResponseEntity<CommonResponse> upsertReply(Long replyIdx) {
        Long memberIdx = sessionInfo.getMemberIdx();
        Member member = memberService.getMember(memberIdx);

        Reply reply= replyService.getReplyOrThrow(replyIdx);
        if (Objects.isNull(reply))
            throw new NotFoundException(CommonResultCode.NOT_FOUND_REPLY);

        Board board = boardService.getBoardOrThrow(reply.getBoard().getBoardIdx());
        if (Objects.isNull(board))
            throw new NotFoundException(CommonResultCode.NOT_FOUND_BOARD);

        List<Like> likes = this.findByReply(reply);

        Like like = likes.stream().filter(val -> val.getMember().equals(member)).findFirst().orElse(null);

        if(Objects.isNull(like)) {
            return this.insertReply(replyIdx);
        }

        return this.update(like);
    }

    /**
     * 좋아요 생성
     * @param boardIdx Long
     * @return ResponseEntity<CommonResponse> 좋아요 상세 정보
     */
    @Override
    public ResponseEntity<CommonResponse> insertBoard(Long boardIdx) {
        Long memberIdx = sessionInfo.getMemberIdx();
        Member member = memberService.getMember(memberIdx);

        Board board = boardService.getBoardOrThrow(boardIdx);
        if (Objects.isNull(board))
            throw new NotFoundException(CommonResultCode.NOT_FOUND_BOARD);

        Like like = Like.builder()
                .member(member)
                .board(board)
                .likeIsLiked(1)
                .build();

        likeRepo.save(like);

        LikeBoardResponse response = likeMapper.likeToLikeBoardResponse(like);

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.CREATED);
    }

    /**
     * 좋아요 생성
     * @param replyIdx Long
     * @return ResponseEntity<CommonResponse> 좋아요 상세 정보
     */
    @Override
    public ResponseEntity<CommonResponse> insertReply(Long replyIdx) {
        Long memberIdx = sessionInfo.getMemberIdx();
        Member member = memberService.getMember(memberIdx);

        Reply reply = replyService.getReplyOrThrow(replyIdx);
        if (Objects.isNull(reply))
            throw new NotFoundException(CommonResultCode.NOT_FOUND_REPLY);

        Like like = Like.builder()
                .member(member)
                .reply(reply)
                .likeIsLiked(1)
                .build();

        likeRepo.save(like);

        LikeReplyResponse response = likeMapper.likeToLikeReplyResponse(like);

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.CREATED);
    }


    @Override
    public List<Like> findByBoard(Board board) {
        return likeRepo.findByBoard(board);
    }

    @Override
    public List<Like> findByReply(Reply reply) {
        return likeRepo.findByReply(reply);
    }

    /**
     * 좋아요 수정
     * @param like Like
     * @return ResponseEntity<CommonResponse> 좋아요 상세 정보
     */
    @Override
    public ResponseEntity<CommonResponse> update(Like like) {
        Long memberIdx = sessionInfo.getMemberIdx();
        Member member = memberService.getMember(memberIdx);

        if (!Objects.equals(like.getMember(), member))
            throw new ForbiddenException();

        like.update();
        likeRepo.save(like);

        if(Objects.isNull(like.getBoard())){
            LikeReplyResponse response = likeMapper.likeToLikeReplyResponse(like);

            return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.OK);
        }else {

            LikeBoardResponse response = likeMapper.likeToLikeBoardResponse(like);

            return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.OK);
        }
    }
}
