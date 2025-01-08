package com.spinner.www.common.io;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse {
    private String createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime createdDate;
    private String modifiedAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime modifiedDate;

    public BaseResponse(LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
