package com.spinner.www.vote.io;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class VoteUpdateRequest {

    private Long voteIdx;
    private String voteName;
    private List<VoteItemUpdateRequest> voteItemUpdateRequestList;
}
