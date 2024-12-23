package com.spinner.www.vote.io;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VoteItemDeleteRequest {
    private Long voteItemIdx;
}
