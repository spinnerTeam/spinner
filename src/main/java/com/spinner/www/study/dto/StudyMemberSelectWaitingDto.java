package com.spinner.www.study.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class StudyMemberSelectWaitingDto {

    private Long memberIdx;
    private Long memberProfileIdx;
    private String memberName;

    @QueryProjection
    public StudyMemberSelectWaitingDto(Long memberIdx, Long memberProfileIdx, String memberName) {
        this.memberIdx = memberIdx;
        this.memberProfileIdx = memberProfileIdx;
        this.memberName = memberName;
    }
}
