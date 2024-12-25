package com.spinner.www.report.dto;

import com.spinner.www.common.dto.BaseDto;
import com.spinner.www.member.entity.Member;
import com.spinner.www.board.entity.Board;
import com.spinner.www.report.entity.ReportType;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ReportGetDto extends BaseDto {
    private Long id;
    private ReportType reportType;
    private Board board;
    private Member member;
}
