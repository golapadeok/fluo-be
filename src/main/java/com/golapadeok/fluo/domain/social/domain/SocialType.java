package com.golapadeok.fluo.domain.social.domain;

import static java.util.Locale.ENGLISH;

public enum SocialType {

    GOOGLE,
    NAVER;

    public static SocialType fromName(String type) {
        return SocialType.valueOf(type.toUpperCase(ENGLISH));
    }

}
