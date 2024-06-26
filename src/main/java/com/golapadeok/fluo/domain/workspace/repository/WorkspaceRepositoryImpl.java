package com.golapadeok.fluo.domain.workspace.repository;

import com.golapadeok.fluo.domain.state.domain.QState;
import com.golapadeok.fluo.domain.state.dto.StateDto;
import com.golapadeok.fluo.domain.tag.domain.QTag;
import com.golapadeok.fluo.domain.tag.dto.TagDto;
import com.golapadeok.fluo.domain.task.domain.QTask;
import com.golapadeok.fluo.domain.task.domain.Task;
import com.golapadeok.fluo.domain.workspace.domain.QWorkspace;
import com.golapadeok.fluo.domain.workspace.dto.SortType;
import com.golapadeok.fluo.domain.workspace.dto.request.CursorPageRequest;
import com.golapadeok.fluo.domain.workspace.dto.request.FilterRequest;
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
import java.util.Collections;
import java.util.List;

@Repository
public class WorkspaceRepositoryImpl {
    private final JPAQueryFactory queryFactory;
    private static final QTask task = QTask.task;

    @Autowired
    public WorkspaceRepositoryImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    public Page<Task> searchPageTasks2(long workspaceId, CursorPageRequest request, FilterRequest filterRequest) {
        OrderSpecifier<?> specifier = getOrderSpecifier(request);

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
                .where(eqTaskWithWorkspaceId(workspaceId),
                        eqTaskWithWorkspaceId(workspaceId),
                        eqPriority(filterRequest.getPriority()),
                        eqStateId(filterRequest.getStateId()),
                        eqEndDate(filterRequest.getEndDate()),
                        eqTagId(filterRequest.getTagId()),
                        eqProjectName(filterRequest.getProjectName()),
                        eqManagerName(filterRequest.getManager()))
                .fetch();

        return new PageImpl<>(content, PageRequest.of(request.getCursorId() + request.getLimit(), request.getLimit()), totals.isEmpty() ? 0 : totals.get(0));
    }

    private static OrderSpecifier<?> getOrderSpecifier(CursorPageRequest request) {
        Order order = request.getAscending().isAscending() ? Order.ASC : Order.DESC;
        return switch (request.getSortType()) {
            case END -> new OrderSpecifier<>(order, task.scheduleRange.endDate);
            case CREATE -> new OrderSpecifier<>(order, task.createDate);
            case PRIORITY -> new OrderSpecifier<>(order, task.configuration.priority);
        };
//        if (request.getSortType() == SortType.END) {
//            specifier = new OrderSpecifier<>(order, task.scheduleRange.endDate);
//        } else if (request.getSortType() == SortType.CREATE) {
//            specifier = new OrderSpecifier<>(order, task.createDate);
//        } else if (request.getSortType() == SortType.PRIORITY) {
//            specifier = new OrderSpecifier<>(order, task.configuration.priority);
//        }
//        return specifier;
    }

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
            return new WorkspaceSearchWithTagsResponse(Collections.emptyList());
        }

        return responses.get(0);
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

    private BooleanExpression eqTaskWithWorkspaceId(long workspaceId) {
        return task.workspace.id.eq(workspaceId);
    }
}
