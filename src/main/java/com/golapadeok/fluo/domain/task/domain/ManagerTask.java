package com.golapadeok.fluo.domain.task.domain;

import com.golapadeok.fluo.common.domain.BaseTimeEntity;
import com.golapadeok.fluo.domain.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class ManagerTask extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "MANAGER_TASK_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "TASK_ID")
    private Task task;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    public void changeTask(Task task) {
        this.task = task;

        if (task != null)
            task.getManagers().add(this);
    }

    public void changeMember(Member member) {
        this.member = member;
    }
}
