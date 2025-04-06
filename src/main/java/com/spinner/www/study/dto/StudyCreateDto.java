package com.spinner.www.study.dto;

import com.spinner.www.common.entity.StudyTopic;
import com.spinner.www.file.entity.Files;
import lombok.Getter;

@Getter
public class StudyCreateDto {

    private String studyName;
    private Files files;
    private String studyInfo;
    private Integer studyMaxPeople;
    private StudyTopic studyTopic;
}
