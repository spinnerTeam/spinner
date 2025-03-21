package com.spinner.www.report.controller;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.report.io.ReportTypeCreateRequest;
import com.spinner.www.report.service.ReportTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 신고 유형 관리 컨트롤러
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
     * @param reportTypeCreateRequest ReportTypeRequest
     * @return ResponseEntity<CommonResponse>
     */
    @PostMapping
    public ResponseEntity<CommonResponse> insertReportType(@RequestBody ReportTypeCreateRequest reportTypeCreateRequest) {
        return reportTypeService.insertReportType(reportTypeCreateRequest);
    }

    /**
     * 신고 유형 리스트 조회
     * @return ResponseEntity<CommonResponse>
     */
    @GetMapping
    public ResponseEntity<CommonResponse> selectReportTypeList() {
        return reportTypeService.selectReportTypeList();
    }

    /**
     * 신고 유형 단건 조회
     * @param id Long
     * @return ResponseEntity<CommonResponse>
     */
    @GetMapping("/{reportTypeIdx}")
    public ResponseEntity<CommonResponse> selectReportType(@PathVariable("reportTypeIdx") Long id) {
        return reportTypeService.selectReportType(id);
    }
}