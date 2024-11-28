package com.spinner.www.vote.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VoteItemCreateDto {
    private Long voteIdx;
    private String voteItemName;
    private String voteItemStatus;
}
