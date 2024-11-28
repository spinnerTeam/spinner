package com.spinner.www.vote.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class VoteCreateDto {
    private Long postIdx;
    private String voteName;
    private LocalDateTime startDatetime;
    private LocalDateTime endDatetime;
}
