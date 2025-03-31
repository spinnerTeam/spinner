package com.spinner.www.common.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCommonCode is a Querydsl query type for CommonCode
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCommonCode extends EntityPathBase<CommonCode> {

    private static final long serialVersionUID = -1856725831L;

    public static final QCommonCode commonCode = new QCommonCode("commonCode");

    public final NumberPath<Long> codeIdx = createNumber("codeIdx", Long.class);

    public final StringPath codeName = createString("codeName");

    public final NumberPath<Integer> codeSort = createNumber("codeSort", Integer.class);

    public final StringPath codeType = createString("codeType");

    public final BooleanPath codeUse = createBoolean("codeUse");

    public QCommonCode(String variable) {
        super(CommonCode.class, forVariable(variable));
    }

    public QCommonCode(Path<? extends CommonCode> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCommonCode(PathMetadata metadata) {
        super(CommonCode.class, metadata);
    }

}

