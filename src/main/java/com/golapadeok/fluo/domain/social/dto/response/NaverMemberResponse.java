package com.golapadeok.fluo.domain.social.dto.response;

import com.golapadeok.fluo.domain.member.domain.Member;
import com.golapadeok.fluo.domain.member.domain.SocialId;
import com.golapadeok.fluo.domain.social.domain.SocialType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NaverMemberResponse {

    private String resultcode;
    private String message;
    private Response response;

    public Member toMember() {
        return Member.builder()
                .socialId(new SocialId(this.response.getId(), SocialType.NAVER))
                .email(this.response.getEmail())
                .profile(this.response.getProfile_image())
                .name(this.response.getName())
                .build();
    }

}

@Getter
class Response {
    private String id;
    private String nickname;
    private String name;
    private String email;
    private String gender;
    private String age;
    private String birthday;
    private String profile_image;
    private String birthyear;
    private String mobile;
}
