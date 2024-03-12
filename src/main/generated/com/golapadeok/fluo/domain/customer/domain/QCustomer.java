package com.golapadeok.fluo.domain.customer.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCustomer is a Querydsl query type for Customer
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCustomer extends EntityPathBase<Member> {

    private static final long serialVersionUID = -2092486358L;

    public static final QCustomer customer = new QCustomer("customer");

    public final com.golapadeok.fluo.common.domain.QBaseTimeEntity _super = new com.golapadeok.fluo.common.domain.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath name = createString("name");

    public final StringPath profile = createString("profile");

    public final ListPath<WorkspaceMember, QWorkspaceMember> workspaceMembers = this.<WorkspaceMember, QWorkspaceMember>createList("workspaceMembers", WorkspaceMember.class, QWorkspaceMember.class, PathInits.DIRECT2);

    public QCustomer(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QCustomer(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCustomer(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

