package com.spinner.www.report.service;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.report.dto.ReportTypeDto;
import com.spinner.www.report.io.ReportTypeCreateRequest;
import com.spinner.www.report.io.ReportTypeResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ReportTypeService {
    ResponseEntity<CommonResponse> insertReportType(ReportTypeCreateRequest reportTypeCreateRequest);
    ResponseEntity<CommonResponse> selectReportTypeList();
    ResponseEntity<CommonResponse> selectReportType(Long id);
    ReportTypeDto reportTypeCreateRequestToReportTypeDto(ReportTypeCreateRequest reportTypeCreateRequest);
    List<ReportTypeResponse> reportTypeListDtoToReportTypeListResponse(List<ReportTypeDto> reportTypeDto);
    ReportTypeResponse reportTypeDtoToReportTypeResponse(ReportTypeDto reportTypeDto);
}
