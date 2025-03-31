package com.spinner.www.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberInterest is a Querydsl query type for MemberInterest
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberInterest extends EntityPathBase<MemberInterest> {

    private static final long serialVersionUID = 1833415956L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberInterest memberInterest = new QMemberInterest("memberInterest");

    public final QMember member;

    public final NumberPath<Long> memberInterestIdx = createNumber("memberInterestIdx", Long.class);

    public final com.spinner.www.common.entity.QMenu menu;

    public QMemberInterest(String variable) {
        this(MemberInterest.class, forVariable(variable), INITS);
    }

    public QMemberInterest(Path<? extends MemberInterest> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberInterest(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberInterest(PathMetadata metadata, PathInits inits) {
        this(MemberInterest.class, metadata, inits);
    }

    public QMemberInterest(Class<? extends MemberInterest> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
        this.menu = inits.isInitialized("menu") ? new com.spinner.www.common.entity.QMenu(forProperty("menu"), inits.get("menu")) : null;
    }

}

