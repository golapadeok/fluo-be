package com.golapadeok.fluo.domain.member.domain;

import com.golapadeok.fluo.domain.social.domain.SocialType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class SocialId {

    private String socialId;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

}
