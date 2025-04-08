package com.spinner.www.study.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MyStudyListDto {

    private String studyName;
    private String studyInfo;
    private String studyMemberRole;

    @QueryProjection
    public MyStudyListDto(String studyName, String studyInfo, String studyMemberRole){
        this.studyName = studyName;
        this.studyInfo = studyInfo;
        this.studyMemberRole = studyMemberRole;
    }
}
