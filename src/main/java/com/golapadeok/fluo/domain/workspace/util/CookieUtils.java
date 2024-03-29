package com.golapadeok.fluo.domain.workspace.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;

public class CookieUtils {

    public static ResponseCookie createCookie(String cookieName, Object cookieValue, HttpServletResponse response) {
        return ResponseCookie.from(cookieName, (String) cookieValue)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .build();
    }

}
