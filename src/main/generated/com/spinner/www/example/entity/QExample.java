package com.spinner.www.example.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QExample is a Querydsl query type for Example
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QExample extends EntityPathBase<Example> {

    private static final long serialVersionUID = -1914915332L;

    public static final QExample example = new QExample("example");

    public final NumberPath<Long> Id = createNumber("Id", Long.class);

    public final StringPath text = createString("text");

    public QExample(String variable) {
        super(Example.class, forVariable(variable));
    }

    public QExample(Path<? extends Example> path) {
        super(path.getType(), path.getMetadata());
    }

    public QExample(PathMetadata metadata) {
        super(Example.class, metadata);
    }

}

