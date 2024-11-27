package com.spinner.www.vote.io;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VoteItemUpdateRequest {
    private String voteItemName;
    private String voteItemStatus;
}
