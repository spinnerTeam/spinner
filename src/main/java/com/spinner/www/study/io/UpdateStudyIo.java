package com.spinner.www.study.io;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStudyIo {

    private Long studyIdx;
    private String studyName;
    private String studyInfo;
    private Integer studyMaxPeople;
    private Long studyTopicIdx;
}
