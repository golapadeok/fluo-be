package com.golapadeok.fluo.domain.authority.domain;

import com.golapadeok.fluo.common.domain.BaseTimeEntity;
import com.golapadeok.fluo.domain.customer.domain.Member;
import jakarta.persistence.*;

@Entity
public class MemberAuthority extends BaseTimeEntity { // 회원그룹권한

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_AUTHORITY_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "AUTHORITY")
    private Authority authority;

}
