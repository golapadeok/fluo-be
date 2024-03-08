package com.golapadeok.fluo.domain.customer.domain;

import com.golapadeok.fluo.common.domain.BaseTimeEntity;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import jakarta.persistence.*;

@Entity
public class WorkspaceMember extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "WORKSPACE_ID")
    private Workspace workspace;
    private String latestUrl; // 마지막 접속 URL

}
