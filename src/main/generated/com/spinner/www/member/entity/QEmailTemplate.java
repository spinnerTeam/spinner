package com.spinner.www.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QEmailTemplate is a Querydsl query type for EmailTemplate
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEmailTemplate extends EntityPathBase<EmailTemplate> {

    private static final long serialVersionUID = -1944198906L;

    public static final QEmailTemplate emailTemplate = new QEmailTemplate("emailTemplate");

    public final com.spinner.www.common.entity.QBaseEntity _super = new com.spinner.www.common.entity.QBaseEntity(this);

    //inherited
    public final NumberPath<Long> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath emailTemplateBody = createString("emailTemplateBody");

    public final NumberPath<Integer> emailTemplateIdx = createNumber("emailTemplateIdx", Integer.class);

    public final StringPath emailTemplateName = createString("emailTemplateName");

    public final StringPath emailTemplateTitle = createString("emailTemplateTitle");

    public final EnumPath<com.spinner.www.member.constants.EmailTemplateType> emailTemplateType = createEnum("emailTemplateType", com.spinner.www.member.constants.EmailTemplateType.class);

    //inherited
    public final NumberPath<Long> modifiedAt = _super.modifiedAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public QEmailTemplate(String variable) {
        super(EmailTemplate.class, forVariable(variable));
    }

    public QEmailTemplate(Path<? extends EmailTemplate> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEmailTemplate(PathMetadata metadata) {
        super(EmailTemplate.class, metadata);
    }

}

