package com.spinner.www.vote.controller;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.vote.io.VoteCreateRequest;
import com.spinner.www.vote.service.VoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping
    public ResponseEntity<CommonResponse> insertVote(@RequestBody VoteCreateRequest voteCreateRequest) {
        return voteService.insertVote(voteCreateRequest);
    }

    @GetMapping("/{postIdx}")
    public ResponseEntity<CommonResponse> selectAllVotes(@PathVariable("postIdx") Long postIdx) {
        return voteService.selectAllVotes(postIdx);
    }

    @GetMapping("/{voteIdx}")
    public ResponseEntity<CommonResponse> selectVote(@PathVariable("voteIdx") Long voteIdx) {
        return voteService.selectVote(voteIdx);
    }

    @PostMapping("/{voteIdx}/voteItem")
    public ResponseEntity<CommonResponse> updateVoteItem(@PathVariable("voteIdx") Long voteIdx,
                                                         @RequestBody List<UpdateVoteItem> voteItemCreateList) {
        return voteService.updateVoteItem(voteItemCreateList);
    }

}
