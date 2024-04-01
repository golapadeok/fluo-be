package com.golapadeok.fluo.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
//                .allowedOriginPatterns("*")
                .allowedOrigins("https://fluo-fe.pages.dev",
                        "http://localhost:5173", "https://project-application.shop:443",
                        "https://accounts.google.com", "https://nid.naver.com")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PATCH", "PUT")
                .allowedHeaders("*")
                .exposedHeaders("Set-Cookies")
                .maxAge(3600L);
    }
}
