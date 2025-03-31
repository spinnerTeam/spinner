package com.spinner.www.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberFile is a Querydsl query type for MemberFile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberFile extends EntityPathBase<MemberFile> {

    private static final long serialVersionUID = -1934653338L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberFile memberFile = new QMemberFile("memberFile");

    public final com.spinner.www.file.entity.QFiles files;

    public final QMember member;

    public final NumberPath<Long> memberFileIdx = createNumber("memberFileIdx", Long.class);

    public QMemberFile(String variable) {
        this(MemberFile.class, forVariable(variable), INITS);
    }

    public QMemberFile(Path<? extends MemberFile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberFile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberFile(PathMetadata metadata, PathInits inits) {
        this(MemberFile.class, metadata, inits);
    }

    public QMemberFile(Class<? extends MemberFile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.files = inits.isInitialized("files") ? new com.spinner.www.file.entity.QFiles(forProperty("files")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
    }

}

