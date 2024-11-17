package com.spinner.www.file.dto;

import com.spinner.www.member.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileDto extends BaseEntity {
    private String fileOriginName;
    private String fileConvertName;
    private String filePath;
}
