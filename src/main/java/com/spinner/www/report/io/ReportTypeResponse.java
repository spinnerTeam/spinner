package com.spinner.www.report.io;

import com.spinner.www.common.io.BaseResponse;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ReportTypeResponse extends BaseResponse {
    private Long reportTypeIdx;
    private String reportTypeContent;
}