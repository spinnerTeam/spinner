package com.spinner.www.vote.io;

import com.spinner.www.vote.entity.VoteStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class VoteParticipateUserRequest {
    private Long voteIdx;
    private List<VoteItemParticipateUserRequest> voteItemParticipateUserRequestList;
    private VoteStatus voteStatus;
}
