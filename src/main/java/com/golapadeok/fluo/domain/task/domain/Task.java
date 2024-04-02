package com.golapadeok.fluo.domain.task.domain;

import com.golapadeok.fluo.common.domain.BaseTimeEntity;
import com.golapadeok.fluo.domain.member.domain.Member;
import com.golapadeok.fluo.domain.state.domain.State;
import com.golapadeok.fluo.domain.tag.domain.Tag;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.catalina.Manager;

import java.awt.*;
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

    @Enumerated(EnumType.STRING)
    private LabelColor labelColor;

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

    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY)
    private List<ManagerTask> managers = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TAG_ID")
    private Tag tag;

    public Task(Long id, String title, String description, String creator, LabelColor labelColor, TaskConfiguration configuration, ScheduleRange scheduleRange) {
        this.id = id;
        this.title = title;
        this.creator = creator;
        this.description = description;
        this.labelColor = labelColor;
        this.configuration = configuration;
        this.scheduleRange = scheduleRange;
    }

    @Builder(toBuilder = true)
    public Task(String title, String description, String creator, LabelColor labelColor, TaskConfiguration configuration, ScheduleRange scheduleRange) {
        this(null, title, description, creator, labelColor, configuration, scheduleRange);
    }

    public void changeState(State state) {
        this.state = state;
    }

    public void changeWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    public void changeTag(Tag tag) {
        this.tag = tag;
    }

    public void changeTask(Task task) {
        this.title = task.getTitle();
        this.creator = task.getCreator();
        this.description = task.getDescription();
        this.labelColor = task.getLabelColor();
        this.configuration = task.getConfiguration();
        this.scheduleRange = task.getScheduleRange();
    }
}
