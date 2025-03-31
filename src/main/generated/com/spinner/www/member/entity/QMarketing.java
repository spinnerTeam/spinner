package com.spinner.www.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMarketing is a Querydsl query type for Marketing
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMarketing extends EntityPathBase<Marketing> {

    private static final long serialVersionUID = 294023926L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMarketing marketing = new QMarketing("marketing");

    public final BooleanPath marketingConsent = createBoolean("marketingConsent");

    public final DateTimePath<java.time.ZonedDateTime> marketingDate = createDateTime("marketingDate", java.time.ZonedDateTime.class);

    public final NumberPath<Long> marketingIdx = createNumber("marketingIdx", Long.class);

    public final QMember member;

    public final QServiceTerms serviceTerms;

    public QMarketing(String variable) {
        this(Marketing.class, forVariable(variable), INITS);
    }

    public QMarketing(Path<? extends Marketing> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMarketing(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMarketing(PathMetadata metadata, PathInits inits) {
        this(Marketing.class, metadata, inits);
    }

    public QMarketing(Class<? extends Marketing> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
        this.serviceTerms = inits.isInitialized("serviceTerms") ? new QServiceTerms(forProperty("serviceTerms")) : null;
    }

}

