package com.golapadeok.fluo.domain.invitation.domain;

import com.golapadeok.fluo.domain.member.domain.Member;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@ToString(exclude = {"member", "workspace"})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Invitation {

    @Id
    @GeneratedValue
    @Column(name = "INVITATION_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "WORKSPACE_ID")
    private Workspace workspace;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createDate; // 초대 발송 날짜

    private Boolean isPending; // 가입여부

    @Builder
    public Invitation(Member member, Workspace workspace, LocalDateTime createDate, Boolean isPending) {
        this.member = member;
        this.workspace = workspace;
        this.createDate = createDate;
        this.isPending = isPending;
    }

    public void updateIsPending(Boolean isPending) {
        this.isPending = isPending;
    }
}
