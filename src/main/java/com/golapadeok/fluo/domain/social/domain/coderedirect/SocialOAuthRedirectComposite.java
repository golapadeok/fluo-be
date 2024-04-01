package com.golapadeok.fluo.domain.social.domain.coderedirect;

import com.golapadeok.fluo.domain.social.domain.SocialType;
import com.golapadeok.fluo.domain.social.exception.SocialErrorException;
import com.golapadeok.fluo.domain.social.exception.SocialErrorStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class SocialOAuthRedirectComposite{

    private final Map<SocialType, SocialOAuthRedirect> redirect;

    /**
     * 매개변수로 Set을 사용하는 이유는 Hash 기반의 자료 검사를 사용하는데 순서가 없고 중복이 없기 때문에 특정 요소를 찾는 작업이 효율적이다.
     * Map 객체로 반환할때 socialOAuthRedirects가 상속하는 클래스들의 getSocialType을 통해 SocialType을 키값으로 지정해준다.
     * identity() 메소드를 통해 현재 스트림 요소 자체를 값으로 설정하여 객체 주소를 저장한다.
     * 예시) {
     *          GOOGLE=com.golapadeok.fluo.domain.social.domain.coderedirect.GoogleOAuthRedirect@860ed35,
     *          NAVER=com.golapadeok.fluo.domain.social.domain.coderedirect.NaverOAuthRedirect@31fd9926
     *       }
     *  스프링이 실행하면서 DI를 통해 매개변수로 있는 socialOAuthRedirects을 초기화 해준다.
      */
    public SocialOAuthRedirectComposite(Set<SocialOAuthRedirect> socialOAuthRedirects) {
        this.redirect = socialOAuthRedirects.stream()
                .collect(Collectors.toMap(
                        SocialOAuthRedirect::getSocialType,
                        Function.identity()
                ));
        log.info("SocialOAuthRedirectComposite_redirect : {}", redirect);
    }

    public String getOAuthRedirectURL(SocialType socialType) {
        log.info("socialType : {}, getOAuthRedirectURL_redirectUrl : {}", redirect.get(socialType).getSocialType(), redirect.get(socialType).getOAuthRedirectURL());
        return Optional.ofNullable(redirect.get(socialType))
                .orElseThrow(() -> new SocialErrorException(SocialErrorStatus.NOT_FOUNT_SOCIAL_LOGIN))
                .getOAuthRedirectURL();
    }
}
