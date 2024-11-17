package com.spinner.www.report.dto;

import com.spinner.www.member.entity.BaseEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReportTypeDto extends BaseEntity {
    private Long id;
    private String reportTypeContent;
}