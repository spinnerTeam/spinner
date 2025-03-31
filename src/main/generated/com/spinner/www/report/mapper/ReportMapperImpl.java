package com.spinner.www.report.mapper;

import com.spinner.www.report.dto.ReportGetDto;
import com.spinner.www.report.entity.Report;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-17T16:56:03+0900",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.10.2.jar, environment: Java 17.0.14 (Amazon.com Inc.)"
)
@Component
public class ReportMapperImpl implements ReportMapper {

    @Override
    public Report reportDtoToReport(ReportGetDto dto) {
        if ( dto == null ) {
            return null;
        }

        Report.ReportBuilder report = Report.builder();

        report.id( dto.getId() );
        report.reportType( dto.getReportType() );
        report.board( dto.getBoard() );
        report.member( dto.getMember() );

        return report.build();
    }

    @Override
    public List<ReportGetDto> reportListToReportDtoList(List<Report> list) {
        if ( list == null ) {
            return null;
        }

        List<ReportGetDto> list1 = new ArrayList<ReportGetDto>( list.size() );
        for ( Report report : list ) {
            list1.add( reportToReportGetDto( report ) );
        }

        return list1;
    }

    @Override
    public ReportGetDto reportToReportGetDto(Report report) {
        if ( report == null ) {
            return null;
        }

        ReportGetDto.ReportGetDtoBuilder<?, ?> reportGetDto = ReportGetDto.builder();

        if ( report.getCreatedAt() != null ) {
            reportGetDto.createdAt( String.valueOf( report.getCreatedAt() ) );
        }
        reportGetDto.createdDate( report.getCreatedDate() );
        if ( report.getModifiedAt() != null ) {
            reportGetDto.modifiedAt( String.valueOf( report.getModifiedAt() ) );
        }
        reportGetDto.modifiedDate( report.getModifiedDate() );
        reportGetDto.id( report.getId() );
        reportGetDto.reportType( report.getReportType() );
        reportGetDto.board( report.getBoard() );
        reportGetDto.member( report.getMember() );

        return reportGetDto.build();
    }
}
