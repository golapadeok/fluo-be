package com.golapadeok.fluo.domain.task.domain;

import com.golapadeok.fluo.common.domain.BaseTimeEntity;
import com.golapadeok.fluo.domain.member.domain.Member;
import com.golapadeok.fluo.domain.state.domain.State;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private String tag;
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

    public Task(Long id, String title, String description, String creator, String manager, String tag, TaskConfiguration configuration, ScheduleRange scheduleRange) {
        this.id = id;
        this.title = title;
        this.creator = creator;
        this.manager = manager;
        this.tag = tag;
        this.description = description;
        this.configuration = configuration;
        this.scheduleRange = scheduleRange;
    }

    @Builder(toBuilder = true)
    public Task(String title, String description, String creator, String manager, String tag, TaskConfiguration configuration, ScheduleRange scheduleRange) {
        this(null, title, description, creator, manager, tag, configuration, scheduleRange);
    }

    public void changeState(State state) {
        this.state = state;
    }

    public void changeTask(Task task) {
        this.title = task.getTitle();
        this.creator = task.getCreator();
        this.manager = task.getManager();
        this.tag = task.getTag();
        this.description = task.getDescription();
        this.configuration = task.getConfiguration();
        this.scheduleRange = task.getScheduleRange();
    }

    public void changeWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }
}
