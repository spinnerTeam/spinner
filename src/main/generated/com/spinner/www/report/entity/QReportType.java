package com.spinner.www.report.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QReportType is a Querydsl query type for ReportType
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReportType extends EntityPathBase<ReportType> {

    private static final long serialVersionUID = -777050152L;

    public static final QReportType reportType = new QReportType("reportType");

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

    public final StringPath reportTypeContent = createString("reportTypeContent");

    public final StringPath reportTypeIsRemoved = createString("reportTypeIsRemoved");

    public QReportType(String variable) {
        super(ReportType.class, forVariable(variable));
    }

    public QReportType(Path<? extends ReportType> path) {
        super(path.getType(), path.getMetadata());
    }

    public QReportType(PathMetadata metadata) {
        super(ReportType.class, metadata);
    }

}

