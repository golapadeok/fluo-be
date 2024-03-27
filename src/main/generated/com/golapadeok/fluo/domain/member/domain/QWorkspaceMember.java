package com.golapadeok.fluo.domain.member.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWorkspaceMember is a Querydsl query type for WorkspaceMember
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWorkspaceMember extends EntityPathBase<WorkspaceMember> {

    private static final long serialVersionUID = -1814116857L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWorkspaceMember workspaceMember = new QWorkspaceMember("workspaceMember");

    public final com.golapadeok.fluo.common.domain.QBaseTimeEntity _super = new com.golapadeok.fluo.common.domain.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath latestUrl = createString("latestUrl");

    public final QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    public final com.golapadeok.fluo.domain.workspace.domain.QWorkspace workspace;

    public QWorkspaceMember(String variable) {
        this(WorkspaceMember.class, forVariable(variable), INITS);
    }

    public QWorkspaceMember(Path<? extends WorkspaceMember> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWorkspaceMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWorkspaceMember(PathMetadata metadata, PathInits inits) {
        this(WorkspaceMember.class, metadata, inits);
    }

    public QWorkspaceMember(Class<? extends WorkspaceMember> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
        this.workspace = inits.isInitialized("workspace") ? new com.golapadeok.fluo.domain.workspace.domain.QWorkspace(forProperty("workspace")) : null;
    }

}

