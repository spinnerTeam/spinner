package com.spinner.www.vote.service;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.vote.io.DeleteVoteItemRequest;
import com.spinner.www.vote.io.UpdateVoteItemRequest;
import com.spinner.www.vote.io.VoteCreateRequest;
import com.spinner.www.vote.io.VoteUpdateRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface VoteService {

    ResponseEntity<CommonResponse> insertVote(VoteCreateRequest voteCreateRequest);

    ResponseEntity<CommonResponse> selectAllVotes(Long postIdx);

    ResponseEntity<CommonResponse> selectVote(Long voteIdx);

    ResponseEntity<CommonResponse> updateVoteItem(VoteUpdateRequest voteUpdateRequest);

    ResponseEntity<CommonResponse> deleteVoteITem(List<DeleteVoteItemRequest> voteItemDeleteList);

    ResponseEntity<CommonResponse> endVote(Long voteIdx);
}
