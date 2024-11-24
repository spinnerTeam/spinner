package com.spinner.www.report.dto;

import com.spinner.www.common.dto.BaseDto;
import com.spinner.www.common.entity.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ReportTypeDto extends BaseDto {
    private Long id;
    private String reportTypeContent;
}