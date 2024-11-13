package com.spinner.www.file.mapper;

import com.spinner.www.file.dto.FileDto;
import com.spinner.www.file.entity.Files;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FileMapper {

    Files fileDtoToFile(FileDto fileDto);
    FileDto fileToFileDto(Files file);
}
