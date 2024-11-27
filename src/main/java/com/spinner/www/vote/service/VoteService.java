package com.spinner.www.vote.service;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.vote.io.VoteCreateRequest;
import org.springframework.http.ResponseEntity;

public interface VoteService {

    ResponseEntity<CommonResponse> insertVote(VoteCreateRequest voteCreateRequest);
}
