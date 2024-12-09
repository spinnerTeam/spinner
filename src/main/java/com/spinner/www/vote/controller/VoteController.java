package com.spinner.www.vote.controller;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.constants.CommonResultCode;
import com.spinner.www.util.ResponseVOUtils;
import com.spinner.www.vote.io.VoteDeleteRequest;
import com.spinner.www.vote.io.VoteItemDeleteRequest;
import com.spinner.www.vote.io.VoteCreateRequest;
import com.spinner.www.vote.io.VoteUpdateRequest;
import com.spinner.www.vote.service.VoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/vote")
public class VoteController {

    private final VoteService voteService;

    /**
     * [memo]
     * - 투표 항목은 수정, 추가, 삭제될 수 있다.
     * (1) 투표 항목은 최대 다섯 개까지만 추가 가능하다.
     * 투표 항목을 생성할 때, 복수 선택 여부를 확인할 수 있다.
     * 투표 완료 후 참여자 인원 수를 확인할 수 있다. 이때 닉네임은 익명이다.
     * 투표 완료 후 투표수를 확인할 수 있다. (몇 퍼센트의 비율인지 또한 확인할 수 있다.)
     * 커뮤 투표 > [익명] 일정 지정 마감 없고, 투표를 완료한 사람들만 투표 결과 확인 가능
     * 스터디 투표 > [닉네임] 일정 지정 마감 o, 투표 즉시 종료 기능 o, 투표 마감 시 결과 확인 가능, 투표가 마감되면 새로운 게시물 생성
     *
     * [12.09] 투표 추가 및 업데이트, 삭제 진행
     *
     */

    /**
     * 투표 추가
     * @param voteCreateRequest VoteCreateRequest
     * @return ResponseEntity<CommonResponse>
     */
    @PostMapping
    public ResponseEntity<CommonResponse> insertVote(@Valid @RequestBody VoteCreateRequest voteCreateRequest) {

        // 투표 항목이 비어 있을 시
        if (voteCreateRequest.getVoteItemCreateRequestList().isEmpty()) {
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.INVALID_VOTE_ITEM_COUNT_MIN_ONE), HttpStatus.BAD_REQUEST);
        }

        // 투표 항목 다섯 개 초과 시
        if (voteCreateRequest.getVoteItemCreateRequestList().size() > 5) {
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.INVALID_VOTE_ITEM_COUNT_MAX_FIVE), HttpStatus.BAD_REQUEST);
        }

        return voteService.insertVote(voteCreateRequest);
    }

    /**
     * 투표 및 항목 업데이트
     * @param voteUpdateRequest VoteUpdateRequest
     * @return ResponseEntity<CommonResponse>
     */
    @PatchMapping
    public ResponseEntity<CommonResponse> updateVoteAndVoteItem(@RequestBody VoteUpdateRequest voteUpdateRequest) {
        return voteService.updateVoteItem(voteUpdateRequest);
    }

    /**
     * 투표 및 투표 항목 삭제
     * @param voteDeleteRequest DeleteVoteItemRequest
     * @return ResponseEntity<CommonResponse>
     */
    @DeleteMapping
    public ResponseEntity<CommonResponse> deleteVoteAndVoteItem(@RequestBody VoteDeleteRequest voteDeleteRequest) {
        return voteService.deleteVoteITem(voteDeleteRequest);
    }

    /**
     * 투표 리스트 조회
     * @param postIdx Long
     * @return ResponseEntity<CommonResponse>
     */
    @GetMapping("/{postIdx}")
    public ResponseEntity<CommonResponse> selectAllVotes(@PathVariable("postIdx") Long postIdx) {
        return voteService.selectAllVotes(postIdx);
    }

    /**
     * 투표 단건 조회
     * @param voteIdx Long
     * @return ResponseEntity<CommonResponse>
     */
    @GetMapping("/{voteIdx}")
    public ResponseEntity<CommonResponse> selectVote(@PathVariable("voteIdx") Long voteIdx) {
        return voteService.selectVote(voteIdx);
    }

    /**
     * [STUDY] 투표 즉시 마감
     * @param voteIdx Long
     * @return ResponseEntity<CommonResponse>
     */
    @PatchMapping("/{voteIdx}/end")
    public ResponseEntity<CommonResponse> endVoteItem(@PathVariable("voteIdx") Long voteIdx) {
        return voteService.endVote(voteIdx);
    }

}
