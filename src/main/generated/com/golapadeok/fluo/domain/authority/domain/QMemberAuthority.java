package com.golapadeok.fluo.domain.authority.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberAuthority is a Querydsl query type for MemberAuthority
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberAuthority extends EntityPathBase<MemberAuthority> {

    private static final long serialVersionUID = -478014756L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberAuthority memberAuthority = new QMemberAuthority("memberAuthority");

    public final com.golapadeok.fluo.common.domain.QBaseTimeEntity _super = new com.golapadeok.fluo.common.domain.QBaseTimeEntity(this);

    public final QAuthority authority;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final com.golapadeok.fluo.domain.customer.domain.QCustomer customer;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public QMemberAuthority(String variable) {
        this(MemberAuthority.class, forVariable(variable), INITS);
    }

    public QMemberAuthority(Path<? extends MemberAuthority> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberAuthority(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberAuthority(PathMetadata metadata, PathInits inits) {
        this(MemberAuthority.class, metadata, inits);
    }

    public QMemberAuthority(Class<? extends MemberAuthority> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.authority = inits.isInitialized("authority") ? new QAuthority(forProperty("authority"), inits.get("authority")) : null;
        this.customer = inits.isInitialized("customer") ? new com.golapadeok.fluo.domain.customer.domain.QCustomer(forProperty("customer")) : null;
    }

}

