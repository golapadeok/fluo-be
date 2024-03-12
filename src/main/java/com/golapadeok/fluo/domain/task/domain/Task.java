package com.golapadeok.fluo.domain.task.domain;

import com.golapadeok.fluo.common.domain.BaseTimeEntity;
import com.golapadeok.fluo.domain.customer.domain.Member;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity
public class Task extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "TASK_ID")
    private Long id;

    private String title;
    private String content;
    private Boolean isPrivate; // 공개여부
    private Integer level; // 중요도

    @ManyToOne
    @JoinColumn(name = "WORKSPACE_ID")
    private Workspace workspace;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY)
    private List<ManagerTask> managerTasks = new ArrayList<>();


}
