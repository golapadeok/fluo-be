package com.golapadeok.fluo.domain.workspace.dto;

import com.golapadeok.fluo.domain.member.domain.Member;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Getter
public class MemberDto {
    private final String memberId;
    private final String name;
    private final String email;
    private final String profileUrl;

    private MemberDto(String memberId, String name, String email, String profileUrl) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.profileUrl = profileUrl;
    }

    public static MemberDto of(Member member) {
        return new MemberDto(
                member.getId().toString(),
                member.getName(),
                member.getEmail(),
                member.getProfile()
        );
    }

    public static List<MemberDto> of(List<Member> members) {
        Iterator<Member> iterator = members.iterator();
        List<MemberDto> results = new ArrayList<>();
        while (iterator.hasNext()) {
            Member next = iterator.next();
            MemberDto memberDto = of(next);
            results.add(memberDto);
        }

        return Collections.unmodifiableList(results);
    }
}
