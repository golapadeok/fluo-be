package com.golapadeok.fluo.domain.task.domain;

import com.golapadeok.fluo.common.domain.BaseTimeEntity;
<<<<<<< HEAD
import com.golapadeok.fluo.domain.member.domain.Member;
=======
import com.golapadeok.fluo.domain.customer.domain.Member;
import com.golapadeok.fluo.domain.task.dto.request.TaskRequest;
import com.golapadeok.fluo.domain.workspace.domain.State;
>>>>>>> origin/develop
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Task extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "TASK_ID")
    private Long id;
    private String title;
    private String description;
    private String creator;
    private String manager;
    private Boolean isPrivate;
    private Integer priority;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STATE_ID")
    private State state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WORKSPACE_ID")
    private Workspace workspace;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY)
    private List<ManagerTask> managerTasks = new ArrayList<>();

    @Builder
    public Task(String title, String description, String creator, String manager, Boolean isPrivate, Integer priority, LocalDateTime startDate, LocalDateTime endDate) {
        this.title = title;
        this.description = description;
        this.creator = creator;
        this.manager = manager;
        this.isPrivate = isPrivate;
        this.priority = priority;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void changeState(State state) {
        this.state = state;
    }

    public void updateTask(TaskRequest request) {
        this.title = request.getTitle();
        this.description = request.getDescription();
        this.creator = request.getCreator();
        this.manager = request.convertManager();
        this.priority = request.getPriority();
        this.isPrivate = request.getIsPrivate();
        this.startDate = request.convertStringStartDateToLocalDateTime();
        this.endDate = request.convertStringStartDateToLocalDateTime();
    }
}
