package com.spinner.www.study.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StudyListDto {

    private String filePath;
    private String studyName;
    private String studyInfo;
    private String studyTopicName;
    private Long countMember;

    @QueryProjection
    public StudyListDto(String filePath, String studyName, String studyInfo, String studyTopicName, Long countMember){
        this.filePath = filePath;
        this.studyName = studyName;
        this.studyInfo = studyInfo;
        this.studyTopicName = studyTopicName;
        this.countMember = countMember;
    }
}
