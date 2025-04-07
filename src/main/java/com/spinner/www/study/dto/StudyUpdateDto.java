package com.spinner.www.study.dto;

import com.spinner.www.common.entity.StudyTopic;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudyUpdateDto {

    private String studyName;
    private String studyInfo;
    private Integer studyMaxPeople;
    private StudyTopic studyTopic;
}
