package com.spinner.www.report.mapper;

import com.spinner.www.report.dto.ReportTypeDto;
import com.spinner.www.report.entity.ReportType;
import com.spinner.www.report.io.ReportTypeCreateRequest;
import com.spinner.www.report.io.ReportTypeResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReportTypeMapper {
    ReportType ReportTypeDtoToReportType(ReportTypeDto reportTypeDto);
    ReportTypeDto ReportTypeToReportTypeDto(ReportType reportType);
}