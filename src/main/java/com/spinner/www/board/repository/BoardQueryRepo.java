package com.spinner.www.board.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spinner.www.board.io.BoardListResponse;
import com.spinner.www.board.io.BoardResponse;
import com.spinner.www.like.entity.QLike;
import com.spinner.www.reply.io.ReplyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.types.Projections.list;
import static com.querydsl.jpa.JPAExpressions.selectOne;
import static com.spinner.www.board.entity.QBoard.board;
import static com.spinner.www.bookmark.entity.QBookmark.bookmark;
import static com.spinner.www.like.entity.QLike.like;
import static com.spinner.www.reply.entity.QReply.reply;
import static com.spinner.www.vote.entity.QVote.vote;

@Repository
@RequiredArgsConstructor
public class BoardQueryRepo {
    private final JPAQueryFactory jpaQueryFactory;
    private final int NOT_REMOVED = 0;
    private final int NOT_REPORTED = 0;
    private final int IS_LIKE = 1;

//    QReply childReply = new QReply("childReply");
//    QMember memberReply = new QMember("memberReply");
//    QMember memberChildReply = new QMember("memberChildReply");

    public List<BoardListResponse> getSliceOfBoard(
            Long codeIdx,
            @Nullable
            Long idx,
            int size,
            @Nullable
            String keyword,
            @Nullable
            Long memberIdx
    ) {
        return jpaQueryFactory.select(
                        Projections.constructor(
                                BoardListResponse.class,
                                board.boardIdx,
                                board.commonCode.codeName,
                                board.boardTitle,
                                board.boardContent,
                                board.member.memberNickname,
                                JPAExpressions.select(vote.count())
                                        .from(vote)
                                        .where(vote.board.boardIdx.eq(board.boardIdx)
                                                .and(vote.voteIsRemoved.eq("N"))),
                                JPAExpressions
                                        .select(reply.count())
                                        .from(reply)
                                        .where(reply.board.boardIdx.eq(board.boardIdx)
                                                .and(reply.replyIsRemoved.eq(NOT_REMOVED))),
                                JPAExpressions
                                        .select(like.count())
                                        .from(like)
                                        .where(like.board.boardIdx.eq(board.boardIdx)
                                                .and(like.likeIsLiked.eq(IS_LIKE))),
                                board.hitCount,
                                JPAExpressions
                                        .select(like.count())
                                        .from(like)
                                        .where(
                                                like.board.boardIdx.eq(board.boardIdx)
                                                        .and(like.member.memberIdx.eq(memberIdx))
                                                        .and(like.likeIsLiked.eq(IS_LIKE))),
                                JPAExpressions
                                        .select(bookmark.count())
                                        .from(bookmark)
                                        .where(bookmark.board.boardIdx.eq(board.boardIdx)
                                                .and(bookmark.member.memberIdx.eq(memberIdx))
                                                .and(bookmark.isBookmarked.eq(1))),
                                board.createdDate,
                                board.modifiedDate
                        )
                )
                .from(board)
                .where(getCodeIdx(codeIdx), getNotRemoved(), getNotReported(), ltBoardIdx(idx), search(keyword))
                .orderBy(board.boardIdx.desc())
                .limit(size)
                .fetch();
    }

    public List<BoardListResponse> getSliceOfHotBoard(
            Long codeIdx,
            @Nullable
            Long idx,
            int size,
            @Nullable
            Long memberIdx
    ) {
        NumberExpression<Long> replyCount = Expressions.numberTemplate(
                Long.class,
                "({0})",
                JPAExpressions.select(reply.count())
                        .from(reply)
                        .where(reply.board.boardIdx.eq(board.boardIdx)
                                .and(reply.replyIsRemoved.eq(NOT_REMOVED)))
        );
        return jpaQueryFactory.select(
                        Projections.constructor(
                                BoardListResponse.class,
                                board.boardIdx,
                                board.commonCode.codeName,
                                board.boardTitle,
                                board.boardContent,
                                board.member.memberNickname,
                                JPAExpressions.select(vote.count())
                                        .from(vote)
                                        .where(vote.board.boardIdx.eq(board.boardIdx)
                                                .and(vote.voteIsRemoved.eq("N"))),
                                JPAExpressions
                                        .select(reply.count())
                                        .from(reply)
                                        .where(reply.board.boardIdx.eq(board.boardIdx)
                                                .and(reply.replyIsRemoved.eq(NOT_REMOVED))),
                                JPAExpressions
                                        .select(like.count())
                                        .from(like)
                                        .where(like.board.boardIdx.eq(board.boardIdx)
                                                .and(like.likeIsLiked.eq(IS_LIKE))),
                                board.hitCount,
                                JPAExpressions
                                        .select(like.count())
                                        .from(like)
                                        .where(
                                                like.board.boardIdx.eq(board.boardIdx)
                                                        .and(like.member.memberIdx.eq(memberIdx))
                                                        .and(like.likeIsLiked.eq(IS_LIKE))),
                                JPAExpressions
                                        .select(bookmark.count())
                                        .from(bookmark)
                                        .where(bookmark.board.boardIdx.eq(board.boardIdx)
                                                .and(bookmark.member.memberIdx.eq(memberIdx))
                                                .and(bookmark.isBookmarked.eq(1))),
                                board.createdDate,
                                board.modifiedDate
                        )
                )
                .from(board)
                .where(getCodeIdx(codeIdx), getNotRemoved(), getNotReported(), ltBoardIdx(idx))
                .orderBy(
                        replyCount.desc(),
                        board.hitCount.desc(),
                        board.boardIdx.desc()
                )

                .limit(size)
                .fetch();
    }

    public List<BoardListResponse> getSliceOfMemberBoard(
            @Nullable
            Long idx,
            int size,
            Long memberIdx
    ) {
        return jpaQueryFactory.select(
                        Projections.constructor(
                                BoardListResponse.class,
                                board.boardIdx,
                                board.commonCode.codeName,
                                board.boardTitle,
                                board.boardContent,
                                board.member.memberNickname,
                                JPAExpressions.select(vote.count())
                                        .from(vote)
                                        .where(vote.board.boardIdx.eq(board.boardIdx)
                                                .and(vote.voteIsRemoved.eq("N"))),
                                JPAExpressions
                                        .select(reply.count())
                                        .from(reply)
                                        .where(reply.board.boardIdx.eq(board.boardIdx)
                                                .and(reply.replyIsRemoved.eq(NOT_REMOVED))),
                                JPAExpressions
                                        .select(like.count())
                                        .from(like)
                                        .where(like.board.boardIdx.eq(board.boardIdx)
                                                .and(like.likeIsLiked.eq(IS_LIKE))),
                                board.hitCount,
                                JPAExpressions
                                        .select(like.count())
                                        .from(like)
                                        .where(
                                                like.board.boardIdx.eq(board.boardIdx)
                                                        .and(like.member.memberIdx.eq(memberIdx))
                                                        .and(like.likeIsLiked.eq(IS_LIKE))),
                                JPAExpressions
                                        .select(bookmark.count())
                                        .from(bookmark)
                                        .where(bookmark.board.boardIdx.eq(board.boardIdx)
                                                .and(bookmark.member.memberIdx.eq(memberIdx))
                                                .and(bookmark.isBookmarked.eq(1))),
                                board.createdDate,
                                board.modifiedDate
                        )
                )
                .from(board)
                .where(getCreateMemberIdx(memberIdx), getNotRemoved(), getNotReported(), ltBoardIdx(idx))
                .orderBy(board.boardIdx.desc())
                .limit(size)
                .fetch();
    }


    public List<BoardListResponse> getSliceOfLikedBoard(
            @Nullable
            Long idx,
            int size,
            Long memberIdx
    ) {
        return jpaQueryFactory.select(
                        Projections.constructor(
                                BoardListResponse.class,
                                board.boardIdx,
                                board.commonCode.codeName,
                                board.boardTitle,
                                board.boardContent,
                                board.member.memberNickname,
                                JPAExpressions.select(vote.count())
                                        .from(vote)
                                        .where(vote.board.boardIdx.eq(board.boardIdx)
                                                .and(vote.voteIsRemoved.eq("N"))),
                                JPAExpressions
                                        .select(reply.count())
                                        .from(reply)
                                        .where(reply.board.boardIdx.eq(board.boardIdx)
                                                .and(reply.replyIsRemoved.eq(NOT_REMOVED))),
                                JPAExpressions
                                        .select(like.count())
                                        .from(like)
                                        .where(like.board.boardIdx.eq(board.boardIdx)
                                                .and(like.likeIsLiked.eq(IS_LIKE))),
                                board.hitCount,
                                JPAExpressions
                                        .select(like.count())
                                        .from(like)
                                        .where(
                                                like.board.boardIdx.eq(board.boardIdx)
                                                        .and(like.member.memberIdx.eq(memberIdx))
                                                        .and(like.likeIsLiked.eq(IS_LIKE))),
                                JPAExpressions
                                        .select(bookmark.count())
                                        .from(bookmark)
                                        .where(bookmark.board.boardIdx.eq(board.boardIdx)
                                                .and(bookmark.member.memberIdx.eq(memberIdx))
                                                .and(bookmark.isBookmarked.eq(1))),
                                board.createdDate,
                                board.modifiedDate
                        )
                )
                .from(board)
                .where(getLikedMemberIdx(memberIdx), getNotRemoved(), getNotReported(), ltBoardIdx(idx))
                .orderBy(board.boardIdx.desc())
                .limit(size)
                .fetch();
    }


    public List<BoardListResponse> getSliceOfBookmarkedBoard(
            @Nullable
            Long idx,
            int size,
            Long memberIdx
    ) {
        return jpaQueryFactory.select(
                        Projections.constructor(
                                BoardListResponse.class,
                                board.boardIdx,
                                board.commonCode.codeName,
                                board.boardTitle,
                                board.boardContent,
                                board.member.memberNickname,
                                JPAExpressions.select(vote.count())
                                        .from(vote)
                                        .where(vote.board.boardIdx.eq(board.boardIdx)
                                                .and(vote.voteIsRemoved.eq("N"))),
                                JPAExpressions
                                        .select(reply.count())
                                        .from(reply)
                                        .where(reply.board.boardIdx.eq(board.boardIdx)
                                                .and(reply.replyIsRemoved.eq(NOT_REMOVED))),
                                JPAExpressions
                                        .select(like.count())
                                        .from(like)
                                        .where(like.board.boardIdx.eq(board.boardIdx)
                                                .and(like.likeIsLiked.eq(IS_LIKE))),
                                board.hitCount,
                                JPAExpressions
                                        .select(like.count())
                                        .from(like)
                                        .where(
                                                like.board.boardIdx.eq(board.boardIdx)
                                                        .and(like.member.memberIdx.eq(memberIdx))
                                                        .and(like.likeIsLiked.eq(IS_LIKE))),
                                JPAExpressions
                                        .select(bookmark.count())
                                        .from(bookmark)
                                        .where(bookmark.board.boardIdx.eq(board.boardIdx)
                                                .and(bookmark.member.memberIdx.eq(memberIdx))
                                                .and(bookmark.isBookmarked.eq(1))),
                                board.createdDate,
                                board.modifiedDate
                        )
                )
                .from(board)
                .where(getBookmarkMemberIdx(memberIdx), getNotRemoved(), getNotReported(), ltBoardIdx(idx))
                .orderBy(board.boardIdx.desc())
                .limit(size)
                .fetch();
    }

// 댓글과 대댓글을 1개씩만 가져오는 문제가 있어 사용하지 않음
    public BoardResponse getBoard(
            Long idx
    ) {
        Map<Long, BoardResponse> result = jpaQueryFactory
                .from(board)
//                .leftJoin(reply).on(
//                        reply.board.boardIdx.eq(board.boardIdx)
//                )
//                .leftJoin(reply.childReplies, childReply)
//                .on(
//                        childReply.boardIdx.eq(board.boardIdx)
//                )
//                .leftJoin(member).on(board.member.memberIdx.eq(member.memberIdx))
//                .leftJoin(memberReply).on(reply.member.memberIdx.eq(memberReply.memberIdx))
//                .leftJoin(memberChildReply).on(childReply.member.memberIdx.eq(memberChildReply.memberIdx))
                .where(getBoardIdx(idx), getNotRemoved(), getNotReported())
                .transform(groupBy(board.boardIdx).as(
                        Projections.constructor(
                                BoardResponse.class,
                                board.boardIdx,
                                board.member.memberNickname,
                                board.boardTitle,
                                board.boardContent,
                                list(
                                        Projections.constructor(ReplyResponse.class,
//                                        board.boardIdx,
                                                reply.replyIdx,
                                                reply.replyContent
//                                        reply.member.memberNickname,
//                                        list(
//                                                Projections.constructor(ReplyResponse.class,
////                                                        board.boardIdx,
//                                                        childReply.replyIdx,
//                                                        childReply.replyContent,
//                                                        childReply.member.memberNickname
//                                                )
//                                        )
                                        )
                                ),
                                board.createdAt,
                                board.createdDate,
                                board.modifiedAt,
                                board.modifiedDate
                        )
                ));
        return result.get(idx);
    }

    private BooleanBuilder getCodeIdx(Long codeIdx) {
        return new BooleanBuilder(board.commonCode.codeIdx.eq(codeIdx));
    }

    private BooleanBuilder getNotRemoved() {
        return new BooleanBuilder(board.boardIsRemoved.eq(NOT_REMOVED));
    }

    private BooleanBuilder getNotRemovedReply() {
        return new BooleanBuilder(board.replies.any().replyIsRemoved.eq(NOT_REMOVED));
    }

    private BooleanBuilder getNotReported() {
        return new BooleanBuilder(board.boardIsReported.eq(NOT_REPORTED));
    }

    private BooleanBuilder search(String keyword) {
        return containsTitle(keyword).or(containsNickname(keyword));
    }

    private BooleanBuilder containsTitle(@Nullable String title) {
        return StringUtils.hasText(title) ? new BooleanBuilder(board.boardTitle.contains(title)) : new BooleanBuilder();
    }

    private BooleanBuilder containsNickname(@Nullable String nickname) {
        return StringUtils.hasText(nickname) ? new BooleanBuilder(board.member.memberNickname.contains(nickname)) : new BooleanBuilder();
    }

    private BooleanBuilder ltBoardIdx(@Nullable Long idx) {
        return idx == null ? new BooleanBuilder() : new BooleanBuilder(board.boardIdx.lt(idx));
    }

    private BooleanBuilder getBoardIdx(@Nullable Long idx) {
        return new BooleanBuilder(board.boardIdx.eq(idx));
    }

    private BooleanBuilder getCreateMemberIdx(Long memberIdx) {
        return new BooleanBuilder(board.member.memberIdx.eq(memberIdx));
    }

    private BooleanBuilder getLikedMemberIdx(Long memberIdx) {
        QLike like = QLike.like; // QLike의 별도 인스턴스 사용
        return new BooleanBuilder(
                selectOne()
                        .from(like)
                        .where(
                                like.board.eq(board), // board는 QBoard 인스턴스 (예: QBoard.board)
                                like.likeIsLiked.eq(1),
                                like.member.memberIdx.eq(memberIdx)
                        )
                        .exists()
        );
    }

    private BooleanBuilder getBookmarkMemberIdx(Long memberIdx) {
        return new BooleanBuilder(board.bookmarks.any().isBookmarked.eq(1).and(board.bookmarks.any().member.memberIdx.eq(memberIdx)));
    }
}
