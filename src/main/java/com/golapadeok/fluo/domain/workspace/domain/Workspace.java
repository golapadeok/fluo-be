package com.golapadeok.fluo.domain.workspace.domain;

import com.golapadeok.fluo.common.domain.BaseTimeEntity;
import com.golapadeok.fluo.domain.role.domain.Role;
import com.golapadeok.fluo.domain.customer.domain.WorkspaceMember;
import com.golapadeok.fluo.domain.task.domain.Task;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Workspace extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "WORKSPACE_ID")
    private Long id;

    private String title;
    private String description;

    @OneToMany(mappedBy = "workspace", fetch = FetchType.LAZY)
    private List<WorkspaceMember> workspaceMembers = new ArrayList<>();

    @OneToMany(mappedBy = "workspace", fetch = FetchType.LAZY)
    private List<Role> roles = new ArrayList<>();

    @OneToMany(mappedBy = "workspace", fetch = FetchType.LAZY)
    private List<Task> tasks = new ArrayList<>();

}
