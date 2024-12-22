package com.spinner.www.vote.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
public class VoteItemDto {
    private Long voteItemIdx;
    private String voteItemName;
    private String voteItemStatus;
}
