package com.golapadeok.fluo.domain.role.domain;

import com.golapadeok.fluo.common.domain.BaseTimeEntity;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import jakarta.persistence.*;
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
    private String roles;

    @ManyToOne
    @JoinColumn(name = "WORKSPACE_ID")
    private Workspace workspace;

    public Role(String name, String roles, Workspace workspace) {
        this.name = name;
        this.roles = roles;
        this.workspace = workspace;
    }

    public List<String> getRoleList() {
        if(this.roles.length() > 0) {
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

}
