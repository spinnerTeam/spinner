package com.spinner.www.vote.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.spinner.www.vote.dto.QVoteCommunityResultDto is a Querydsl Projection type for VoteCommunityResultDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QVoteCommunityResultDto extends ConstructorExpression<VoteCommunityResultDto> {

    private static final long serialVersionUID = 597323549L;

    public QVoteCommunityResultDto(com.querydsl.core.types.Expression<Long> voteItemIdx, com.querydsl.core.types.Expression<String> voteItemName, com.querydsl.core.types.Expression<Long> voteCount, com.querydsl.core.types.Expression<Double> votePercentage) {
        super(VoteCommunityResultDto.class, new Class<?>[]{long.class, String.class, long.class, double.class}, voteItemIdx, voteItemName, voteCount, votePercentage);
    }

}

