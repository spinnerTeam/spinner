package com.spinner.www.file.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileDto {

    private String fileOriginName;
    private String fileConvertName;
    private String filePath;
    private LocalDateTime createdDatetime;
    private String createdAt;
}
