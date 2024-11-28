package com.spinner.www.vote.controller;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.constants.CommonResultCode;
import com.spinner.www.util.ResponseVOUtils;
import com.spinner.www.vote.io.DeleteVoteItemRequest;
import com.spinner.www.vote.io.UpdateVoteItemRequest;
import com.spinner.www.vote.io.VoteCreateRequest;
import com.spinner.www.vote.service.VoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class VoteController {

    private final VoteService voteService;

    /**
     * [memo]
     * - 투표 항목은 수정, 추가, 삭제될 수 있다.
     * (1) 투표 항목은 최대 다섯 개까지만 추가 가능하다.
     * 투표 항목을 생성할 때, 복수 선택 여부를 확인할 수 있다.
     * 투표 완료 후 참여자 인원 수를 확인할 수 있다. 이때 닉네임은 익명이다.
     * 투표 완료 후 투표수를 확인할 수 있다. (몇 퍼센트의 비율인지 또한 확인할 수 있다.)
     */

    /**
     * 투표 추가
     * @param voteCreateRequest VoteCreateRequest
     * @return ResponseEntity<CommonResponse>
     */
    @PostMapping
    public ResponseEntity<CommonResponse> insertVote(@RequestBody VoteCreateRequest voteCreateRequest) {

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
     * 투표 항목 업데이트
     * @param voteItemUpdateList List<UpdateVoteItem>
     * @return ResponseEntity<CommonResponse>
     */
    @PatchMapping("/voteItem")
    public ResponseEntity<CommonResponse> updateVoteItem(@RequestBody List<UpdateVoteItemRequest> voteItemUpdateList, @PathVariable String voteIdx) {
        return voteService.updateVoteItem(voteItemUpdateList);
    }

    /**
     * 투표 항목 삭제
     * @param voteItemDeleteList List<DeleteVoteItem>
     * @return ResponseEntity<CommonResponse>
     */
    @DeleteMapping("/voteItem")
    public ResponseEntity<CommonResponse> deleteVoteItem(@RequestBody List<DeleteVoteItemRequest> voteItemDeleteList) {
        return voteService.deleteVoteITem(voteItemDeleteList);
    }

}
