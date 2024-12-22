package com.spinner.www.vote.io;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VoteItemSelectResponse {
    private Long voteItemIdx;
    private String voteItemName;
}
