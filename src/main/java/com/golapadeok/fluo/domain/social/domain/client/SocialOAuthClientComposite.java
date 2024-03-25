package com.golapadeok.fluo.domain.social.domain.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.golapadeok.fluo.domain.member.domain.Member;
import com.golapadeok.fluo.domain.social.domain.SocialType;
import com.golapadeok.fluo.domain.social.exception.SocialErrorException;
import com.golapadeok.fluo.domain.social.exception.SocialErrorStatus;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class SocialOAuthClientComposite {

    private Map<SocialType, SocialOAuthClient> client;

    public SocialOAuthClientComposite(Set<SocialOAuthClient> socialOAuthClients) {
        this.client = socialOAuthClients.stream()
                .collect(Collectors.toMap(
                        SocialOAuthClient::getSocialType,
                        Function.identity()
                ));
    }

    public Member requestAccessTokenAndUserInfo(SocialType socialType, String code) throws JsonProcessingException {
        return Optional.ofNullable(this.client.get(socialType).requestAccessTokenAndUserInfo(code))
                .orElseThrow(() -> new SocialErrorException(SocialErrorStatus.NOT_FOUNT_SOCIAL_LOGIN)); // 지원하지 않는 소셜 로그인 입니다.
    }

}
