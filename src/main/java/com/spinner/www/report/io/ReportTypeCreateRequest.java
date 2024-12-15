package com.spinner.www.report.io;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Builder
public class ReportTypeCreateRequest {
    @NotBlank(message = "신고 타입 내용은 필수 입력 조건입니다.")
    private String reportTypeContent;
}