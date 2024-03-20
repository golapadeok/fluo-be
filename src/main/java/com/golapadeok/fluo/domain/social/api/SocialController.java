package com.golapadeok.fluo.domain.social.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.golapadeok.fluo.common.security.domain.PrincipalDetails;
import com.golapadeok.fluo.domain.social.domain.SocialType;
import com.golapadeok.fluo.domain.social.dto.response.SocialLoginResponse;
import com.golapadeok.fluo.domain.social.service.SocialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

    @Operation(summary = "소셜 로그인 리다이렉트", description = "소셜 로그인 타입 입력시, 해당 하는 로그인 페이지로 리다이렉트")
    @GetMapping("/auth/{socialLoginType}")
    public ResponseEntity<Void> socialLoginRedirect(@Parameter(description = "소셜 타입", required = true)
                                                    @PathVariable("socialLoginType") String socialLoginType,
                                                    HttpServletResponse response) throws IOException {
        log.info("socialLoginRedirect({}) invoked.", socialLoginType);
        SocialType socialType = SocialType.valueOf(socialLoginType.toUpperCase());
        String redirectUrl = this.oAuthService.getRedirectUrl(socialType);
        response.sendRedirect(redirectUrl);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "소셜 로그인 진행", description = "소셜 로그인 진행, 만약 회원가입이 안되어 있다면 회원가입을 함.")
    @GetMapping("/auth/{socialLoginType}/callback")
    public ResponseEntity<Map<String, String>> socialLogin(@Parameter(description = "소셜 타입", required = true)
                                            @PathVariable("socialLoginType") String socialLoginType,
                                            @Parameter(description = "로그인 진행 후 일회성 인증 코드", example = "sampleX", required = true)
                                            @RequestParam("code") String code,
                                            HttpServletResponse response) throws JsonProcessingException {
        log.info("socialLogin({}, {}) invoked.", socialLoginType, code);

        SocialType socialType  = SocialType.valueOf(socialLoginType.toUpperCase());

        SocialLoginResponse socialLoginResponse = this.oAuthService.socialLogin(socialType, code);

        // 쿠키 세팅
        Cookie cookie = new Cookie("RefreshToken", socialLoginResponse.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        
        response.addHeader(HttpHeaders.AUTHORIZATION, "Bearer "+socialLoginResponse.getAccessToken());
        response.addCookie(cookie);

        Map<String, String> params = new HashMap<>();
        params.put("memberId", socialLoginResponse.getMemberId());

        return ResponseEntity.ok(params);
    }

    @Operation(summary = "로그아웃 진행", description = "엑세스 토큰을 받아 해당 엑세스 토큰을 DB에 저장시켜 다시 엑세스 토큰으로 인증 하지 못하도록 함.")
    @PostMapping("/auth/logout")
    public ResponseEntity<Map<String, String>> logout(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                        @Parameter(description = "엑세스 토큰", required = true)
                                        HttpServletRequest request,
                                                      @CookieValue(value = "RefreshToken") Cookie cookie) {
        log.info("cookie : {}", cookie.getValue());
        log.info("memberId : {}", principalDetails.toString());
        String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("accessToken : {}", accessToken);
        if(accessToken == null){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }

        this.oAuthService.logout(principalDetails.getMember(), accessToken);

        Map<String, String> params = new HashMap<>();
        params.put("message", "로그아웃 되었습니다.");

        return ResponseEntity.ok(params);
    }


}
