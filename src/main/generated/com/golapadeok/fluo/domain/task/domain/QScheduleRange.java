package com.golapadeok.fluo.domain.task.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QScheduleRange is a Querydsl query type for ScheduleRange
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QScheduleRange extends BeanPath<ScheduleRange> {

    private static final long serialVersionUID = -84915501L;

    public static final QScheduleRange scheduleRange = new QScheduleRange("scheduleRange");

    public final DateTimePath<java.time.LocalDateTime> endDate = createDateTime("endDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> startDate = createDateTime("startDate", java.time.LocalDateTime.class);

    public QScheduleRange(String variable) {
        super(ScheduleRange.class, forVariable(variable));
    }

    public QScheduleRange(Path<? extends ScheduleRange> path) {
        super(path.getType(), path.getMetadata());
    }

    public QScheduleRange(PathMetadata metadata) {
        super(ScheduleRange.class, metadata);
    }

}

