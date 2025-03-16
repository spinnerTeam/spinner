package com.spinner.www.study.io;

import com.spinner.www.common.entity.CommonCode;
import com.spinner.www.study.constants.StudyStatusType;
import lombok.Getter;

@Getter
public class StudyResponse {
    private Long studyIdx;
    private String studyName;
    private String studyIntro;
    private Long fileIdx;
    private StudyStatusType studyStatusType;
    private String studyIsRemoved;
    private CommonCode common;
    private Long studyViews;
    private int studyMaxPeople;
}
