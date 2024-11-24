package com.spinner.www.report.dto;

import com.spinner.www.common.dto.BaseDto;
import com.spinner.www.common.entity.BaseEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReportTypeDto extends BaseDto {
    private Long id;
    private String reportTypeContent;
}