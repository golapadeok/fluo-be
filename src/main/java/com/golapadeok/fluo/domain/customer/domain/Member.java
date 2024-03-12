package com.golapadeok.fluo.domain.customer.domain;

import com.golapadeok.fluo.common.domain.BaseTimeEntity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    private String email;
    private String name;
    private String profile;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<WorkspaceMember> workspaceMembers = new ArrayList<>(); // 그룹참여유저

}
