package com.spinner.www.vote.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVoteUser is a Querydsl query type for VoteUser
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVoteUser extends EntityPathBase<VoteUser> {

    private static final long serialVersionUID = -255873643L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QVoteUser voteUser = new QVoteUser("voteUser");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.spinner.www.member.entity.QMember member;

    public final QVote vote;

    public final QVoteItem voteItem;

    public QVoteUser(String variable) {
        this(VoteUser.class, forVariable(variable), INITS);
    }

    public QVoteUser(Path<? extends VoteUser> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QVoteUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QVoteUser(PathMetadata metadata, PathInits inits) {
        this(VoteUser.class, metadata, inits);
    }

    public QVoteUser(Class<? extends VoteUser> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.spinner.www.member.entity.QMember(forProperty("member"), inits.get("member")) : null;
        this.vote = inits.isInitialized("vote") ? new QVote(forProperty("vote"), inits.get("vote")) : null;
        this.voteItem = inits.isInitialized("voteItem") ? new QVoteItem(forProperty("voteItem"), inits.get("voteItem")) : null;
    }

}

