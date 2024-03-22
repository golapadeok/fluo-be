package com.golapadeok.fluo.domain.member.domain;

import com.golapadeok.fluo.common.domain.BaseTimeEntity;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import jakarta.persistence.*;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor
@Entity
public class WorkspaceMember extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "WORKSPACE_MEMBER_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "WORKSPACE_ID")
    private Workspace workspace;
    private String latestUrl; // 마지막 접속 URL

    @Builder
    public WorkspaceMember(Member member, Workspace workspace, String latestUrl) {
        this.member = member;
        this.workspace = workspace;
        this.latestUrl = latestUrl;
    }
}
