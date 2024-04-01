package com.golapadeok.fluo.domain.social.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.golapadeok.fluo.common.jwt.JwtTokenProvider;
import com.golapadeok.fluo.common.security.domain.PrincipalDetails;
import com.golapadeok.fluo.common.util.CookieUtils;
import com.golapadeok.fluo.domain.member.domain.Member;
import com.golapadeok.fluo.domain.social.domain.SocialType;
import com.golapadeok.fluo.domain.social.dto.request.AuthorizationCodeRequest;
import com.golapadeok.fluo.domain.social.dto.response.SocialLoginResponse;
import com.golapadeok.fluo.domain.social.service.SocialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.message.LineFormatter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@Tag(name = "소셜 로그인 API", description = "소셜 로그인 관련 API 목록")
@RestController
public class SocialController {

    private final SocialService oAuthService;
    private final JwtTokenProvider provider;

    @Operation(summary = "소셜 로그인 리다이렉트", description = "소셜 로그인 타입 입력시, 해당 하는 로그인 페이지로 리다이렉트")
    @GetMapping("/auth/{socialLoginType}")
    public ResponseEntity<String> socialLoginRedirect(@Parameter(description = "소셜 타입", required = true)
                                                    @PathVariable("socialLoginType") String socialLoginType,
                                                    HttpServletResponse response) throws IOException {
        log.info("socialLoginRedirect({}) invoked.", socialLoginType);
        var socialType = SocialType.valueOf(socialLoginType.toUpperCase());
        String redirectUrl = this.oAuthService.getRedirectUrl(socialType);
        log.info("social-redirectUrl : {}", redirectUrl);
//        response.sendRedirect(redirectUrl);

        return ResponseEntity.ok(redirectUrl);
//        return ResponseEntity.ok().build();
    }

    @Operation(summary = "소셜 로그인 진행", description = "소셜 로그인 진행, 만약 회원가입이 안되어 있다면 회원가입을 함.")
    @PostMapping("/auth/{socialLoginType}/callback")
    public ResponseEntity<Map<String, String>> socialLogin(@Parameter(description = "소셜 타입", required = true)
                                            @PathVariable("socialLoginType") String socialLoginType,
                                            @Parameter(description = "로그인 진행 후 일회성 인증 코드", example = "sampleX", required = true)
                                            @RequestBody AuthorizationCodeRequest request,
                                            HttpServletResponse response) throws JsonProcessingException {
        log.info("socialLogin({}, {}) invoked.", socialLoginType, request.getCode());

        SocialType socialType  = SocialType.valueOf(socialLoginType.toUpperCase());

        SocialLoginResponse socialLoginResponse = this.oAuthService.socialLogin(socialType, request.getCode());

        // 쿠키 세팅
        ResponseCookie responseCookie =
                CookieUtils.createCookie("refreshToken", socialLoginResponse.getRefreshToken(), response);

        Map<String, String> params = new HashMap<>();
        params.put(HttpHeaders.AUTHORIZATION, "Bearer "+socialLoginResponse.getAccessToken());
        // 리다이렉트
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(params);
    }

    @Operation(summary = "로그아웃 진행", description = "엑세스 토큰을 받아 해당 엑세스 토큰을 DB에 저장시켜 다시 엑세스 토큰으로 인증 하지 못하도록 함.")
    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                        @Parameter(description = "엑세스 토큰", required = true)
                                        HttpServletRequest request) {
//        log.info("memberId : {}", principalDetails.toString());
        String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("accessToken : {}", accessToken);
        if(accessToken == null){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }

        this.oAuthService.logout(principalDetails.getMember(), accessToken);

        ResponseCookie responseCookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .build();

        Map<String, String> params = new HashMap<>();
        params.put("message", "로그아웃 되었습니다.");

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(params);
    }

}
