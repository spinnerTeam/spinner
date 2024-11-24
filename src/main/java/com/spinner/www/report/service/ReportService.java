package com.spinner.www.report.service;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.report.dto.ReportCreateDto;
import com.spinner.www.report.dto.ReportGetDto;
import com.spinner.www.report.io.ReportCreateRequest;
import com.spinner.www.report.io.ReportResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ReportService {
    ResponseEntity<CommonResponse> insertReport(ReportCreateRequest reportCreateRequest);
    ResponseEntity<CommonResponse> selectReportList();
    ResponseEntity<CommonResponse> selectReport(Long reportIdx);
    ReportCreateDto reportCreatRequestToReportToDto(ReportCreateRequest reportCreateRequest);
    List<ReportResponse> reportGetListDtoToReportListResponse(List<ReportGetDto> reportGetDtoList);
    ReportResponse reportGetDtoToReportResponse(ReportGetDto reportGetDto);
}
