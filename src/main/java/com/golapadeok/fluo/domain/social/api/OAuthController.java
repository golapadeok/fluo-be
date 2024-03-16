package com.golapadeok.fluo.domain.social.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.golapadeok.fluo.domain.social.domain.SocialType;
import com.golapadeok.fluo.domain.social.dto.response.SocialLoginResponse;
import com.golapadeok.fluo.domain.social.service.OAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/v1")
public class OAuthController {

    private final OAuthService oAuthService;

    @Operation(summary = "소셜 로그인 리다이렉트", description = "소셜 로그인 타입 입력시, 해당 하는 로그인 페이지로 리다이렉트")
    @GetMapping("/auth/{socialLoginType}")
    public ResponseEntity<Void> socialLoginRedirect(@Parameter(description = "소셜 타입", required = true)
                                                    @PathVariable("socialLoginType") String socialLoginType,
                                                    HttpServletResponse response) throws IOException {
        log.trace("socialLoginRedirect({}) invoked.", socialLoginType);
        SocialType socialType = SocialType.valueOf(socialLoginType.toUpperCase());
        String redirectUrl = this.oAuthService.getRedirectUrl(socialType);
        response.sendRedirect(redirectUrl);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "소셜 로그인 진행", description = "소셜 로그인 진행, 만약 회원가입이 안되어 있다면 회원가입을 함.")
    @GetMapping("/auth/{socialLoginType}/callback")
    public ResponseEntity<Void> socialLogin(@Parameter(description = "소셜 타입", required = true)
                                            @PathVariable("socialLoginType") String socialLoginType,
                                            @Parameter(description = "로그인 진행 후 일회성 인증 코드", example = "sampleX", required = true)
                                            @RequestParam("code") String code,
                                            HttpServletResponse response) throws JsonProcessingException {
        log.trace("socialLogin({}, {}) invoked.", socialLoginType, code);

        SocialType socialType = SocialType.valueOf(socialLoginType.toUpperCase());
        SocialLoginResponse socialLoginResponse = this.oAuthService.socialLogin(socialType, code);

        Cookie cookie = new Cookie("RefreshToken", socialLoginResponse.getRefreshToken());
        response.addHeader("Authorization", "Bearer "+socialLoginResponse.getAccessToken());
        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }

}
