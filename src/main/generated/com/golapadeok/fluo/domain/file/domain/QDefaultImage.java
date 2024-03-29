package com.golapadeok.fluo.domain.file.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDefaultImage is a Querydsl query type for DefaultImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDefaultImage extends EntityPathBase<DefaultImage> {

    private static final long serialVersionUID = -524336508L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDefaultImage defaultImage = new QDefaultImage("defaultImage");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath url = createString("url");

    public final com.golapadeok.fluo.domain.workspace.domain.QWorkspace workspace;

    public QDefaultImage(String variable) {
        this(DefaultImage.class, forVariable(variable), INITS);
    }

    public QDefaultImage(Path<? extends DefaultImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDefaultImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDefaultImage(PathMetadata metadata, PathInits inits) {
        this(DefaultImage.class, metadata, inits);
    }

    public QDefaultImage(Class<? extends DefaultImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.workspace = inits.isInitialized("workspace") ? new com.golapadeok.fluo.domain.workspace.domain.QWorkspace(forProperty("workspace")) : null;
    }

}

