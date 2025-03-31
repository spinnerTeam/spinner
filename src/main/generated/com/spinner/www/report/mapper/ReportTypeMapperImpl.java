package com.spinner.www.report.mapper;

import com.spinner.www.report.dto.ReportTypeDto;
import com.spinner.www.report.entity.ReportType;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-17T16:56:02+0900",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.10.2.jar, environment: Java 17.0.14 (Amazon.com Inc.)"
)
@Component
public class ReportTypeMapperImpl implements ReportTypeMapper {

    @Override
    public ReportType ReportTypeDtoToReportType(ReportTypeDto reportTypeDto) {
        if ( reportTypeDto == null ) {
            return null;
        }

        ReportType.ReportTypeBuilder reportType = ReportType.builder();

        reportType.id( reportTypeDto.getId() );
        reportType.reportTypeContent( reportTypeDto.getReportTypeContent() );

        return reportType.build();
    }

    @Override
    public List<ReportTypeDto> ReportTypeListToReportDtoTypeList(List<ReportType> reportTypeDtoList) {
        if ( reportTypeDtoList == null ) {
            return null;
        }

        List<ReportTypeDto> list = new ArrayList<ReportTypeDto>( reportTypeDtoList.size() );
        for ( ReportType reportType : reportTypeDtoList ) {
            list.add( ReportTypeToReportTypeDto( reportType ) );
        }

        return list;
    }

    @Override
    public ReportTypeDto ReportTypeToReportTypeDto(ReportType reportType) {
        if ( reportType == null ) {
            return null;
        }

        ReportTypeDto.ReportTypeDtoBuilder<?, ?> reportTypeDto = ReportTypeDto.builder();

        if ( reportType.getCreatedAt() != null ) {
            reportTypeDto.createdAt( String.valueOf( reportType.getCreatedAt() ) );
        }
        reportTypeDto.createdDate( reportType.getCreatedDate() );
        if ( reportType.getModifiedAt() != null ) {
            reportTypeDto.modifiedAt( String.valueOf( reportType.getModifiedAt() ) );
        }
        reportTypeDto.modifiedDate( reportType.getModifiedDate() );
        reportTypeDto.id( reportType.getId() );
        reportTypeDto.reportTypeContent( reportType.getReportTypeContent() );

        return reportTypeDto.build();
    }
}
