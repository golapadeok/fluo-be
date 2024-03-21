package com.golapadeok.fluo.domain.workspace.repository;

import com.golapadeok.fluo.domain.task.domain.QTask;
import com.golapadeok.fluo.domain.task.domain.Task;
import com.golapadeok.fluo.domain.workspace.domain.QWorkspace;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.jsonwebtoken.lang.Assert;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

    public Page<Task> searchPage(long workspaceId, int limit, int offset, boolean ascending) {
        QTask task = QTask.task;
        List<Task> content = queryFactory.selectFrom(task)
                .where(task.workspace.id.eq(workspaceId))
                .orderBy(ascending ? task.createDate.asc() : task.createDate.desc())
                .offset((long) offset * limit)
                .limit(limit)
                .fetch();

        Long totalCount = queryFactory.select(task.count())
                .from(task)
                .where(task.workspace.id.eq(workspaceId))
                .fetchOne();

        Assert.notNull(totalCount, "Total Count must not null");
        return new PageImpl<>(content, PageRequest.of(offset * limit, limit), totalCount);
    }
}
