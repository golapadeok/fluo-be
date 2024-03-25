package com.golapadeok.fluo.domain.member.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QInvitation is a Querydsl query type for Invitation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInvitation extends EntityPathBase<Invitation> {

    private static final long serialVersionUID = 1775724065L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QInvitation invitation = new QInvitation("invitation");

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isPending = createBoolean("isPending");

    public final QMember member;

    public final com.golapadeok.fluo.domain.workspace.domain.QWorkspace workspace;

    public QInvitation(String variable) {
        this(Invitation.class, forVariable(variable), INITS);
    }

    public QInvitation(Path<? extends Invitation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QInvitation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QInvitation(PathMetadata metadata, PathInits inits) {
        this(Invitation.class, metadata, inits);
    }

    public QInvitation(Class<? extends Invitation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
        this.workspace = inits.isInitialized("workspace") ? new com.golapadeok.fluo.domain.workspace.domain.QWorkspace(forProperty("workspace")) : null;
    }

}

