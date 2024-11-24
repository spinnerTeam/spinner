package com.spinner.www.file.dto;

import com.spinner.www.common.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class FileDto extends BaseDto {
    private String fileOriginName;
    private String fileConvertName;
    private String filePath;
}
