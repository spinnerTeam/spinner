package com.spinner.www.report.service;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.constants.CommonResultCode;
import com.spinner.www.member.dto.SessionInfo;
import com.spinner.www.member.entity.Member;
import com.spinner.www.member.repository.MemberRepo;
import com.spinner.www.board.entity.Board;
import com.spinner.www.board.repository.BoardRepo;
import com.spinner.www.report.dto.ReportCreateDto;
import com.spinner.www.report.dto.ReportGetDto;
import com.spinner.www.report.entity.Report;
import com.spinner.www.report.entity.ReportType;
import com.spinner.www.report.io.ReportCreateRequest;
import com.spinner.www.report.io.ReportResponse;
import com.spinner.www.report.mapper.ReportMapper;
import com.spinner.www.report.repository.ReportRepo;
import com.spinner.www.report.repository.ReportTypeRepo;
import com.spinner.www.util.ResponseVOUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.spinner.www.report.entity.Report.create;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportServiceImpl implements ReportService {

    private final ReportRepo reportRepo;
    private final SessionInfo sessionInfo;
    private final ReportMapper reportMapper;
    private final ReportTypeRepo reportTypeRepo;
    private final BoardRepo boardRepo;
    private final MemberRepo memberRepo;

    /**
     * 신고 추가
     * @param reportCreateRequest ReportCreateRequest
     * @return ResponseEntity<CommonResponse>
     */
    @Override
    @Transactional
    public ResponseEntity<CommonResponse> insertReport(ReportCreateRequest reportCreateRequest) {

        ReportCreateDto reportInsertDto = reportCreatRequestToReportToDto(reportCreateRequest);
        ReportType reportType = reportTypeRepo.findById(reportInsertDto.getReportTypeIdx())
                .orElseThrow(() -> new RuntimeException("ReportType를 찾을 수 없습니다."));
        Board reportBoard = boardRepo.findById(reportInsertDto.getBoardIdx())
                .orElseThrow(() -> new RuntimeException("reportBoard not found"));
        Member reportMember = memberRepo.findById(sessionInfo.getMemberIdx())
                .orElseThrow(() -> new RuntimeException("reportMember not found"));
        Report report = create(reportType, reportBoard, reportMember);

        // 신고 데이터 중 포스트와 멤버가 동일한 데이터가 있을 경우 중복 처리
        if (reportRepo.findByBoardAndMember(report.getBoard(), report.getMember()).isPresent()) {
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.DUPLICATE_REPORT), HttpStatus.OK);
        }

        reportRepo.save(report);

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(report.getId()), HttpStatus.OK);
    }

    /**
     * 신고 리스트 조회
     * 어드민에서 이용
     * @return ResponseEntity<CommonResponse>
     */
    @Override
    public ResponseEntity<CommonResponse> selectReportList() {
        List<Report> reportList = reportRepo.findAll();
        List<ReportGetDto> reportGetListDto = reportMapper.reportListToReportDtoList(reportList);
        List<ReportResponse> reportResponseList = reportGetListDtoToReportListResponse(reportGetListDto);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(reportResponseList), HttpStatus.OK);
    }

    /**
     * 신고 단건 조회
     * 어드민에서 이용
     * @param reportIdx Long
     * @return ResponseEntity<CommonResponse>
     */
    @Override
    public ResponseEntity<CommonResponse> selectReport(Long reportIdx) {

        Report report = reportRepo.findById(reportIdx).orElseThrow(null);

        if (report == null) {
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.DATA_NOT_FOUND), HttpStatus.NOT_FOUND);
        }

        ReportGetDto reportGetDto = reportMapper.reportToReportGetDto(report);
        ReportResponse reportResponse = reportGetDtoToReportResponse(reportGetDto);

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(reportResponse), HttpStatus.OK);
    }

    /**
     * ReportCreateRequest -> ReportDto 변환
     * @param reportCreateRequest ReportCreateRequest
     * @return ReportDto
     */
    @Override
    public ReportCreateDto reportCreatRequestToReportToDto(ReportCreateRequest reportCreateRequest) {
        return ReportCreateDto.builder()
                .reportTypeIdx(reportCreateRequest.getReportTypeIdx())
                .boardIdx(reportCreateRequest.getBoardIdx())
                .build();
    }

    /**
     * List<ReportGetDto> -> List<ReportResponse> 변환
     * @param reportGetDtoList List<ReportGetDto>
     * @return List<ReportResponse>
     */
    @Override
    public List<ReportResponse> reportGetListDtoToReportListResponse(List<ReportGetDto> reportGetDtoList) {
        List<ReportResponse> reportResponseList = new ArrayList<>();
        for (ReportGetDto reportGetDto : reportGetDtoList) {
            ReportResponse reportResponse = ReportResponse.builder()
                    .reportIdx(reportGetDto.getId())
                    .reportTypeContent(reportGetDto.getReportType().getReportTypeContent())
                    .boardIdx(reportGetDto.getBoard().getBoardIdx())
                    .boardTitle(reportGetDto.getBoard().getBoardTitle())
                    .boardContent(reportGetDto.getBoard().getBoardContent())
                    .reportMember(reportGetDto.getMember().getMemberEmail())
                    .createdAt(reportGetDto.getCreatedAt())
                    .createdDate(reportGetDto.getCreatedDate())
                    .modifiedAt(reportGetDto.getModifiedAt())
                    .modifiedDate(reportGetDto.getModifiedDate())
                    .build();
            reportResponseList.add(reportResponse);
        }

        return reportResponseList;
    }

    /**
     * ReportGetDto -> ReportResponse 변환
     * @param reportGetDto ReportGetDto
     * @return ReportResponse
     */
    @Override
    public ReportResponse reportGetDtoToReportResponse(ReportGetDto reportGetDto) {
        return ReportResponse.builder()
                .reportIdx(reportGetDto.getId())
                .reportTypeContent(reportGetDto.getReportType().getReportTypeContent())
                .boardIdx(reportGetDto.getBoard().getBoardIdx())
                .boardTitle(reportGetDto.getBoard().getBoardTitle())
                .boardContent(reportGetDto.getBoard().getBoardContent())
                .reportMember(reportGetDto.getMember().getMemberEmail())
                .createdAt(reportGetDto.getCreatedAt())
                .createdDate(reportGetDto.getCreatedDate())
                .modifiedAt(reportGetDto.getModifiedAt())
                .modifiedDate(reportGetDto.getModifiedDate())
                .build();
    }

}
