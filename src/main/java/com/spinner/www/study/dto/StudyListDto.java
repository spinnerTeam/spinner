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

    private Long studyIdx;
    private String filePath;
    private String studyName;
    private String studyInfo;
    private String studyTopicName;
    private Integer memberCount;
    private Integer boardCount;


    @QueryProjection
    public StudyListDto(Long studyIdx, String filePath, String studyName, String studyInfo, String studyTopicName){
        this.studyIdx = studyIdx;
        this.filePath = filePath;
        this.studyName = studyName;
        this.studyInfo = studyInfo;
        this.studyTopicName = studyTopicName;
    }

    public void addMemberIdxCountAndBoardIdxCount(Integer memberCount, Integer boardCount){
        this.memberCount = memberCount;
        this.boardCount = boardCount;
    }
}
