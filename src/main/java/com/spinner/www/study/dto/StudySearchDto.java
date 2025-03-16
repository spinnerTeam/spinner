package com.spinner.www.study.dto;


import com.querydsl.core.annotations.QueryProjection;
import com.spinner.www.file.entity.Files;

public class StudySearchDto {
    private Long studyIdx;
    private String studyName;
    private String studyIntro;
    private Long fileIdx;

    @QueryProjection
    public StudySearchDto(Long id, String studyName, String studyIntro, Files files) {
        this.studyIdx = id;
        this.studyName = studyName;
        this.studyIntro = studyIntro;
        this.fileIdx = files.getFileIdx();
    }
}
