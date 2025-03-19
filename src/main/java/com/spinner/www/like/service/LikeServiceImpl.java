package com.spinner.www.like.service;

import com.spinner.www.board.entity.Board;
import com.spinner.www.board.service.BoardService;
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
    /**
     * 좋아요 생성 또는 업데이트
     * @param boardIdx Long
     * @return ResponseEntity<CommonResponse> 좋아요 상세 정보
     */
    @Override
    public ResponseEntity<CommonResponse> upsertBoard(Long boardIdx) {
        Long memberIdx = sessionInfo.getMemberIdx();
        if (Objects.isNull(memberIdx))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);

        Member member = memberService.getMember(memberIdx);
        if (Objects.isNull(member))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);

        Board board = boardService.findByBoardIdx(boardIdx);
        if (Objects.isNull(board))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.DATA_NOT_FOUND), HttpStatus.NOT_FOUND);

        List<Like> likes = this.findByBoardIdx(boardIdx);

        Like like = likes.stream().filter(val -> val.getMember().getMemberIdx().equals(memberIdx)).findFirst().orElse(null);

        if(Objects.isNull(like)) {
            return this.insertBoard(boardIdx);
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
        if (Objects.isNull(memberIdx))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);

        Member member = memberService.getMember(memberIdx);
        if (Objects.isNull(member))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);

        Reply reply= replyService.findByReplyIdx(replyIdx);
        if (Objects.isNull(reply))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.DATA_NOT_FOUND), HttpStatus.NOT_FOUND);

        Board board = boardService.findByBoardIdx(reply.getBoardIdx());
        if (Objects.isNull(board))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.DATA_NOT_FOUND), HttpStatus.NOT_FOUND);

        List<Like> likes = this.findByReplyIdx(replyIdx);

        Like like = likes.stream().filter(val -> val.getMember().getMemberIdx().equals(memberIdx)).findFirst().orElse(null);

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

        Like like = Like.builder()
                .member(member)
                .boardIdx(boardIdx)
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

        Like like = Like.builder()
                .member(member)
                .replyIdx(replyIdx)
                .likeIsLiked(1)
                .build();

        likeRepo.save(like);

        LikeReplyResponse response = likeMapper.likeToLikeReplyResponse(like);

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.CREATED);
    }


    @Override
    public List<Like> findByBoardIdx(Long boardIdx) {
        return likeRepo.findByBoardIdx(boardIdx);
    }

    @Override
    public List<Like> findByReplyIdx(Long boardIdx) {
        return likeRepo.findByReplyIdx(boardIdx);
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
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.FORBIDDEN), HttpStatus.FORBIDDEN);

        like.update();
        likeRepo.save(like);

        if(Objects.isNull(like.getBoardIdx())){
            LikeReplyResponse response = likeMapper.likeToLikeReplyResponse(like);

            return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.OK);
        }else {

            LikeBoardResponse response = likeMapper.likeToLikeBoardResponse(like);

            return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.OK);
        }
    }
}
