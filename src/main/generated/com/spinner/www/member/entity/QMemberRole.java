package com.spinner.www.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMemberRole is a Querydsl query type for MemberRole
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberRole extends EntityPathBase<MemberRole> {

    private static final long serialVersionUID = -1934290080L;

    public static final QMemberRole memberRole = new QMemberRole("memberRole");

    public final NumberPath<Integer> roleIdx = createNumber("roleIdx", Integer.class);

    public final EnumPath<com.spinner.www.member.constants.RoleName> roleName = createEnum("roleName", com.spinner.www.member.constants.RoleName.class);

    public QMemberRole(String variable) {
        super(MemberRole.class, forVariable(variable));
    }

    public QMemberRole(Path<? extends MemberRole> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMemberRole(PathMetadata metadata) {
        super(MemberRole.class, metadata);
    }

}

