package com.spinner.www.study.io;

import com.spinner.www.file.entity.Files;
import org.springframework.web.multipart.MultipartFile;

public class CreateStudy {

    private String studyName;
    private MultipartFile file;
    private String studyInfo;
    private Integer studyMaxPeople;
    private Long studyTopicIdx;
}
