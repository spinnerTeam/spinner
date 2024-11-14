package com.spinner.www.report.io;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReportTypeResponse {
    private Long id;
    private String reportTypeContent;
}