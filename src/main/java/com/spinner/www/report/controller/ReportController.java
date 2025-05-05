package com.spinner.www.report.controller;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.report.io.ReportBoardCreateRequest;
import com.spinner.www.report.io.ReportMemberCreateRequest;
import com.spinner.www.report.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
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
     * 게시글 신고 삽입
     * @param reportBoardCreateRequest ReportBoardCreateRequest
     * @return ResponseEntity<CommonResponse>
     */
    @Operation(
            summary = "게시글 신고 등록",
            description =
                    "게시글을 신고합니다.<br/>" +
                            "<strong>Request Body</strong><br/>" +
                            "• reportTypeIdx   : Long    (신고 종류 IDX)<br/>" +
                            "• boardIdx        : Long    (신고 대상 게시글 IDX)<br/>",
            responses = {
                    @ApiResponse(content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "20000", description = "요청 성공"),
                    @ApiResponse(responseCode = "50003", description = "ReportType를 찾을 수 없습니다."),
                    @ApiResponse(responseCode = "40101", description = "권한이 없습니다."),
                    @ApiResponse(responseCode = "40006", description = "존재하지 않는 게시글입니다.")
            }
    )
    @PostMapping("/board")
    public ResponseEntity<CommonResponse> insertReport(@Valid @RequestBody ReportBoardCreateRequest reportBoardCreateRequest) {
        return reportService.insertReport(reportBoardCreateRequest);
    }

    /**
     * 유저 신고 삽입
     * @param reportMemberCreateRequest ReportMemeberCreateRequest
     * @return ResponseEntity<CommonResponse>
     */
    @Operation(
            summary = "회원(유저) 신고 등록",
            description =
                    "사용자를 신고합니다.<br/>" +
                            "<strong>Request Body</strong><br/>" +
                            "• reportTypeIdx   : Long    (신고 종류 IDX)<br/>" +
                            "• memberIdx       : Long    (신고 대상 회원 ID)<br/>",
            responses = {
                    @ApiResponse(content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "20000", description = "요청 성공"),
                    @ApiResponse(responseCode = "50003", description = "ReportType를 찾을 수 없습니다."),
                    @ApiResponse(responseCode = "40101", description = "권한이 없습니다."),
                    @ApiResponse(responseCode = "40008", description = "존재하지 않는 사용자입니다.")
            }
    )
    @PostMapping("/member")
    public ResponseEntity<CommonResponse> insertReport(@Valid @RequestBody ReportMemberCreateRequest reportMemberCreateRequest) {
        return reportService.insertReport(reportMemberCreateRequest);
    }

    /**
     * 신고 리스트 조회
     * @return ResponseEntity<CommonResponse>
     */
    @Operation(
            summary = "신고 내역 목록 조회",
            description =
                    "모든 신고 내역을 조회합니다.<br/>",
            responses = {
                    @ApiResponse(content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "20000", description = "조회 성공"),
                    @ApiResponse(responseCode = "40101", description = "권한이 없습니다.")
            }
    )
    @GetMapping("/board")
    public ResponseEntity<CommonResponse> selectReportList() {
        return reportService.selectReportList();
    }

    /**
     * 신고 단건 조회
     * @param reportIdx Long
     * @return ResponseEntity<CommonResponse>
     */
    @Operation(
            summary = "신고 상세 조회",
            description =
                    "단일 신고 내역을 조회합니다.<br/>" +
                            "<strong>Path Variable</strong><br/>" +
                            "• reportIdx : Long (신고 ID)<br/>",
            responses = {
                    @ApiResponse(content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "20000", description = "요청 성공"),
                    @ApiResponse(responseCode = "40101", description = "권한이 없습니다."),
                    @ApiResponse(responseCode = "50001", description = "데이터를 찾을 수 없음")
            }
    )
    @GetMapping("/board/{reportIdx}")
    public ResponseEntity<CommonResponse> selectReport(@PathVariable("reportIdx") Long reportIdx) {
        return reportService.selectReport(reportIdx);
    }

}
