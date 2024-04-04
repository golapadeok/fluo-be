package com.golapadeok.fluo.domain.task.dto;

import com.golapadeok.fluo.domain.member.domain.Member;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Getter
public class MemberDto {
    private final String id;
    private final String name;
    private final String profileUrl;

    private MemberDto(String id, String name, String profileUrl) {
        this.id = id;
        this.name = name;
        this.profileUrl = profileUrl;
    }

    public static MemberDto of(Member member) {
        return new MemberDto(member.getId().toString(), member.getName(), member.getProfile());
    }

    public static List<MemberDto> of(List<Member> members) {
        Iterator<Member> iterator = members.iterator();
        List<MemberDto> results = new ArrayList<>();
        while (iterator.hasNext()) {
            Member member = iterator.next();
            results.add(new MemberDto(member.getId().toString(), member.getName(), member.getProfile()));
        }

        return Collections.unmodifiableList(results);
    }
}
