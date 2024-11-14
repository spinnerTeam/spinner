package com.spinner.www.report.service;

import com.spinner.www.common.CommonResponse;
import com.spinner.www.report.io.ReportTypeRequest;
import org.springframework.http.ResponseEntity;

public interface ReportTypeService {

    ResponseEntity<CommonResponse> insertReportType(ReportTypeRequest reportTypeRequest);
    ResponseEntity<CommonResponse> selectReportType(Long id);
}
