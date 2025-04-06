package com.spinner.www.file.mapper;

import com.spinner.www.file.dto.FileDto;
import com.spinner.www.file.entity.Files;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FileMapper {

//    @Mapping(target = "codeIdx", source = "fileTypeCodeIdx")
//    Files fileDtoToFile(FileDto fileDto);
}
