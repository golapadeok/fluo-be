package com.golapadeok.fluo.common.security.config;

import com.golapadeok.fluo.common.annotation.interception.AuthCheckInterceptor;
import com.golapadeok.fluo.common.jwt.JwtTokenProvider;
import com.golapadeok.fluo.common.security.filter.JwtAuthorizationFilter;
import com.golapadeok.fluo.common.security.filter.JwtExceptionFilter;
import com.golapadeok.fluo.common.security.service.PrincipalDetailsService;
import com.golapadeok.fluo.domain.member.repository.MemberRepository;
import com.golapadeok.fluo.domain.social.repository.BlackListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenProvider provider;
    private final MemberRepository memberRepository;
    private final BlackListRepository blackListRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    @Profile("default")
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorization -> authorization
                        .requestMatchers(PathRequest.toH2Console()).permitAll()
                        .requestMatchers("/auth/**").permitAll()
                        .anyRequest().permitAll()); // 테스트 종료시 authentication()으로 변경

//        http.cors(cors -> cors.configurationSource(corsConfiguration()));

        //H2 데이터베이스 접근
        http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

        http.sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable);

        // UsernamePasswordAuthenticationFilter 이전에 JwtAuthorizationFilter를 실행하겠다는 뜻
        http.addFilterBefore(new JwtAuthorizationFilter(this.provider, this.memberRepository, this.blackListRepository), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtExceptionFilter(), JwtAuthorizationFilter.class);

        return http.build();
    }

    @Bean
    @Profile({"dev", "blue", "green"})
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((authorization) -> authorization
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "/webjars/**").permitAll()
                        .anyRequest().permitAll()); // 테스트 종료시 authentication()으로 변경

        http.cors(cors -> cors.configurationSource(corsConfiguration()));

        http.sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(formLogin -> formLogin.disable())
                .httpBasic(httpBasic -> httpBasic.disable());

        // UsernamePasswordAuthenticationFilter 이전에 JwtAuthorizationFilter를 실행하겠다는 뜻
        http.addFilterBefore(new JwtAuthorizationFilter(this.provider, this.memberRepository, this.blackListRepository), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtExceptionFilter(), JwtAuthorizationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfiguration() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // 5173

        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(
                List.of("https://fluo-fe.pages.dev:443", "http://localhost:5173", "http://localhost:5173/", "https://project-application.shop:443"));
//        config.setAllowCredentials(true);
        config.setAllowedMethods(List.of("GET", "POST", "DELETE", "PATCH", "PUT"));
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of(HttpHeaders.SET_COOKIE, HttpHeaders.AUTHORIZATION));
        config.setMaxAge(3600L);

        source.registerCorsConfiguration("/**", config);

        return source;
    }

}
