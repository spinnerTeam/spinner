package com.spinner.www.common.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BaseDto {
    private String createdAt;
    private LocalDateTime createdDate;
    private String modifiedAt;
    private LocalDateTime modifiedDate;
}
