package com.spinner.www.study.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StudyUpdateDto {
    private String studyName;
    private String studyIntro;
    private Long studyCategoryType;
    private int studyMaxPeople;
}