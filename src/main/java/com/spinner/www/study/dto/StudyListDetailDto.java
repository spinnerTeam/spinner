package com.spinner.www.study.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
@Getter
public class StudyListDetailDto {


    private Long countMember;
    private Long countBoard;

    @QueryProjection
    public StudyListDetailDto(String filePath, String studyName, String studyInfo, String studyTopicName, Long countMember, Long countBoard){

        this.countBoard = countBoard;
    }
}
