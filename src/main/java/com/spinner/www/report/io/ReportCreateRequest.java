package com.spinner.www.report.io;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReportCreateRequest {
    private Long reportTypeIdx;
    private Long postIdx;
}
