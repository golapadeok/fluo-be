package com.golapadeok.fluo.domain.role.domain;

import com.golapadeok.fluo.common.domain.BaseTimeEntity;
import com.golapadeok.fluo.domain.role.dto.request.RoleUpdateRequest;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ToString(exclude = "workspace")
@Getter
@NoArgsConstructor
@Entity
public class Role extends BaseTimeEntity { // 그룹역할종류

    @Id @GeneratedValue
    @Column(name = "ROLE_ID")
    private Long id;

    private String name;
    private String description;
    private String roles;
    private Boolean isDefault;

    @ManyToOne
    @JoinColumn(name = "WORKSPACE_ID")
    private Workspace workspace;

    @OneToMany(mappedBy = "role", cascade = CascadeType.REMOVE)
    private List<MemberRole> memberRoles = new ArrayList<>();

    @Builder
    public Role(String name, String description,String roles, Workspace workspace, Boolean isDefault) {
        this.name = name;
        this.description = description;
        this.roles = roles;
        this.workspace = workspace;
        this.isDefault = isDefault;
    }

    public List<String> getRoleList() {
        if(this.roles.length() > 0) {
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

    public void updateRole(RoleUpdateRequest request) {
        this.name = request.getName();
        this.description = request.getDescription();
        this.roles = String.join(",", request.getCredentials().stream().map(Enum::name).toList());
    }

}
