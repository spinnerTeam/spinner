package com.spinner.www.file.io;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BoardFileResponse {
    private Long idx;
    private String fileName;
    private String fileType;
    private String fileUrl;
}
