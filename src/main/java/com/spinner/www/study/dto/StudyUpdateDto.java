package com.spinner.www.study.dto;

import com.spinner.www.study.constants.StudyCategoryType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StudyUpdateDto {
    private String studyName;
    private String studyIntro;
    private StudyCategoryType studyCategoryType;
    private int studyMaxPeople;
}