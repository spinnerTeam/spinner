package com.spinner.www.report.io;

import com.spinner.www.common.io.BaseResponse;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ReportResponse extends BaseResponse {
    private Long reportIdx;
    private String reportTypeContent;
    private Long boardIdx;
    private String boardTitle;
    private String boardContent;
    private String reportMember;
}
