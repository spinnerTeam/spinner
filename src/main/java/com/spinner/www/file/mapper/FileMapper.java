package com.spinner.www.file.mapper;

import com.spinner.www.file.constants.CommonFileCode;
import com.spinner.www.file.dto.FileDto;
import com.spinner.www.file.entity.Files;
import com.spinner.www.file.io.BoardFileResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FileMapper {

    @Mapping(target = "codeIdx", source = "fileTypeCodeIdx")
    Files fileDtoToFile(FileDto fileDto);

    @Mapping(target="fileType", expression = "java(FileMapper.mapFileType(file.getCodeIdx()))")
    @Mapping(target="fileUrl", expression = "java(FileMapper.mapFileUrl(file.getFileIdx()))")
    BoardFileResponse fileToBoardFileResponse(Files file);

    static String mapFileType(Long fileTypeCodeIdx) {
        return CommonFileCode.getLetter(fileTypeCodeIdx);
    }

    // HACK url 생성할때 아이피랑 포트 가져오는 방식 수정 필요
    static String mapFileUrl(Long fileIdx) {
        return "http://127.0.0.1:3030/common/file/"+fileIdx;
    }
}
