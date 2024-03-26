package com.golapadeok.fluo.domain.workspace.domain;

import com.golapadeok.fluo.common.domain.BaseTimeEntity;
import com.golapadeok.fluo.domain.file.domain.DefaultImage;
import com.golapadeok.fluo.domain.role.domain.Role;
import com.golapadeok.fluo.domain.member.domain.WorkspaceMember;
import com.golapadeok.fluo.domain.state.domain.State;
import com.golapadeok.fluo.domain.tag.domain.Tag;
import com.golapadeok.fluo.domain.task.domain.Task;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString(exclude = {"states", "workspaceMembers", "roles", "tasks", "tags"})
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
    private String imageUrl;
    private String invitationCode;

    @OneToMany(mappedBy = "workspace", fetch = FetchType.LAZY)
    private List<State> states = new ArrayList<>();

    @OneToMany(mappedBy = "workspace", fetch = FetchType.LAZY)
    private List<WorkspaceMember> workspaceMembers = new ArrayList<>();

    @OneToMany(mappedBy = "workspace", fetch = FetchType.LAZY)
    private List<Role> roles = new ArrayList<>();

    @OneToMany(mappedBy = "workspace", fetch = FetchType.LAZY)
    private List<Task> tasks = new ArrayList<>();

    @OneToMany(mappedBy = "workspace", fetch = FetchType.LAZY)
    private List<Tag> tags = new ArrayList<>();

    public Workspace(Long id, String title, String description, String imageUrl) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public Workspace(String title, String description, String imageUrl) {
        this(null, title, description, imageUrl);
    }

    public void changeImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
