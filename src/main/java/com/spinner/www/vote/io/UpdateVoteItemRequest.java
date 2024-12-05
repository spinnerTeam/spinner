package com.spinner.www.vote.io;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateVoteItemRequest {
    private Long voteItemId;
}
