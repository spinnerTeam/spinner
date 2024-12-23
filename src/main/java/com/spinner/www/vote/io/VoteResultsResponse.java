package com.spinner.www.vote.io;

import com.spinner.www.vote.dto.VoteResultDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class VoteResultsResponse {
    private long totalVotes;                 // 총 투표 수
    private List<VoteResultDto> voteResult;    // 투표 항목별 결과
}
