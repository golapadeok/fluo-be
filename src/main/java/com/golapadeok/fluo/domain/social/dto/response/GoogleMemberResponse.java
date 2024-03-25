package com.golapadeok.fluo.domain.social.dto.response;

import com.golapadeok.fluo.domain.member.domain.Member;
import com.golapadeok.fluo.domain.member.domain.SocialId;
import com.golapadeok.fluo.domain.social.domain.SocialType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GoogleMemberResponse {

    private String id;
    private String email;
    private Boolean verifiedEmail;
    private String name;
    private String givenName;
    private String familyName;
    public String picture;
    public String locale;

    public Member toMember() {
        return Member.builder()
                .socialId(new SocialId(this.id, SocialType.GOOGLE))
                .email(this.email)
                .profile(this.picture)
                .name(this.name)
                .build();
    }

}
