package com.golapadeok.fluo.common.config;

import com.golapadeok.fluo.common.annotation.interception.AuthCheckInterceptor;
import com.golapadeok.fluo.domain.social.converter.SocialConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AuthCheckInterceptor authCheckInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
//                .allowedOriginPatterns("*")
                .allowedOrigins("https://fluo-fe.pages.dev",
                        "http://localhost:5173", "https://project-application.shop:443")
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

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authCheckInterceptor)
                .addPathPatterns("*");
    }
}
