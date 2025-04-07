package com.spinner.www.study.io;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateStudy {

    private String studyName;
    private MultipartFile file;
    private String studyInfo;
    private Integer studyMaxPeople;
    private Long studyTopicIdx;
}
