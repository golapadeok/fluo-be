package com.golapadeok.fluo.common.config;

import com.golapadeok.fluo.domain.social.converter.SocialConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
//                .allowedOriginPatterns("https://accounts.google.com/*", "https://nid.naver.com")
                .allowedOrigins("https://fluo-fe.pages.dev",
                        "http://localhost:5173", "https://project-application.shop:443",
                        "https://accounts.google.com", "https://nid.naver.com")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PATCH", "PUT")
                .allowedHeaders("*")
                .exposedHeaders("Set-Cookies")
                .maxAge(3600L);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new SocialConverter());
    }
}
