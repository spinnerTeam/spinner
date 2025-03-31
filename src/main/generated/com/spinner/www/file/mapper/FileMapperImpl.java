package com.spinner.www.file.mapper;

import com.spinner.www.file.dto.FileDto;
import com.spinner.www.file.entity.Files;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-17T16:56:03+0900",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.10.2.jar, environment: Java 17.0.14 (Amazon.com Inc.)"
)
@Component
public class FileMapperImpl implements FileMapper {

    @Override
    public Files fileDtoToFile(FileDto fileDto) {
        if ( fileDto == null ) {
            return null;
        }

        Files.FilesBuilder files = Files.builder();

        files.codeIdx( fileDto.getFileTypeCodeIdx() );
        files.fileOriginName( fileDto.getFileOriginName() );
        files.fileConvertName( fileDto.getFileConvertName() );
        files.filePath( fileDto.getFilePath() );

        return files.build();
    }
}
