package com.golapadeok.fluo.domain.customer.domain;

import com.golapadeok.fluo.common.domain.BaseTimeEntity;
import com.golapadeok.fluo.domain.task.domain.ManagerTask;
import com.golapadeok.fluo.domain.task.domain.Task;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "CUSTOMER_ID")
    private Long id;

    private String email;
    private String name;
    private String profile;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<WorkspaceMember> workspaceMembers = new ArrayList<>(); // 그룹참여유저

}
