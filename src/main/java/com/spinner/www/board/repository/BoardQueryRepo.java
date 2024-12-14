package com.spinner.www.board.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spinner.www.board.io.BoardListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.spinner.www.board.entity.QBoard.board;

@Repository
@RequiredArgsConstructor
public class BoardQueryRepo {
    private final JPAQueryFactory jpaQueryFactory;

    public List<BoardListResponse> getSliceOfBoard(
            @Nullable
            Long idx,
            int size,
            @Nullable
            String keyword
    ) {
        return jpaQueryFactory.select(
                        Projections.constructor(
                                BoardListResponse.class,
                                board.boardIdx,
                                board.boardTitle,
                                board.boardContent,
                                board.member.memberNickname,
                                board.replies.size().longValue(),
                                board.createdAt,
                                board.createdDate,
                                board.modifiedAt,
                                board.modifiedDate
                        )
                )
                .from(board)
                .where(ltBoardIdx(idx), search(keyword))
                .orderBy(board.boardIdx.desc())
                .limit(size)
                .fetch();
    }

    @SuppressWarnings("all")
    private BooleanBuilder search(String keyword) {
        return containsTitle(keyword).or(containsContent(keyword));
    }

    private BooleanBuilder containsTitle(@Nullable String title) {
        return StringUtils.hasText(title) ? new BooleanBuilder(board.boardTitle.contains(title)) : new BooleanBuilder();
    }

    private BooleanBuilder containsContent(@Nullable String content) {
        return StringUtils.hasText(content) ? new BooleanBuilder(board.boardContent.contains(content)) : new BooleanBuilder();
    }

    private BooleanBuilder ltBoardIdx(@Nullable Long idx) {
        return idx == null ? new BooleanBuilder() : new BooleanBuilder(board.boardIdx.lt(idx));
    }
}
