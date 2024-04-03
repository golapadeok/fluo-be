package com.golapadeok.fluo.common.security.config;

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
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenProvider provider;
    private final PrincipalDetailsService principalDetailsService;
    private final MemberRepository memberRepository;

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

        http.sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable);

        // UsernamePasswordAuthenticationFilter 이전에 JwtAuthorizationFilter를 실행하겠다는 뜻
        http.addFilterBefore(new JwtAuthorizationFilter(this.provider, this.memberRepository), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtExceptionFilter(), JwtAuthorizationFilter.class);

        return http.build();
    }

    @Bean
    @Profile({"dev", "blue", "green"})
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((authorization) -> authorization
//                        .requestMatchers(PathRequest.toH2Console()).permitAll()
                        .requestMatchers("/api/v1/auth/logout").authenticated()
                        .anyRequest().permitAll());

        http.sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(formLogin -> formLogin.disable())
                .httpBasic(httpBasic -> httpBasic.disable());

        // UsernamePasswordAuthenticationFilter 이전에 JwtAuthorizationFilter를 실행하겠다는 뜻
        http.addFilterBefore(new JwtAuthorizationFilter(this.provider, this.memberRepository), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtExceptionFilter(), JwtAuthorizationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfiguration corsConfiguration() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("*"));
        config.setAllowCredentials(true);
        config.setAllowedMethods(Collections.singletonList("*"));
        config.setAllowedHeaders(Collections.singletonList("*"));
        config.setExposedHeaders(Collections.singletonList("Set-Cookie"));
        config.setMaxAge(3600L);

        source.registerCorsConfiguration("/api/**", config);

        return config;
    }

}
