package com.spinner.www.study.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudyJoinMemberDto {

    private String nickName;
    private String info;

    @QueryProjection
    public StudyJoinMemberDto(String nickName, String info) {
        this.nickName = nickName;
        this.info = info;
    }
}
