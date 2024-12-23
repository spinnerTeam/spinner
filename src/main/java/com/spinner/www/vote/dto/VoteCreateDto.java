package com.spinner.www.vote.dto;

import com.spinner.www.vote.entity.VoteStatus;
import com.spinner.www.vote.entity.VoteType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class VoteCreateDto {
    private Long boardIdx;
    private String voteName;
    private VoteStatus voteStatus;
    private VoteType voteType;
    private LocalDateTime voteStartDatetime;
    private LocalDateTime voteEndDatetime;
}
