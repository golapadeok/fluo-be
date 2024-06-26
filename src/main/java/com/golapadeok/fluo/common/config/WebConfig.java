package com.golapadeok.fluo.common.config;

import com.golapadeok.fluo.domain.social.converter.SocialConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableScheduling
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
//                .allowedOriginPatterns("*")
                .allowedOrigins("https://fluo-fe.pages.dev",
                        "http://localhost:5173",
                        "http://localhost:5173/",
                        "https://project-application.shop:443")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PATCH", "PUT")
                .allowedHeaders("*")
                .exposedHeaders(HttpHeaders.SET_COOKIE, HttpHeaders.AUTHORIZATION)
                .maxAge(3600L);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new SocialConverter());
    }
}
