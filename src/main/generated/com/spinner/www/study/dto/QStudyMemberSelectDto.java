package com.spinner.www.study.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.spinner.www.study.dto.QStudyMemberSelectDto is a Querydsl Projection type for StudyMemberSelectDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QStudyMemberSelectDto extends ConstructorExpression<StudyMemberSelectDto> {

    private static final long serialVersionUID = -274182195L;

    public QStudyMemberSelectDto(com.querydsl.core.types.Expression<Long> memberIdx, com.querydsl.core.types.Expression<Long> memberProfileIdx, com.querydsl.core.types.Expression<String> memberName) {
        super(StudyMemberSelectDto.class, new Class<?>[]{long.class, long.class, String.class}, memberIdx, memberProfileIdx, memberName);
    }

}

