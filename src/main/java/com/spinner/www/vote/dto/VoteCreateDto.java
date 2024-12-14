package com.spinner.www.vote.dto;

import com.spinner.www.vote.entity.VoteStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class VoteCreateDto {
    private Long boardIdx;
    private String voteName;
    private VoteStatus voteStatus;
    private LocalDateTime voteStartDatetime;
    private LocalDateTime voteEndDatetime;
}
