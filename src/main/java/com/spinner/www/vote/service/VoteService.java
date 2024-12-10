package com.spinner.www.vote.service;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.vote.io.*;
import org.springframework.http.ResponseEntity;

public interface VoteService {

    ResponseEntity<CommonResponse> insertVote(VoteCreateRequest voteCreateRequest);

    ResponseEntity<CommonResponse> selectAllVotes(Long postIdx);

    ResponseEntity<CommonResponse> selectVote(Long voteIdx);

    ResponseEntity<CommonResponse> updateVoteItem(VoteUpdateRequest voteUpdateRequest);

    ResponseEntity<CommonResponse> deleteVoteITem(VoteDeleteRequest voteDeleteRequest);

    ResponseEntity<CommonResponse> endVote(Long voteIdx);

    ResponseEntity<CommonResponse> selectVoteItem(VoteParticipateUserRequest voteParticipateUserRequest);
}
