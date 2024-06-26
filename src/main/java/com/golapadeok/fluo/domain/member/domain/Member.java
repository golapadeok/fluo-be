package com.golapadeok.fluo.domain.member.domain;

import com.golapadeok.fluo.common.domain.BaseTimeEntity;
import com.golapadeok.fluo.domain.invitation.domain.Invitation;
import com.golapadeok.fluo.domain.role.domain.MemberRole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@ToString(exclude = {"socialId", "workspaceMembers", "invitations", "memberRoles"})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    private String email;
    private String name;
    private String profile;
    private String refreshToken;

    @Embedded
    private SocialId socialId;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<WorkspaceMember> workspaceMembers = new ArrayList<>(); // 그룹참여유저

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Invitation> invitations = new ArrayList<>(); // 그룹참여유저

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<MemberRole> memberRoles = new ArrayList<>();

    @Builder
    public Member(Long id, String email, String name, String profile, String refreshToken, SocialId socialId, List<WorkspaceMember> workspaceMembers) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.profile = profile;
        this.refreshToken = refreshToken;
        this.socialId = socialId;
        this.workspaceMembers = workspaceMembers;
    }

    public void updateRefreshToken(String recreatingRefreshToken) {
        this.refreshToken = recreatingRefreshToken;
    }

    public void updateNameAndProfile(String name, String profile) {
        this.name = name;
        this.profile = profile;
    }
}
