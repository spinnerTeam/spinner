package com.spinner.www.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QServiceTerms is a Querydsl query type for ServiceTerms
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QServiceTerms extends EntityPathBase<ServiceTerms> {

    private static final long serialVersionUID = 1638097154L;

    public static final QServiceTerms serviceTerms = new QServiceTerms("serviceTerms");

    public final com.spinner.www.common.entity.QBaseEntity _super = new com.spinner.www.common.entity.QBaseEntity(this);

    //inherited
    public final NumberPath<Long> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    //inherited
    public final NumberPath<Long> modifiedAt = _super.modifiedAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath serviceTermsContent = createString("serviceTermsContent");

    public final NumberPath<Long> serviceTermsIdx = createNumber("serviceTermsIdx", Long.class);

    public final BooleanPath serviceTermsIsUse = createBoolean("serviceTermsIsUse");

    public final StringPath serviceTermsTitle = createString("serviceTermsTitle");

    public final EnumPath<com.spinner.www.member.constants.ServiceTermsType> serviceTermsType = createEnum("serviceTermsType", com.spinner.www.member.constants.ServiceTermsType.class);

    public QServiceTerms(String variable) {
        super(ServiceTerms.class, forVariable(variable));
    }

    public QServiceTerms(Path<? extends ServiceTerms> path) {
        super(path.getType(), path.getMetadata());
    }

    public QServiceTerms(PathMetadata metadata) {
        super(ServiceTerms.class, metadata);
    }

}

