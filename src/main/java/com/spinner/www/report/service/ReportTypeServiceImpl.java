package com.spinner.www.report.service;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.constants.CommonResultCode;
import com.spinner.www.report.io.ReportTypeCreateRequest;
import com.spinner.www.report.mapper.ReportTypeMapper;
import com.spinner.www.report.dto.ReportTypeDto;
import com.spinner.www.report.entity.ReportType;
import com.spinner.www.report.io.ReportTypeResponse;
import com.spinner.www.report.repository.ReportTypeRepo;
import com.spinner.www.util.ResponseVOUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReportTypeServiceImpl implements ReportTypeService {

    private final ReportTypeRepo reportTypeRepo;
    private final ReportTypeMapper reportTypeMapper;

    /**
     * 신고 유형 추가
     * @param reportTypeCreateRequest ResponseEntity<CommonResponse>
     * @return ResponseEntity<CommonResponse>
     */
    @Override
    public ResponseEntity<CommonResponse> insertReportType(ReportTypeCreateRequest reportTypeCreateRequest) {

        ReportTypeDto reportTypeDto = reportTypeCreateRequestToReportTypeDto(reportTypeCreateRequest);
        ReportType reportType = ReportType.create(reportTypeDto);
        reportTypeRepo.save(reportType);

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(reportType.getReportTypeIdx()), HttpStatus.OK);
    }

    /**
     * 신고 유형 리스트 조회
     * @return ResponseEntity<CommonResponse>
     */
    @Override
    public ResponseEntity<CommonResponse> selectReportTypeList() {
        List<ReportType> reportTypeList = reportTypeRepo.findAll();
        List<ReportTypeDto> reportTypeDtoList = reportTypeMapper.ReportTypeListToReportDtoTypeList(reportTypeList);
        List<ReportTypeResponse> reportTypeResponseList = reportTypeListDtoToReportTypeListResponse(reportTypeDtoList);

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(reportTypeResponseList), HttpStatus.OK);
    }

    /**
     * 신고 유형 조회
     * @param id Long
     * @return ResponseEntity<CommonResponse>
     */
    @Override
    public ResponseEntity<CommonResponse> selectReportType(Long id) {

        ReportType reportType = reportTypeRepo.findById(id).orElse(null);

        // 신고 단건을 찾을 수 없는 경우
        if (reportType == null) {
           return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.DATA_NOT_FOUND), HttpStatus.NOT_FOUND);
        }

        ReportTypeDto reportTypeDto = reportTypeMapper.ReportTypeToReportTypeDto(reportType);
        ReportTypeResponse reportTypeResponse = reportTypeDtoToReportTypeResponse(reportTypeDto);

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(reportTypeResponse), HttpStatus.OK);
    }

    /**
     * ReportTypeCreateRequest -> ReportTypeDto 변환
     * @param reportTypeCreateRequest ReportTypeCreateRequest
     * @return ReportTypeDto
     */
    @Override
    public ReportTypeDto reportTypeCreateRequestToReportTypeDto(ReportTypeCreateRequest reportTypeCreateRequest) {
        return ReportTypeDto.builder()
                .reportTypeContent(reportTypeCreateRequest.getReportTypeContent())
                .build();
    }

    /**
     * List<ReportTypeDto> -> List<ReportTypeResponse> 변환
     * @param reportTypeDto List<ReportTypeDto>
     * @return List<ReportTypeResponse>
     */
    @Override
    public List<ReportTypeResponse> reportTypeListDtoToReportTypeListResponse(List<ReportTypeDto> reportTypeDto) {

        List<ReportTypeResponse> reportTypeResponseList = new ArrayList<>();

        for (ReportTypeDto reportTypeDtoOne : reportTypeDto) {
            ReportTypeResponse responseOne = reportTypeDtoToReportTypeResponse(reportTypeDtoOne);
            reportTypeResponseList.add(responseOne);
        }

        return reportTypeResponseList;
    }

    /**
     * ReportTypeDto -> ReportTypeResponse 변환
     * @param reportTypeDto ReportTypeDto
     * @return ReportTypeResponse
     */
    @Override
    public ReportTypeResponse reportTypeDtoToReportTypeResponse(ReportTypeDto reportTypeDto) {
        return ReportTypeResponse.builder()
                .reportTypeIdx(reportTypeDto.getId())
                .reportTypeContent(reportTypeDto.getReportTypeContent())
                .createdAt(reportTypeDto.getCreatedAt())
                .createdDate(reportTypeDto.getCreatedDate())
                .modifiedAt(reportTypeDto.getModifiedAt())
                .modifiedDate(reportTypeDto.getModifiedDate())
                .build();
    }

}