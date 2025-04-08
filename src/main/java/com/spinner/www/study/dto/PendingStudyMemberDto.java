package com.spinner.www.study.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PendingStudyMemberDto {

    private Long studyIdx;
    private String nickName;

    @QueryProjection
    public PendingStudyMemberDto(Long studyIdx, String nickName){
        this.studyIdx = studyIdx;
        this.nickName = nickName;
    }
}
