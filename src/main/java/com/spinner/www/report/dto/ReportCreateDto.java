package com.spinner.www.report.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ReportCreateDto {
    private Long reportTypeIdx;
    private Long reportedBoardIdx;
    private Long reportedMemberIdx;
}
