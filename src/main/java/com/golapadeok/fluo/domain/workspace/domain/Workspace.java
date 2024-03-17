package com.golapadeok.fluo.domain.workspace.domain;

import com.golapadeok.fluo.common.domain.BaseTimeEntity;
import com.golapadeok.fluo.domain.role.domain.Role;
import com.golapadeok.fluo.domain.member.domain.WorkspaceMember;
import com.golapadeok.fluo.domain.task.domain.Task;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Workspace extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "WORKSPACE_ID")
    private Long id;

    private String title;
    private String description;

    @OneToMany(mappedBy = "workspace", fetch = FetchType.LAZY)
    private List<State> states = new ArrayList<>();

    @OneToMany(mappedBy = "workspace", fetch = FetchType.LAZY)
    private List<WorkspaceMember> workspaceMembers = new ArrayList<>();

    @OneToMany(mappedBy = "workspace", fetch = FetchType.LAZY)
    private List<Role> roles = new ArrayList<>();

    @OneToMany(mappedBy = "workspace", fetch = FetchType.LAZY)
    private List<Task> tasks = new ArrayList<>();

    public Workspace(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public Workspace(String title, String description) {
        this(null, title, description);
    }
}
