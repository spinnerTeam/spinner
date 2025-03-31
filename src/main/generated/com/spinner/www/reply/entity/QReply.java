package com.spinner.www.reply.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReply is a Querydsl query type for Reply
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReply extends EntityPathBase<Reply> {

    private static final long serialVersionUID = -1175647044L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReply reply = new QReply("reply");

    public final com.spinner.www.common.entity.QBaseEntity _super = new com.spinner.www.common.entity.QBaseEntity(this);

    public final NumberPath<Long> boardIdx = createNumber("boardIdx", Long.class);

    public final ListPath<Reply, QReply> childReplies = this.<Reply, QReply>createList("childReplies", Reply.class, QReply.class, PathInits.DIRECT2);

    //inherited
    public final NumberPath<Long> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final ListPath<com.spinner.www.like.entity.Like, com.spinner.www.like.entity.QLike> likes = this.<com.spinner.www.like.entity.Like, com.spinner.www.like.entity.QLike>createList("likes", com.spinner.www.like.entity.Like.class, com.spinner.www.like.entity.QLike.class, PathInits.DIRECT2);

    public final com.spinner.www.member.entity.QMember member;

    //inherited
    public final NumberPath<Long> modifiedAt = _super.modifiedAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath replyContent = createString("replyContent");

    public final NumberPath<Long> replyIdx = createNumber("replyIdx", Long.class);

    public final NumberPath<Integer> replyIsRemoved = createNumber("replyIsRemoved", Integer.class);

    public final NumberPath<Long> replyParentIdx = createNumber("replyParentIdx", Long.class);

    public QReply(String variable) {
        this(Reply.class, forVariable(variable), INITS);
    }

    public QReply(Path<? extends Reply> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReply(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReply(PathMetadata metadata, PathInits inits) {
        this(Reply.class, metadata, inits);
    }

    public QReply(Class<? extends Reply> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.spinner.www.member.entity.QMember(forProperty("member"), inits.get("member")) : null;
    }

}

