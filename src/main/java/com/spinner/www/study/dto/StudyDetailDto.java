package com.spinner.www.study.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class StudyDetailDto {

    private String studyName;
    private String studyInfo;
    private String filePath;
    private String createdDate;
    private String studyTopicName;

    private List<StudyJoinMemberDto> studyJoinMemberDtos;
    private Long studyMemberCount;
    private Long countBoard;

    private boolean studyJoinStatus;

    @QueryProjection
    public StudyDetailDto(String studyName, String studyInfo, String filePath, String createdDate, String studyTopicName){
        this.studyName = studyName;
        this.studyInfo = studyInfo;
        this.filePath = filePath;
        this.createdDate = createdDate;
        this.studyTopicName = studyTopicName;
    }

    public void setMembersAndCounts(List<StudyJoinMemberDto> studyJoinMemberDtos, Long studyMemberCount, boolean studyJoinStatus){
        this.studyJoinMemberDtos = studyJoinMemberDtos;
        this.studyMemberCount = studyMemberCount;
        this.studyJoinStatus = studyJoinStatus;
    }

}
