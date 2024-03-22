package com.golapadeok.fluo.domain.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class MemberInfoResponse {

    private String memberId;
    private String name;
    private String email;
    private String profileUrl;

}
