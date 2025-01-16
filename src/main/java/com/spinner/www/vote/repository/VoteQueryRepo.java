package com.spinner.www.vote.repository;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spinner.www.member.entity.Member;
import com.spinner.www.vote.dto.QVoteCommunityResultDto;
import com.spinner.www.vote.dto.VoteCommunityResultDto;
import com.spinner.www.vote.entity.QVoteItem;
import com.spinner.www.vote.entity.QVoteUser;
import com.spinner.www.vote.entity.Vote;
import com.spinner.www.vote.entity.VoteItem;
import com.spinner.www.vote.io.VoteResultsCommunityResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.spinner.www.vote.entity.QVoteUser.*;
import static com.spinner.www.vote.entity.QVoteItem.*;

@Repository
@RequiredArgsConstructor
public class VoteQueryRepo {

    private final JPAQueryFactory queryFactory;

    // 기존에 투표한 적이 있는지
    public Boolean findVoteUser(Member member, Vote vote) {

        List<Long> voteUserStatus = queryFactory.select(voteUser.id)
                .from(voteUser)
                .where(voteUser.vote.id.eq(vote.getId())
                        .and(voteUser.member.memberIdx.eq(member.getMemberIdx())))
                .fetch();
        // 투표한 적이 있으면 false 반환, 투표한 적 없으면 true 반환
        return voteUserStatus.isEmpty();
    }

    // 투표 항목의 투표 idx와 투표가 일치하는지
    public Boolean findVote(Vote vote, List<Long> itemIdxList) {

        List<Long> voteItemStatus = queryFactory
                .select(voteItem.id)
                .from(voteItem)
                .where(voteItem.vote.id.eq(vote.getId())
                        .and(voteItem.id.in(itemIdxList)))
                .fetch();

        // 결과가 있으면 true
        return !voteItemStatus.isEmpty();
    }

    public VoteResultsCommunityResponse findVoteCommunityResultsByVote(Vote vote) {

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
                        voteItem.id,
                        voteItem.voteItemName,
                        voteUser.count(),
                        Expressions.asNumber(
                                voteUser.count()
                                        .doubleValue()
                                        .divide(totalVotesExpr)
                                        .multiply(100) // 퍼센트 계산
                                        .multiply(10) // 소숫점 1번째 자리까지 유지하도록 변환
                                        .round() // 반올림 처리
                                        .divide(10) // 다시 원래 단위로 변환
                        )))
                .from(voteItem)
                .leftJoin(voteUser)
                .on(voteUser.voteItem.eq(voteItem))
                .where(voteItem.vote.eq(vote))
                .groupBy(voteItem.id, voteItem.voteItemName)
                .fetch();

        return VoteResultsCommunityResponse.builder()
                .totalVotes(totalVotes)
                .voteIdx(vote.getId())
                .voteResult(results)
                .build();
    }
}
