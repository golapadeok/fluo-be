package com.golapadeok.fluo.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
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
                .allowedOrigins("https://localhost:5173", "http://localhost:5713")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PATCH", "PUT")
                .allowedHeaders("*")
                .exposedHeaders("Set-Cookies")
                .maxAge(3600L);
    }
}
