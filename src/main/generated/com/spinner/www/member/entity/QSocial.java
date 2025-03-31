package com.spinner.www.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSocial is a Querydsl query type for Social
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSocial extends EntityPathBase<Social> {

    private static final long serialVersionUID = 16162973L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSocial social = new QSocial("social");

    public final QMember member;

    public final NumberPath<Long> sIdx = createNumber("sIdx", Long.class);

    public final StringPath socialNum = createString("socialNum");

    public final EnumPath<com.spinner.www.member.constants.SocialType> socialtype = createEnum("socialtype", com.spinner.www.member.constants.SocialType.class);

    public QSocial(String variable) {
        this(Social.class, forVariable(variable), INITS);
    }

    public QSocial(Path<? extends Social> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSocial(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSocial(PathMetadata metadata, PathInits inits) {
        this(Social.class, metadata, inits);
    }

    public QSocial(Class<? extends Social> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
    }

}

