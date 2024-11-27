package com.spinner.www.vote.io;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class VoteCreateRequest {

    private Long postIdx;

    private String voteName;

    private List<VoteItemCreateRequest> voteItemCreateRequestList;

    private LocalDateTime startDatetime;

    private LocalDateTime endDatetime;
}
