package com.spinner.www.report.io;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportTypeCreateRequest {
    private String reportTypeContent;
}