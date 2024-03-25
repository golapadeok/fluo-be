package com.golapadeok.fluo.domain.member.domain;

import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

}
