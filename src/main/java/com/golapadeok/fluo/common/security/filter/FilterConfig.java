package com.golapadeok.fluo.common.security.filter;

import com.golapadeok.fluo.common.annotation.interception.AuthCheckInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Configuration
public class FilterConfig {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public FilterRegistrationBean<AuthCheckInterceptor> authCheckFilter() {
        FilterRegistrationBean<AuthCheckInterceptor> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AuthCheckInterceptor());
        registrationBean.addUrlPatterns("/api/*"); // URL 패턴에 따라 적절히 설정
        return registrationBean;
    }

}
