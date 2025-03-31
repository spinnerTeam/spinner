package com.spinner.www.file.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFiles is a Querydsl query type for Files
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFiles extends EntityPathBase<Files> {

    private static final long serialVersionUID = 1273556325L;

    public static final QFiles files = new QFiles("files");

    public final com.spinner.www.common.entity.QBaseEntity _super = new com.spinner.www.common.entity.QBaseEntity(this);

    public final NumberPath<Long> codeIdx = createNumber("codeIdx", Long.class);

    //inherited
    public final NumberPath<Long> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath fileConvertName = createString("fileConvertName");

    public final NumberPath<Long> fileIdx = createNumber("fileIdx", Long.class);

    public final StringPath fileOriginName = createString("fileOriginName");

    public final StringPath filePath = createString("filePath");

    //inherited
    public final NumberPath<Long> modifiedAt = _super.modifiedAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public QFiles(String variable) {
        super(Files.class, forVariable(variable));
    }

    public QFiles(Path<? extends Files> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFiles(PathMetadata metadata) {
        super(Files.class, metadata);
    }

}

