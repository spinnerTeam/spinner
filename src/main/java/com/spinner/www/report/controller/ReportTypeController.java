package com.spinner.www.report.controller;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.report.io.ReportTypeCreateRequest;
import com.spinner.www.report.service.ReportTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@RequestMapping("/reportType")
public class ReportTypeController {

    private final ReportTypeService reportTypeService;

    /**
     * 신고 유형 추가
     * @param reportTypeCreateRequest ReportTypeRequest
     * @return ResponseEntity<CommonResponse>
     */
    @Operation(
            summary = "신고 유형 등록",
            description =
                    "새로운 신고 유형을 추가합니다.<br/>" +
                            "<strong>Request Body</strong><br/>" +
                            "• reportTypeContent : String (신고 유형 내용)<br/>",
            responses = {
                    @ApiResponse(content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "20000", description = "요청 성공"),
                    @ApiResponse(responseCode = "40001", description = "잘못된 요청입니다."),
                    @ApiResponse(responseCode = "40900", description = "이미 존재하는 신고 유형입니다.")
            }
    )
    @PostMapping
    public ResponseEntity<CommonResponse> insertReportType(@RequestBody ReportTypeCreateRequest reportTypeCreateRequest) {
        return reportTypeService.insertReportType(reportTypeCreateRequest);
    }

    /**
     * 신고 유형 리스트 조회
     * @return ResponseEntity<CommonResponse>
     */
    @Operation(
            summary = "신고 유형 목록 조회",
            description =
                    "등록된 모든 신고 유형을 조회합니다.<br/>",
            responses = {
                    @ApiResponse(content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "20000", description = "요청 성공"),
                    @ApiResponse(responseCode = "40101", description = "권한이 없습니다.")
            }
    )
    @GetMapping
    public ResponseEntity<CommonResponse> selectReportTypeList() {
        return reportTypeService.selectReportTypeList();
    }

    /**
     * 신고 유형 단건 조회
     * @param id Long
     * @return ResponseEntity<CommonResponse>
     */
    @Operation(
            summary = "신고 유형 상세 조회",
            description =
                    "단일 신고 유형을 조회합니다.<br/>" +
                            "<strong>Path Variable</strong><br/>" +
                            "• reportTypeIdx : Long (신고 유형 IDX)<br/>",
            responses = {
                    @ApiResponse(content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "20000", description = "요청 성공"),
                    @ApiResponse(responseCode = "40101", description = "권한이 없습니다."),
                    @ApiResponse(responseCode = "50001", description = "데이터를 찾을 수 없음")
            }
    )
    @GetMapping("/{reportTypeIdx}")
    public ResponseEntity<CommonResponse> selectReportType(@PathVariable("reportTypeIdx") Long id) {
        return reportTypeService.selectReportType(id);
    }
}