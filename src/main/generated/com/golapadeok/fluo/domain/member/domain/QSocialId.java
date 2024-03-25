package com.golapadeok.fluo.domain.member.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSocialId is a Querydsl query type for SocialId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QSocialId extends BeanPath<SocialId> {

    private static final long serialVersionUID = -130946576L;

    public static final QSocialId socialId1 = new QSocialId("socialId1");

    public final StringPath socialId = createString("socialId");

    public final EnumPath<com.golapadeok.fluo.domain.social.domain.SocialType> socialType = createEnum("socialType", com.golapadeok.fluo.domain.social.domain.SocialType.class);

    public QSocialId(String variable) {
        super(SocialId.class, forVariable(variable));
    }

    public QSocialId(Path<? extends SocialId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSocialId(PathMetadata metadata) {
        super(SocialId.class, metadata);
    }

}

