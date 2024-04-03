package com.golapadeok.fluo.domain.workspace.domain;

import com.golapadeok.fluo.common.domain.BaseTimeEntity;
import com.golapadeok.fluo.domain.role.domain.Role;
import com.golapadeok.fluo.domain.member.domain.WorkspaceMember;
import com.golapadeok.fluo.domain.state.domain.State;
import com.golapadeok.fluo.domain.tag.domain.Tag;
import com.golapadeok.fluo.domain.task.domain.Task;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
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
    private String creator;

    @OneToMany(mappedBy = "workspace", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<State> states = new ArrayList<>();

    @OneToMany(mappedBy = "workspace", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<WorkspaceMember> workspaceMembers = new ArrayList<>();

    @OneToMany(mappedBy = "workspace", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Role> roles = new ArrayList<>();

    @OneToMany(mappedBy = "workspace", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Task> tasks = new ArrayList<>();

    @OneToMany(mappedBy = "workspace", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Tag> tags = new ArrayList<>();

    public Workspace(Long id, String title, String description, String imageUrl, String creator) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.creator = creator;
    }

    @Builder(toBuilder = true)
    public Workspace(String title, String description, String imageUrl, String creator) {
        this(null, title, description, imageUrl, creator);
    }

    public void changeImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void changeWorkspace(Workspace workspace) {
        this.title = workspace.getTitle();
    }

    public void changeInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }
}
