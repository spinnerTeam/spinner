package com.spinner.www.report.service;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.report.dto.ReportTypeDto;
import com.spinner.www.report.io.ReportTypeCreateRequest;
import com.spinner.www.report.io.ReportTypeResponse;
import org.springframework.http.ResponseEntity;

public interface ReportTypeService {

    ResponseEntity<CommonResponse> insertReportType(ReportTypeCreateRequest reportTypeCreateRequest);
    ResponseEntity<CommonResponse> selectReportType(Long id);
    ReportTypeDto reportTypeCreateRequestToReportTypeDto(ReportTypeCreateRequest reportTypeCreateRequest);
    ReportTypeResponse reportTypeDtoToReportTypeResponse(ReportTypeDto reportTypeDto);
}
