package com.golapadeok.fluo.domain.role.domain;

import com.golapadeok.fluo.common.domain.BaseTimeEntity;
import com.golapadeok.fluo.domain.member.domain.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

//@ToString
@Getter
@NoArgsConstructor
@Entity
public class MemberRole extends BaseTimeEntity { // 회원그룹권한

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ROLE_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "ROLE_ID")
    private Role role;

    @Builder
    public MemberRole(Member member, Role role) {
        this.member = member;
        this.role = role;
    }

    public void updateRole(Member member, Role role) {
        this.member = member;
        this.role = role;
    }
}
