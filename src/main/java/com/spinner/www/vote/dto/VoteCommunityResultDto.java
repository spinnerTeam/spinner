package com.spinner.www.vote.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VoteCommunityResultDto {
    private Long voteItemIdx;
    private String voteItemName;  // 항목 이름
    private long voteCount;      // 항목별 투표 수
    private double votePercentage;   // 항목별 투표 비율

    @QueryProjection
    public VoteCommunityResultDto(Long voteItemIdx, String voteItemName, Long voteCount, Double votePercentage) {
        this.voteItemIdx = voteItemIdx;
        this.voteItemName = voteItemName;
        this.voteCount = voteCount;
        this.votePercentage = votePercentage;
    }
}
