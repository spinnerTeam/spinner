package com.spinner.www.report.Mapper;

import com.spinner.www.report.dto.ReportTypeDto;
import com.spinner.www.report.entity.ReportType;
import com.spinner.www.report.io.ReportTypeRequest;
import com.spinner.www.report.io.ReportTypeResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReportTypeMapper {
    ReportTypeDto reportTypeRequestToReportTypeDto(ReportTypeRequest reportTypeRequest);
    ReportType ReportTypeDtoToReportType(ReportTypeDto reportTypeDto);
    ReportTypeDto ReportTypeToReportTypeDto(ReportType reportType);
    ReportTypeResponse reportTypeToReportTypeResponse(ReportType reportType);
}