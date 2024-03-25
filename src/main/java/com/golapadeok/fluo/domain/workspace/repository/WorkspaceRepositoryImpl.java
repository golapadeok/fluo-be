package com.golapadeok.fluo.domain.workspace.repository;

import com.golapadeok.fluo.domain.member.domain.QMember;
import com.golapadeok.fluo.domain.member.domain.QWorkspaceMember;
import com.golapadeok.fluo.domain.state.domain.QState;
import com.golapadeok.fluo.domain.state.dto.StateDto;
import com.golapadeok.fluo.domain.tag.domain.QTag;
import com.golapadeok.fluo.domain.tag.dto.TagDto;
import com.golapadeok.fluo.domain.task.domain.QTask;
import com.golapadeok.fluo.domain.task.domain.Task;
import com.golapadeok.fluo.domain.workspace.domain.QWorkspace;
import com.golapadeok.fluo.domain.workspace.dto.CustomPageImpl;
import com.golapadeok.fluo.domain.workspace.dto.MemberDto;
import com.golapadeok.fluo.domain.workspace.dto.request.CursorPageRequest;
import com.golapadeok.fluo.domain.workspace.dto.request.FilterRequest;
import com.golapadeok.fluo.domain.workspace.dto.response.WorkspaceSearchWithMembersResponse;
import com.golapadeok.fluo.domain.workspace.dto.response.WorkspaceSearchWithStatesResponse;
import com.golapadeok.fluo.domain.workspace.dto.response.WorkspaceSearchWithTagsResponse;
import com.querydsl.core.types.Projections;
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

    public WorkspaceSearchWithStatesResponse findWorkspaceWithStages(long workspaceId) {
        QWorkspace workspace = QWorkspace.workspace;
        QState state = QState.state;

        return queryFactory
                .select(Projections.constructor(WorkspaceSearchWithStatesResponse.class,
                        Projections.list(Projections.constructor(StateDto.class, state.id, state.name)))).from(workspace)
                .leftJoin(workspace.states, state)
                .where(workspace.id.eq(workspaceId))
                .fetchOne();
    }

    public WorkspaceSearchWithMembersResponse findWorkspaceWithMembers(long workspaceId) {
        QWorkspace workspace = QWorkspace.workspace;
        QWorkspaceMember workspaceMember = QWorkspaceMember.workspaceMember;

        List<WorkspaceSearchWithMembersResponse> responses = queryFactory
                .select(Projections.constructor(WorkspaceSearchWithMembersResponse.class,
                        Projections.list(Projections.constructor(MemberDto.class, workspaceMember.member.id, workspaceMember.member.email, workspaceMember.member.name, workspaceMember.member.profile)))).from(workspace)
                .leftJoin(workspace.workspaceMembers, workspaceMember)
                .where(workspace.id.eq(workspaceId).and(workspaceMember.isNotNull()))
                .fetch();

        if (responses.isEmpty()) {
            return new WorkspaceSearchWithMembersResponse(null);
        }

        return responses.get(0);
    }

    public WorkspaceSearchWithTagsResponse findWorkspaceWithTags(long workspaceId) {
        QWorkspace workspace = QWorkspace.workspace;
        QTag tag = QTag.tag;

        List<WorkspaceSearchWithTagsResponse> responses = queryFactory
                .select(Projections.constructor(WorkspaceSearchWithTagsResponse.class,
                        Projections.list(Projections.constructor(TagDto.class, tag.id, tag.tagName, tag.colorCode)))).from(workspace)
                .leftJoin(workspace.tags, tag)
                .where(workspace.id.eq(workspaceId).and(tag.isNotNull()))
                .fetch();

        if (responses.isEmpty()) {
            return new WorkspaceSearchWithTagsResponse(null);
        }

        return responses.get(0);
    }
}
