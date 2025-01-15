package com.spinner.www.vote.io;

import com.spinner.www.vote.dto.VoteCommunityResultDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class VoteResultsCommunityResponse {
    private long totalVotes;                 // 총 투표 수
    private long voteIdx;
    private List<VoteCommunityResultDto> voteResult;    // 투표 항목별 결과
}
