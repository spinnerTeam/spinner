package com.spinner.www.file.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileDto {

    private String fileOriginName;
    private String fileConvertName;
    private String filePath;

    private String createdAt;
    private LocalDate createdDate;
    private LocalTime createdTime;
}
