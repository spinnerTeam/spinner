package com.spinner.www.report.controller;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.report.io.ReportCreateRequest;
import com.spinner.www.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 유저 신고 컨트롤러
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    /**
     * 신고 삽입
     * @param reportCreateRequest ReportCreateRequest
     * @return ResponseEntity<CommonResponse>
     */
    @PostMapping
    public ResponseEntity<CommonResponse> insertReport(@RequestBody ReportCreateRequest reportCreateRequest) {
        return reportService.insertReport(reportCreateRequest);
    }

    /**
     * 신고 리스트 조회
     * @return ResponseEntity<CommonResponse>
     */
    @GetMapping
    public ResponseEntity<CommonResponse> selectReportList() {
        return reportService.selectReportList();
    }

    /**
     * 신고 단건 조회
     * @param reportIdx Long
     * @return ResponseEntity<CommonResponse>
     */
    @GetMapping("/{reportIdx}")
    public ResponseEntity<CommonResponse> selectReport(@PathVariable("reportIdx") Long reportIdx) {
        return reportService.selectReport(reportIdx);
    }

}
