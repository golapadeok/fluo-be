package com.golapadeok.fluo.domain.workspace.repository;

import com.golapadeok.fluo.domain.task.domain.QTask;
import com.golapadeok.fluo.domain.task.domain.Task;
import com.golapadeok.fluo.domain.workspace.dto.CustomPageImpl;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.jsonwebtoken.lang.Assert;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.function.Function;

@Repository
public class WorkspaceRepositoryImpl implements WorkspaceRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Autowired
    public WorkspaceRepositoryImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    public CustomPageImpl<Task> searchPageTasks(long workspaceId, int limit, int cursorId, boolean ascending) {
        QTask task = QTask.task;
        List<Task> content;

        JPAQuery<Task> defaultQuery = queryFactory.selectFrom(task)
                .where(task.workspace.id.eq(workspaceId));

        if (cursorId != 0) {
            defaultQuery = defaultQuery.where(ascending ? task.id.gt(cursorId) : task.id.lt(cursorId));
        }

        content = defaultQuery
                .orderBy(ascending ? task.createDate.asc() : task.createDate.desc())
                .orderBy(ascending ? task.id.asc() : task.id.desc())
                .limit(limit + 1L)
                .fetch();

        Long totalCount = queryFactory.select(task.count())
                .from(task)
                .where(task.workspace.id.eq(workspaceId))
                .fetchOne();
        Assert.notNull(totalCount, "Total Count must not null");

        long nextCursorId = -1L;
        if (content.size() > limit) {
            content = content.subList(0, content.size() - 1);
            nextCursorId = content.get(content.size() - 1).getId();
        }

        return new CustomPageImpl<>(content, PageRequest.of(0, limit), totalCount, nextCursorId);
    }
}
