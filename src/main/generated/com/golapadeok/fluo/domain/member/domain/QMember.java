package com.golapadeok.fluo.domain.member.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -2040070878L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMember member = new QMember("member1");

    public final com.golapadeok.fluo.common.domain.QBaseTimeEntity _super = new com.golapadeok.fluo.common.domain.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<com.golapadeok.fluo.domain.invitation.domain.Invitation, com.golapadeok.fluo.domain.invitation.domain.QInvitation> invitations = this.<com.golapadeok.fluo.domain.invitation.domain.Invitation, com.golapadeok.fluo.domain.invitation.domain.QInvitation>createList("invitations", com.golapadeok.fluo.domain.invitation.domain.Invitation.class, com.golapadeok.fluo.domain.invitation.domain.QInvitation.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public final StringPath profile = createString("profile");

    public final StringPath refreshToken = createString("refreshToken");

    public final QSocialId socialId;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    public final ListPath<WorkspaceMember, QWorkspaceMember> workspaceMembers = this.<WorkspaceMember, QWorkspaceMember>createList("workspaceMembers", WorkspaceMember.class, QWorkspaceMember.class, PathInits.DIRECT2);

    public QMember(String variable) {
        this(Member.class, forVariable(variable), INITS);
    }

    public QMember(Path<? extends Member> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMember(PathMetadata metadata, PathInits inits) {
        this(Member.class, metadata, inits);
    }

    public QMember(Class<? extends Member> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.socialId = inits.isInitialized("socialId") ? new QSocialId(forProperty("socialId")) : null;
    }

}

