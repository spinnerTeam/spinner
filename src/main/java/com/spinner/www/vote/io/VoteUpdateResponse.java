package com.spinner.www.vote.io;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Builder
public class VoteUpdateResponse {
    private Long voteId;
    private List<Long> voteItemId;
}
