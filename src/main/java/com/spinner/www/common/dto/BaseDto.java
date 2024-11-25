package com.spinner.www.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseDto {
    private String createdAt;
    private LocalDateTime createdDate;
    private String modifiedAt;
    private LocalDateTime modifiedDate;
}
