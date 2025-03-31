package com.spinner.www.study.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.spinner.www.study.dto.QStudySearchDto is a Querydsl Projection type for StudySearchDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QStudySearchDto extends ConstructorExpression<StudySearchDto> {

    private static final long serialVersionUID = 610418715L;

    public QStudySearchDto(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> studyName, com.querydsl.core.types.Expression<String> studyIntro, com.querydsl.core.types.Expression<? extends com.spinner.www.file.entity.Files> files) {
        super(StudySearchDto.class, new Class<?>[]{long.class, String.class, String.class, com.spinner.www.file.entity.Files.class}, id, studyName, studyIntro, files);
    }

}

