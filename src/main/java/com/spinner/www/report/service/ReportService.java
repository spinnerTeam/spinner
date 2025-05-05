package com.spinner.www.report.service;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.report.dto.ReportCreateDto;
import com.spinner.www.report.dto.ReportGetDto;
import com.spinner.www.report.io.ReportBoardCreateRequest;
import com.spinner.www.report.io.ReportMemberCreateRequest;
import com.spinner.www.report.io.ReportResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ReportService {
    ResponseEntity<CommonResponse> insertReport(ReportBoardCreateRequest reportBoardCreateRequest);
    ResponseEntity<CommonResponse> insertReport(ReportMemberCreateRequest reportMemberCreateRequest);
    ResponseEntity<CommonResponse> selectReportList();
    ResponseEntity<CommonResponse> selectReport(Long reportIdx);
    ReportCreateDto reportCreatRequestToReportToDto(ReportBoardCreateRequest reportBoardCreateRequest);
    ReportCreateDto reportCreatRequestToReportToDto(ReportMemberCreateRequest reportMemberCreateRequest);
    List<ReportResponse> reportGetListDtoToReportListResponse(List<ReportGetDto> reportGetDtoList);
    ReportResponse reportGetDtoToReportResponse(ReportGetDto reportGetDto);
}
