package com.spinner.www.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseDto {

    private String createdAt;
    private LocalDateTime createdDate;
    private String modifiedAt;
    private LocalDateTime modifiedDate;
}
