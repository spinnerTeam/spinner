package com.spinner.www.vote.io;

import com.spinner.www.vote.entity.VoteStatus;
import com.spinner.www.vote.entity.VoteType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class VoteSelectResponse {
    private Long voteIdx;
    private String voteName;
    private VoteType voteType;
    private VoteStatus voteStatus;
    private LocalDateTime startDatetime;
    private LocalDateTime endDatetime;
    private List<VoteItemSelectResponse> voteItemSelectResponseList;
}
