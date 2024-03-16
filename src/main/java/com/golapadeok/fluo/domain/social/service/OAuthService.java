package com.golapadeok.fluo.domain.social.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.golapadeok.fluo.common.jwt.JwtTokenProvider;
import com.golapadeok.fluo.domain.member.domain.Member;
import com.golapadeok.fluo.domain.member.repository.MemberRepository;
import com.golapadeok.fluo.domain.social.domain.client.GoogleOAuthClient;
import com.golapadeok.fluo.domain.social.domain.SocialType;
import com.golapadeok.fluo.domain.social.domain.client.SocialOAuthClientComposite;
import com.golapadeok.fluo.domain.social.domain.coderedirect.SocialOAuthRedirectComposite;
import com.golapadeok.fluo.domain.social.dto.request.GoogleOAuthToken;
import com.golapadeok.fluo.domain.social.dto.response.SocialLoginResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class OAuthService {

    private final SocialOAuthRedirectComposite socialOAuthRedirectComposite;
    private final SocialOAuthClientComposite socialOAuthClientComposite;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider provider;

    public String getRedirectUrl(SocialType socialType) {
        return this.socialOAuthRedirectComposite.getOAuthRedirectURL(socialType);
    }

    public SocialLoginResponse socialLogin(SocialType socialType, String code) throws JsonProcessingException {
        Member socialMember = this.socialOAuthClientComposite.requestAccessTokenAndUserInfo(socialType, code);

        String accessToken = this.provider.createAccessToken(socialMember.getEmail());
        String refreshToken = this.provider.createRefreshToken();

        // 로그인시 access token과 refresh token을 재 생성하고, refresh token을 db에 저장한다.
        Member member = this.memberRepository.findByEmailAndSocialId(socialMember.getEmail(), socialMember.getSocialId())
                .map(m -> {
                    m.updateRefreshToken(refreshToken);
                    return this.memberRepository.save(m);
                })
                .orElseGet(() -> this.socialSave(socialMember, refreshToken));

        return SocialLoginResponse.builder()
                .memberId(String.valueOf(member.getId()))
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private Member socialSave(Member socialMember, String refreshToken) {
        // refresh token 생성

        Member saved = Member.builder()
                .name(socialMember.getName())
                .email(socialMember.getEmail())
                .profile(socialMember.getProfile())
                .socialId(socialMember.getSocialId())
                .refreshToken(refreshToken)
                .build();

        return this.memberRepository.save(saved);
    }

}
