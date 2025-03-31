package com.spinner.www.study.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.spinner.www.study.dto.QStudyMemberSelectWaitingDto is a Querydsl Projection type for StudyMemberSelectWaitingDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QStudyMemberSelectWaitingDto extends ConstructorExpression<StudyMemberSelectWaitingDto> {

    private static final long serialVersionUID = -1302886748L;

    public QStudyMemberSelectWaitingDto(com.querydsl.core.types.Expression<Long> memberIdx, com.querydsl.core.types.Expression<Long> memberProfileIdx, com.querydsl.core.types.Expression<String> memberName) {
        super(StudyMemberSelectWaitingDto.class, new Class<?>[]{long.class, long.class, String.class}, memberIdx, memberProfileIdx, memberName);
    }

}

