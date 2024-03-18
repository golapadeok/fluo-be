package com.golapadeok.fluo.domain.social.exception;

import lombok.Getter;

@Getter
public class SocialErrorException extends RuntimeException{

    private SocialErrorStatus socialErrorStatus;

    public SocialErrorException(SocialErrorStatus socialErrorStatus) {
        this.socialErrorStatus = socialErrorStatus;
    }

}
