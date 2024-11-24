package com.spinner.www.report.mapper;

import com.spinner.www.report.dto.ReportCreateDto;
import com.spinner.www.report.dto.ReportGetDto;
import com.spinner.www.report.entity.Report;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReportMapper {
    Report reportDtoToReport(ReportGetDto dto);
    List<ReportGetDto> reportListToReportDtoList(List<Report> list);
    ReportGetDto reportToReportGetDto(Report report);
}
