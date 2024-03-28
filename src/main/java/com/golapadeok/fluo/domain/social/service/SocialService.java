package com.golapadeok.fluo.domain.social.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.golapadeok.fluo.common.jwt.JwtTokenProvider;
import com.golapadeok.fluo.domain.member.domain.Member;
import com.golapadeok.fluo.domain.member.repository.MemberRepository;
import com.golapadeok.fluo.domain.social.domain.BlackList;
import com.golapadeok.fluo.domain.social.domain.SocialType;
import com.golapadeok.fluo.domain.social.domain.client.SocialOAuthClientComposite;
import com.golapadeok.fluo.domain.social.domain.coderedirect.SocialOAuthRedirectComposite;
import com.golapadeok.fluo.domain.social.dto.response.SocialLoginResponse;
import com.golapadeok.fluo.domain.social.exception.SocialErrorException;
import com.golapadeok.fluo.domain.social.exception.SocialErrorStatus;
import com.golapadeok.fluo.domain.social.repository.BlackListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Service
public class SocialService {

    private final SocialOAuthRedirectComposite socialOAuthRedirectComposite;
    private final SocialOAuthClientComposite socialOAuthClientComposite;
    private final MemberRepository memberRepository;
    private final BlackListRepository blackListRepository;
    private final JwtTokenProvider provider;

    public String getRedirectUrl(SocialType socialType) {
        return this.socialOAuthRedirectComposite.getOAuthRedirectURL(socialType);
    }

    public SocialLoginResponse socialLogin(SocialType socialType, String code) throws JsonProcessingException {
        Member socialMember = this.socialOAuthClientComposite.requestAccessTokenAndUserInfo(socialType, code);

        String accessToken = this.provider.createAccessToken(socialMember.getEmail());
        String refreshToken = this.provider.createRefreshToken();

        // 로그인시 access token과 refresh token을 재 생성하고, refresh token을 db에 저장한다.
        // 이미 회원가입이 되어 있는 상태에서 구글이나 네이버의 계졍에 이름이나 사진이 수정되면 수정해준다.
        Member member = this.memberRepository.findByEmailAndSocialId(socialMember.getEmail(), socialMember.getSocialId())
                .map(m -> {
                    m.updateNameAndProfile(socialMember.getName(), socialMember.getProfile());
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

    @Transactional
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

    @Transactional
    public void logout(Member member, String accessToken) {

        this.memberRepository.findById(member.getId())
                .map(m -> {
                    m.updateRefreshToken(null);
                    return this.memberRepository.save(m);
                })
                .orElseThrow(() -> new SocialErrorException(SocialErrorStatus.NOT_FOUNT_USER));


        // 블랙리스트에 access token 저장
        this.blackListRepository.findByAccessToken(accessToken)
                .ifPresent(existingBlackList -> {
                    throw new SocialErrorException(SocialErrorStatus.USER_ALREADY_LOGGED_OUT);
                });

        BlackList blackList = new BlackList(accessToken);
        this.blackListRepository.save(blackList);

    }

}
