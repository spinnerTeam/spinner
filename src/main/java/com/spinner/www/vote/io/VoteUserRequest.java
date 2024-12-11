package com.spinner.www.vote.io;

import com.spinner.www.vote.entity.VoteStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class VoteUserRequest {
    private Long voteIdx;
    private Long memberIdx;
    private List<VoteItemUserRequest> voteItemIdxList;
    private VoteStatus voteStatus;
}
