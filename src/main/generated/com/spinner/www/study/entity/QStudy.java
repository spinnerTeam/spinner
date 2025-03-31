package com.spinner.www.study.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStudy is a Querydsl query type for Study
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStudy extends EntityPathBase<Study> {

    private static final long serialVersionUID = -779751652L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStudy study = new QStudy("study");

    public final com.spinner.www.common.entity.QBaseEntity _super = new com.spinner.www.common.entity.QBaseEntity(this);

    public final com.spinner.www.common.entity.QCommonCode common;

    //inherited
    public final NumberPath<Long> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final com.spinner.www.file.entity.QFiles files;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final NumberPath<Long> modifiedAt = _super.modifiedAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath studyIntro = createString("studyIntro");

    public final StringPath studyIsRemoved = createString("studyIsRemoved");

    public final NumberPath<Integer> studyMaxPeople = createNumber("studyMaxPeople", Integer.class);

    public final ListPath<StudyMember, QStudyMember> studyMembers = this.<StudyMember, QStudyMember>createList("studyMembers", StudyMember.class, QStudyMember.class, PathInits.DIRECT2);

    public final StringPath studyName = createString("studyName");

    public final EnumPath<com.spinner.www.study.constants.StudyStatusType> studyStatusType = createEnum("studyStatusType", com.spinner.www.study.constants.StudyStatusType.class);

    public final NumberPath<Long> studyViews = createNumber("studyViews", Long.class);

    public QStudy(String variable) {
        this(Study.class, forVariable(variable), INITS);
    }

    public QStudy(Path<? extends Study> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStudy(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStudy(PathMetadata metadata, PathInits inits) {
        this(Study.class, metadata, inits);
    }

    public QStudy(Class<? extends Study> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.common = inits.isInitialized("common") ? new com.spinner.www.common.entity.QCommonCode(forProperty("common")) : null;
        this.files = inits.isInitialized("files") ? new com.spinner.www.file.entity.QFiles(forProperty("files")) : null;
    }

}

