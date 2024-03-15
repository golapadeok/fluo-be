package com.golapadeok.fluo.domain.social.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.golapadeok.fluo.common.jwt.JwtTokenProvider;
import com.golapadeok.fluo.domain.member.domain.Member;
import com.golapadeok.fluo.domain.member.repository.MemberRepository;
import com.golapadeok.fluo.domain.social.client.GoogleOAuthClient;
import com.golapadeok.fluo.domain.social.domain.SocialType;
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

    private final GoogleOAuthClient googleOAuthClient;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider provider;

    public String getRedirectUrl(SocialType socialType) {
        String redirectUrl = switch (socialType) {
            case GOOGLE -> this.googleOAuthClient.getOAuthRedirectURL();
            default -> throw new IllegalArgumentException("지원하지 않는 소셜 로그인 입니다.");
        };
        return redirectUrl;
    }

    public SocialLoginResponse socialLogin(SocialType socialType, String code) throws JsonProcessingException {
        Member member = null;
        String accessToken = null;
        String refreshToken = this.provider.createRefreshToken();

        switch (socialType) {
            case GOOGLE -> {
                ResponseEntity<String> responseAccessToken = this.googleOAuthClient.requestAccessToken(code);
                GoogleOAuthToken googleOAuthToken = this.googleOAuthClient.getAccessToken(responseAccessToken);
                Member socialMember = this.googleOAuthClient.requestUserInfo(googleOAuthToken);

                accessToken = this.provider.createAccessToken(socialMember.getEmail());

                // 로그인시 access token과 refresh token을 재 생성하고, refresh token을 db에 저장한다.
                member = this.memberRepository.findByEmailAndSocialId(socialMember.getEmail(), socialMember.getSocialId())
                        .map(m -> {
                            m.updateRefreshToken(refreshToken);
                            return this.memberRepository.save(m);
                        })
                        .orElseGet(() -> this.socialSave(socialMember, refreshToken));


            }
            default -> throw new IllegalArgumentException("알 수 없는 로그인 형식 입니다.");
        }

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
