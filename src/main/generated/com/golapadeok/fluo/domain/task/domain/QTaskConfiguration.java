package com.golapadeok.fluo.domain.task.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTaskConfiguration is a Querydsl query type for TaskConfiguration
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QTaskConfiguration extends BeanPath<TaskConfiguration> {

    private static final long serialVersionUID = 1308630750L;

    public static final QTaskConfiguration taskConfiguration = new QTaskConfiguration("taskConfiguration");

    public final BooleanPath isPrivate = createBoolean("isPrivate");

    public final NumberPath<Integer> priority = createNumber("priority", Integer.class);

    public QTaskConfiguration(String variable) {
        super(TaskConfiguration.class, forVariable(variable));
    }

    public QTaskConfiguration(Path<? extends TaskConfiguration> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTaskConfiguration(PathMetadata metadata) {
        super(TaskConfiguration.class, metadata);
    }

}

