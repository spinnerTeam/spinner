package com.spinner.www.study.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudyJoinMemberDto {

    private Long studyMemberIdx;
    private String nickName;
    private String info;

    @QueryProjection
    public StudyJoinMemberDto(Long studyMemberIdx, String nickName, String info) {
        this.studyMemberIdx = studyMemberIdx;
        this.nickName = nickName;
        this.info = info;
    }
}
