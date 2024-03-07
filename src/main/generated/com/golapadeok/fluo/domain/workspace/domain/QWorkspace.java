package com.golapadeok.fluo.domain.workspace.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWorkspace is a Querydsl query type for Workspace
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWorkspace extends EntityPathBase<Workspace> {

    private static final long serialVersionUID = -875083562L;

    public static final QWorkspace workspace = new QWorkspace("workspace");

    public final com.golapadeok.fluo.common.domain.QBaseTimeEntity _super = new com.golapadeok.fluo.common.domain.QBaseTimeEntity(this);

    public final ListPath<com.golapadeok.fluo.domain.authority.domain.Authority, com.golapadeok.fluo.domain.authority.domain.QAuthority> authorities = this.<com.golapadeok.fluo.domain.authority.domain.Authority, com.golapadeok.fluo.domain.authority.domain.QAuthority>createList("authorities", com.golapadeok.fluo.domain.authority.domain.Authority.class, com.golapadeok.fluo.domain.authority.domain.QAuthority.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final ListPath<com.golapadeok.fluo.domain.task.domain.Task, com.golapadeok.fluo.domain.task.domain.QTask> tasks = this.<com.golapadeok.fluo.domain.task.domain.Task, com.golapadeok.fluo.domain.task.domain.QTask>createList("tasks", com.golapadeok.fluo.domain.task.domain.Task.class, com.golapadeok.fluo.domain.task.domain.QTask.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    public final ListPath<com.golapadeok.fluo.domain.customer.domain.WorkspaceMember, com.golapadeok.fluo.domain.customer.domain.QWorkspaceMember> workspaceMembers = this.<com.golapadeok.fluo.domain.customer.domain.WorkspaceMember, com.golapadeok.fluo.domain.customer.domain.QWorkspaceMember>createList("workspaceMembers", com.golapadeok.fluo.domain.customer.domain.WorkspaceMember.class, com.golapadeok.fluo.domain.customer.domain.QWorkspaceMember.class, PathInits.DIRECT2);

    public QWorkspace(String variable) {
        super(Workspace.class, forVariable(variable));
    }

    public QWorkspace(Path<? extends Workspace> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWorkspace(PathMetadata metadata) {
        super(Workspace.class, metadata);
    }

}

