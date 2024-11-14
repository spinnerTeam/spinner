package com.spinner.www.report.controller;

import com.spinner.www.common.CommonResponse;
import com.spinner.www.report.io.ReportTypeRequest;
import com.spinner.www.report.service.ReportTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 리포트 유형 관리 컨트롤러
 * 어드민에서 필요
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/report/type")
public class ReportTypeController {

    private final ReportTypeService reportTypeService;

    /**
     * 신고 유형 추가
     * @param reportTypeRequest ReportTypeRequest
     * @return ResponseEntity<CommonResponse>
     */
    @PostMapping("/insert")
    public ResponseEntity<CommonResponse> insertReportType(@RequestBody ReportTypeRequest reportTypeRequest) {
        return reportTypeService.insertReportType(reportTypeRequest);
    }

    /**
     * 신고 유형 조회
     * @param id Long
     * @return ResponseEntity<CommonResponse>
     */
    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse> selectReportType(@PathVariable Long id) {
        return reportTypeService.selectReportType(id);
    }
}