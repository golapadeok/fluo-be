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

    private MemberDto(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public static MemberDto of(Member member) {
        return new MemberDto(member.getId().toString(), member.getName());
    }

    public static List<MemberDto> of(List<Member> members) {
        Iterator<Member> iterator = members.iterator();
        List<MemberDto> results = new ArrayList<>();
        while (iterator.hasNext()) {
            Member member = iterator.next();
            results.add(new MemberDto(member.getId().toString(), member.getName()));
        }

        return Collections.unmodifiableList(results);
    }
}
