package com.golapadeok.fluo.common.util;

import com.golapadeok.fluo.common.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseCookie;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CookieUtils {

    public static ResponseCookie createCookie(String cookieName, Object cookieValue, HttpServletResponse response) {
        return ResponseCookie.from(cookieName, (String) cookieValue)
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/")
                .build();
    }

}
