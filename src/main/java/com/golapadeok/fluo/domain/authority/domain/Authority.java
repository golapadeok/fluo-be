package com.golapadeok.fluo.domain.authority.domain;

import com.golapadeok.fluo.common.domain.BaseTimeEntity;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
public class Authority extends BaseTimeEntity { // 그룹역할종류

    @Id @GeneratedValue
    @Column(name = "AUTHORITY_ID")
    private Long id;

    private String name;
    private String roles; // ROLE_WRITER, ROLE_READ, ROLE_UPDATE, ROLE_DELETE, ROLE_ENTER, ROLE_INVITE

    @ManyToOne
    @JoinColumn(name = "WORKSPACE_ID")
    private Workspace workspace;

    public List<String> getRoleList() {
        if(this.roles.length() > 0) {
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

}
