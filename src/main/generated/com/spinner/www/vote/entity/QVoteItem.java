package com.spinner.www.vote.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVoteItem is a Querydsl query type for VoteItem
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVoteItem extends EntityPathBase<VoteItem> {

    private static final long serialVersionUID = -256230179L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QVoteItem voteItem = new QVoteItem("voteItem");

    public final com.spinner.www.common.entity.QBaseEntity _super = new com.spinner.www.common.entity.QBaseEntity(this);

    //inherited
    public final NumberPath<Long> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final NumberPath<Long> modifiedAt = _super.modifiedAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final QVote vote;

    public final StringPath voteItemIsRemoved = createString("voteItemIsRemoved");

    public final StringPath voteItemName = createString("voteItemName");

    public QVoteItem(String variable) {
        this(VoteItem.class, forVariable(variable), INITS);
    }

    public QVoteItem(Path<? extends VoteItem> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QVoteItem(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QVoteItem(PathMetadata metadata, PathInits inits) {
        this(VoteItem.class, metadata, inits);
    }

    public QVoteItem(Class<? extends VoteItem> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.vote = inits.isInitialized("vote") ? new QVote(forProperty("vote"), inits.get("vote")) : null;
    }

}

