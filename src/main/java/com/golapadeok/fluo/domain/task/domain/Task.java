package com.golapadeok.fluo.domain.task.domain;

import com.golapadeok.fluo.common.domain.BaseTimeEntity;
import com.golapadeok.fluo.domain.member.domain.Member;
import com.golapadeok.fluo.domain.state.domain.State;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Embedded
    private TaskConfiguration configuration;

    @Embedded
    private ScheduleRange scheduleRange;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STATE_ID")
    private State state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WORKSPACE_ID")
    private Workspace workspace;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    public Task(Long id, String title, String description, TaskConfiguration configuration, ScheduleRange scheduleRange) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.configuration = configuration;
        this.scheduleRange = scheduleRange;
    }

    public Task(String title, String description, TaskConfiguration configuration, ScheduleRange scheduleRange) {
        this(null, title, description, configuration, scheduleRange);
    }

    public void changeState(State state) {
        this.state = state;
    }

    public void changeWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    public void changeTaskConfiguration(TaskConfiguration configuration) {
        this.configuration = configuration;
    }

    public void changeScheduleRange(ScheduleRange scheduleRange) {
        this.scheduleRange = scheduleRange;
    }

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeDescription(String description) {
        this.description = description;
    }
}
