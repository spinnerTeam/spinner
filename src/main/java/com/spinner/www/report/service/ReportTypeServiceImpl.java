package com.spinner.www.report.service;

import com.spinner.www.common.CommonResponse;
import com.spinner.www.constants.CommonResultCode;
import com.spinner.www.report.Mapper.ReportTypeMapper;
import com.spinner.www.report.dto.ReportTypeDto;
import com.spinner.www.report.entity.ReportType;
import com.spinner.www.report.io.ReportTypeRequest;
import com.spinner.www.report.io.ReportTypeResponse;
import com.spinner.www.report.repository.ReportTypeRepository;
import com.spinner.www.util.ResponseVOUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReportTypeServiceImpl implements ReportTypeService {

    private final ReportTypeRepository reportTypeRepository;
    private final ReportTypeMapper reportTypeMapper;

    /**
     * 신고 유형 추가
     * @param reportTypeRequest ResponseEntity<CommonResponse>
     * @return ResponseEntity<CommonResponse>
     */

    @Override
    public ResponseEntity<CommonResponse> insertReportType(ReportTypeRequest reportTypeRequest) {

        ReportTypeDto reportTypeDto = reportTypeMapper.reportTypeRequestToReportTypeDto(reportTypeRequest);
        ReportType reportType = reportTypeMapper.ReportTypeDtoToReportType(reportTypeDto);
        reportTypeRepository.save(reportType);

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(reportType.getId()), HttpStatus.OK);
    }

    /**
     * 신고 유형 조회
     * @param id Long
     * @return ResponseEntity<CommonResponse>
     */
    @Override
    public ResponseEntity<CommonResponse> selectReportType(Long id) {

        ReportType reportType = reportTypeRepository.findById(id).orElse(null);

        if (reportType == null) {
           return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.DATA_NOT_FOUND), HttpStatus.NOT_FOUND);
        }

        ReportTypeResponse reportTypeResponse = reportTypeMapper.reportTypeToReportTypeResponse(reportType);

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(reportTypeResponse), HttpStatus.OK);
    }
}