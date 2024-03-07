package com.golapadeok.fluo.domain.task.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QManagerTask is a Querydsl query type for ManagerTask
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QManagerTask extends EntityPathBase<ManagerTask> {

    private static final long serialVersionUID = 104480767L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QManagerTask managerTask = new QManagerTask("managerTask");

    public final com.golapadeok.fluo.common.domain.QBaseTimeEntity _super = new com.golapadeok.fluo.common.domain.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final com.golapadeok.fluo.domain.customer.domain.QCustomer customer;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final QTask task;

    public QManagerTask(String variable) {
        this(ManagerTask.class, forVariable(variable), INITS);
    }

    public QManagerTask(Path<? extends ManagerTask> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QManagerTask(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QManagerTask(PathMetadata metadata, PathInits inits) {
        this(ManagerTask.class, metadata, inits);
    }

    public QManagerTask(Class<? extends ManagerTask> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.customer = inits.isInitialized("customer") ? new com.golapadeok.fluo.domain.customer.domain.QCustomer(forProperty("customer")) : null;
        this.task = inits.isInitialized("task") ? new QTask(forProperty("task"), inits.get("task")) : null;
    }

}

