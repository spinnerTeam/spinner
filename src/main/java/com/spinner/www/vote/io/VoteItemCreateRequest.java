package com.spinner.www.vote.io;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VoteItemCreateRequest {
    private String voteItemName;
}
