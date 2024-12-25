package com.spinner.www.vote.repository;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spinner.www.vote.dto.QVoteCommunityResultDto;
import com.spinner.www.vote.dto.VoteCommunityResultDto;
import com.spinner.www.vote.entity.QVoteItem;
import com.spinner.www.vote.entity.QVoteUser;
import com.spinner.www.vote.entity.Vote;
import com.spinner.www.vote.io.VoteResultsCommunityResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class VoteQueryRepo {

    private final JPAQueryFactory queryFactory;

    public VoteResultsCommunityResponse findVoteCommunityResultsByVote(Vote vote) {

        QVoteUser voteUser = QVoteUser.voteUser;
        QVoteItem voteItem = QVoteItem.voteItem;

        // 총 투표 수 계산
        Long totalVotes = queryFactory
                .select(voteUser.count())
                .from(voteUser)
                .where(voteUser.vote.eq(vote))
                .fetchOne();

        // 전체 투표 수가 0이거나 데이터가 없을 경우 빈 응답 반환
        if (totalVotes == null || totalVotes == 0L) {
            return VoteResultsCommunityResponse.builder()
                    .totalVotes(0)
                    .voteResult(List.of())
                    .build();
        }

        // 전체 투표 수를 NumberExpression으로 변환
        NumberExpression<Double> totalVotesExpr = Expressions.asNumber(totalVotes.doubleValue());

        // 각 투표 항목별 데이터 조회
        List<VoteCommunityResultDto> results = queryFactory.select(new QVoteCommunityResultDto(
                        voteItem.voteItemName,
                        voteUser.count(),
                        voteUser.count().doubleValue().divide(totalVotesExpr)))
                .from(voteUser)
                .join(voteUser.voteItem, voteItem)
                .where(voteUser.vote.eq(vote))
                .groupBy(voteItem.id, voteItem.voteItemName)
                .fetch();

        return VoteResultsCommunityResponse.builder()
                .totalVotes(totalVotes)
                .voteResult(results)
                .build();
    }
}
