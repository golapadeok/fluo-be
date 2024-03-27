package com.golapadeok.fluo.domain.workspace.repository;

import com.golapadeok.fluo.domain.member.domain.QWorkspaceMember;
import com.golapadeok.fluo.domain.state.domain.QState;
import com.golapadeok.fluo.domain.state.dto.StateDto;
import com.golapadeok.fluo.domain.tag.domain.QTag;
import com.golapadeok.fluo.domain.tag.dto.TagDto;
import com.golapadeok.fluo.domain.task.domain.QTask;
import com.golapadeok.fluo.domain.task.domain.Task;
import com.golapadeok.fluo.domain.workspace.domain.QWorkspace;
import com.golapadeok.fluo.domain.workspace.dto.MemberDto;
import com.golapadeok.fluo.domain.workspace.dto.SortType;
import com.golapadeok.fluo.domain.workspace.dto.request.CursorPageRequest;
import com.golapadeok.fluo.domain.workspace.dto.request.FilterRequest;
import com.golapadeok.fluo.domain.workspace.dto.response.WorkspaceSearchWithMembersResponse;
import com.golapadeok.fluo.domain.workspace.dto.response.WorkspaceSearchWithStatesResponse;
import com.golapadeok.fluo.domain.workspace.dto.response.WorkspaceSearchWithTagsResponse;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.jsonwebtoken.lang.Assert;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
public class WorkspaceRepositoryImpl {
    private final JPAQueryFactory queryFactory;
    private static final QTask task = QTask.task;

    @Autowired
    public WorkspaceRepositoryImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    public Page<Task> searchPageTasks2(Integer workspaceId, CursorPageRequest request, FilterRequest filterRequest) {
        Order order = request.getAscending().isAscending() ? Order.ASC : Order.DESC;
        OrderSpecifier specifier = null;
        if (request.getSortType() == SortType.END) {
            specifier = new OrderSpecifier(order, task.scheduleRange.endDate);
        } else if (request.getSortType() == SortType.CREATE) {
            specifier = new OrderSpecifier(order, task.createDate);
        } else if (request.getSortType() == SortType.PRIORITY) {
            specifier = new OrderSpecifier(order, task.configuration.priority);
        }

        List<Task> content = queryFactory
                .selectFrom(task)
                .where(eqTaskWithWorkspaceId(workspaceId),
                        eqPriority(filterRequest.getPriority()),
                        eqStateId(filterRequest.getStateId()),
                        eqEndDate(filterRequest.getEndDate()),
                        eqTagId(filterRequest.getTagId()),
                        eqProjectName(filterRequest.getProjectName()),
                        eqManagerName(filterRequest.getManager()))
                .orderBy(specifier)
                .orderBy(task.id.desc())
                .offset(request.getCursorId())
                .limit(request.getLimit())
                .fetch();

        List<Long> totals = queryFactory.select(task.count())
                .from(task)
                .where(eqTaskWithWorkspaceId(workspaceId))
                .fetch();

        return new PageImpl<>(content, PageRequest.of(request.getCursorId() + request.getLimit(), request.getLimit()), totals.isEmpty() ? 0 : totals.get(0));
    }

   /* public CustomPageImpl<Task> searchPageTasks(Integer workspaceId, CursorPageRequest pageRequest, FilterRequest filterRequest) {

        final int limit = pageRequest.getLimit();
        final boolean ascending = pageRequest.getAscending();
        final long cursorId = pageRequest.getCursorId();

        List<Task> content = queryFactory
                .selectFrom(task)
                .where(eqTaskWithWorkspaceId(workspaceId),
                        validateCursorId(ascending, cursorId),
                        eqPriority(filterRequest.getPriority()),
                        eqStateId(filterRequest.getStateId()),
                        eqEndDate(filterRequest.getEndDate()),
                        eqTagId(filterRequest.getTagId()),
                        eqProjectName(filterRequest.getProjectName()),
                        eqManagerName(filterRequest.getManager()))
                .orderBy(ascending ? task.createDate.asc() : task.createDate.desc())
                .orderBy(ascending ? task.id.asc() : task.id.desc())
                .limit(limit + 1L)
                .fetch();

        List<Long> totals = queryFactory.select(task.count())
                .from(task)
                .where(eqTaskWithWorkspaceId(workspaceId))
                .fetch();

        long nextCursorId = -1L;
        if (content.size() > limit) {
            content = content.subList(0, content.size() - 1);
            nextCursorId = content.get(content.size() - 1).getId();
        }

        return new CustomPageImpl<>(content, PageRequest.of(0, limit), totals.isEmpty() ? 0 : totals.get(0), nextCursorId);
    }*/

    public WorkspaceSearchWithStatesResponse findWorkspaceWithStates(long workspaceId) {
        QWorkspace workspace = QWorkspace.workspace;
        QState state = QState.state;

        List<WorkspaceSearchWithStatesResponse> fetch = queryFactory
                .select(Projections.constructor(WorkspaceSearchWithStatesResponse.class,
                        Projections.list(Projections.constructor(StateDto.class, state.id, state.name)))).from(workspace)
                .leftJoin(workspace.states, state)
                .where(workspace.id.eq(workspaceId))
                .fetch();

        List<StateDto> list = fetch.stream()
                .flatMap(response -> response.getStates().stream())
                .toList();

        return new WorkspaceSearchWithStatesResponse(list);
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

    private static BooleanExpression validateCursorId(boolean ascending, long cursorId) {
        if (cursorId == 0)
            return null;

        return ascending ? task.id.gt(cursorId) : task.id.lt(cursorId);
    }

    private BooleanExpression eqPriority(Integer priority) {
        if (priority == null)
            return null;

        return task.configuration.priority.eq(priority);
    }

    private BooleanExpression eqStateId(Integer stateId) {
        if (stateId == null)
            return null;

        return task.state.id.eq(stateId.longValue());
    }

    private BooleanExpression eqEndDate(String endDate) {
        if (endDate == null || endDate.isEmpty())
            return null;

        return task.scheduleRange.endDate.eq(LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE).atStartOfDay());
    }

    private BooleanExpression eqTagId(Integer tagId) {
        if (tagId == null)
            return null;

        return task.tag.id.eq(tagId.longValue());
    }

    private BooleanExpression eqProjectName(String projectName) {
        if (projectName == null || projectName.isEmpty())
            return null;

        return task.title.like(projectName);
    }

    private BooleanExpression eqManagerName(String managerName) {
        if (managerName == null || managerName.isEmpty())
            return null;

        return task.managers.any().member.name.like("%" + managerName + "%");
    }

    private BooleanExpression eqTaskWithWorkspaceId(Integer workspaceId) {
        Assert.notNull(workspaceId, "workspace id must not null");
        return task.workspace.id.eq(workspaceId.longValue());
    }
}
