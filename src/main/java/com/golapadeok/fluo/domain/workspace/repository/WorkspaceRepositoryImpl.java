package com.golapadeok.fluo.domain.workspace.repository;

import com.golapadeok.fluo.domain.task.domain.QTask;
import com.golapadeok.fluo.domain.task.domain.Task;
import com.golapadeok.fluo.domain.workspace.dto.CustomPageImpl;
import com.golapadeok.fluo.domain.workspace.dto.request.CursorPageRequest;
import com.golapadeok.fluo.domain.workspace.dto.request.FilterRequest;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.jsonwebtoken.lang.Assert;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WorkspaceRepositoryImpl implements WorkspaceRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Autowired
    public WorkspaceRepositoryImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    public CustomPageImpl<Task> searchPageTasks(long workspaceId, int limit, int cursorId, boolean ascending) {
        QTask task = QTask.task;
        JPAQuery<Task> defaultQuery = queryFactory.selectFrom(task)
                .where(task.workspace.id.eq(workspaceId));

        if (cursorId != 0) {
            defaultQuery = defaultQuery.where(ascending ? task.id.gt(cursorId) : task.id.lt(cursorId));
        }

        List<Task> content = defaultQuery
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

    @Override
    public CustomPageImpl<Task> searchPageTasks(Integer workspaceId, CursorPageRequest pageRequest, FilterRequest filterRequest) {
        QTask task = QTask.task;

        final boolean ascending = pageRequest.getAscending();

        JPAQuery<Task> defaultQuery = queryFactory.selectFrom(task)
                .where(task.workspace.id.eq(workspaceId.longValue()));

        final long cursorId = pageRequest.getCursorId();
        if (cursorId != 0) {
            defaultQuery = defaultQuery.where(ascending ? task.id.gt(cursorId) : task.id.lt(cursorId));
        }

        if (filterRequest.getPriority() != null) {
            defaultQuery = defaultQuery.where(task.configuration.priority.eq(filterRequest.getPriority()));
        }

        if (filterRequest.getStateId() != null) {
            defaultQuery = defaultQuery.where(task.state.id.eq(filterRequest.getStateId().longValue()));
        }

//        if (filterRequest.getManager() != null) {
//            // TODO KDY like 사용에 대해 고민해봐야할 듯
//            defaultQuery = defaultQuery.where(task.configuration.manager.like("%" + filterRequest.getManager() + "%"));
//        }

        // TODO KDY 날짜는 조건이 어떻게 들어올지 모르겠다
//        if (filterRequest.getEndDate() != null) {
//            defaultQuery = defaultQuery.where(task.configuration.priority.eq(filterRequest.getPriority()));
//        }

        // TODO KDY TAG 구현이 필요하다.
//        if (filterRequest.getTag() != null) {
//            defaultQuery = defaultQuery.where(task..priority.eq(filterRequest.getPriority()));
//        }


        if (filterRequest.getProjectName() != null) {
            defaultQuery = defaultQuery.where(task.title.like(filterRequest.getProjectName()));
        }

        List<Task> content = defaultQuery
                .orderBy(ascending ? task.createDate.asc() : task.createDate.desc())
                .orderBy(ascending ? task.id.asc() : task.id.desc())
                .limit(pageRequest.getLimit() + 1L)
                .fetch();

        Long totalCount = queryFactory.select(task.count())
                .from(task)
                .where(task.workspace.id.eq(workspaceId.longValue()))
                .fetchOne();
        Assert.notNull(totalCount, "Total Count must not null");

        long nextCursorId = -1L;
        if (content.size() > pageRequest.getLimit()) {
            content = content.subList(0, content.size() - 1);
            nextCursorId = content.get(content.size() - 1).getId();
        }

        return new CustomPageImpl<>(content, PageRequest.of(0, pageRequest.getLimit()), totalCount, nextCursorId);
    }

}
