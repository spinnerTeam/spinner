package com.spinner.www.board.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoard is a Querydsl query type for Board
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoard extends EntityPathBase<Board> {

    private static final long serialVersionUID = -218405316L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBoard board = new QBoard("board");

    public final com.spinner.www.common.entity.QBaseEntity _super = new com.spinner.www.common.entity.QBaseEntity(this);

    public final StringPath boardContent = createString("boardContent");

    public final NumberPath<Long> boardIdx = createNumber("boardIdx", Long.class);

    public final NumberPath<Integer> boardIsRemoved = createNumber("boardIsRemoved", Integer.class);

    public final NumberPath<Integer> boardIsReported = createNumber("boardIsReported", Integer.class);

    public final StringPath boardTitle = createString("boardTitle");

    public final NumberPath<Long> codeIdx = createNumber("codeIdx", Long.class);

    //inherited
    public final NumberPath<Long> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> hitCount = createNumber("hitCount", Long.class);

    public final ListPath<com.spinner.www.like.entity.Like, com.spinner.www.like.entity.QLike> likes = this.<com.spinner.www.like.entity.Like, com.spinner.www.like.entity.QLike>createList("likes", com.spinner.www.like.entity.Like.class, com.spinner.www.like.entity.QLike.class, PathInits.DIRECT2);

    public final com.spinner.www.member.entity.QMember member;

    //inherited
    public final NumberPath<Long> modifiedAt = _super.modifiedAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final ListPath<com.spinner.www.reply.entity.Reply, com.spinner.www.reply.entity.QReply> replies = this.<com.spinner.www.reply.entity.Reply, com.spinner.www.reply.entity.QReply>createList("replies", com.spinner.www.reply.entity.Reply.class, com.spinner.www.reply.entity.QReply.class, PathInits.DIRECT2);

    public QBoard(String variable) {
        this(Board.class, forVariable(variable), INITS);
    }

    public QBoard(Path<? extends Board> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBoard(PathMetadata metadata, PathInits inits) {
        this(Board.class, metadata, inits);
    }

    public QBoard(Class<? extends Board> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.spinner.www.member.entity.QMember(forProperty("member"), inits.get("member")) : null;
    }

}

