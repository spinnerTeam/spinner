package com.spinner.www.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEmailLog is a Querydsl query type for EmailLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEmailLog extends EntityPathBase<EmailLog> {

    private static final long serialVersionUID = -2108758216L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEmailLog emailLog = new QEmailLog("emailLog");

    public final NumberPath<Long> emailLogIdx = createNumber("emailLogIdx", Long.class);

    public final BooleanPath emailLogStatus = createBoolean("emailLogStatus");

    public final DateTimePath<java.time.ZonedDateTime> emailSendDate = createDateTime("emailSendDate", java.time.ZonedDateTime.class);

    public final QEmailTemplate emailTemplate;

    public final StringPath toEmail = createString("toEmail");

    public QEmailLog(String variable) {
        this(EmailLog.class, forVariable(variable), INITS);
    }

    public QEmailLog(Path<? extends EmailLog> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEmailLog(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEmailLog(PathMetadata metadata, PathInits inits) {
        this(EmailLog.class, metadata, inits);
    }

    public QEmailLog(Class<? extends EmailLog> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.emailTemplate = inits.isInitialized("emailTemplate") ? new QEmailTemplate(forProperty("emailTemplate")) : null;
    }

}

