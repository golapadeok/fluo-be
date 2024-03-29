package com.golapadeok.fluo.domain.invitation.dto.response;

import com.golapadeok.fluo.domain.member.domain.Member;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberInfo {

    private String id;
    private String name;
    private String profileUrl;

    @Builder
    public MemberInfo(String id, String name, String profileUrl) {
        this.id = id;
        this.name = name;
        this.profileUrl = profileUrl;
    }

    public static MemberInfo of(Member member) {
        return MemberInfo.builder()
                .id(String.valueOf(member.getId()))
                .name(member.getName())
                .profileUrl(member.getProfile())
                .build();
    }
}
