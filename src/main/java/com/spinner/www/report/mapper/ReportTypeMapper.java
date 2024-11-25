package com.spinner.www.report.mapper;

import com.spinner.www.report.dto.ReportTypeDto;
import com.spinner.www.report.entity.ReportType;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReportTypeMapper {
    ReportType ReportTypeDtoToReportType(ReportTypeDto reportTypeDto);
    List<ReportTypeDto> ReportTypeListToReportDtoTypeList(List<ReportType> reportTypeDtoList);
    ReportTypeDto ReportTypeToReportTypeDto(ReportType reportType);
}