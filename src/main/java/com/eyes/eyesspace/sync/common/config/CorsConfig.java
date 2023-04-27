package com.eyes.eyesspace.sync.common.config;

import java.util.Collections;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author eyesYeager
 * @date 2023/2/13 17:17
 */
@Order(1)
@Configuration
public class CorsConfig {
  @Bean
  FilterRegistrationBean<CorsFilter> corsFilter() {
    // 配置跨域策略
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
    corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
    corsConfiguration.setAllowedOrigins(Collections.singletonList("*"));
    corsConfiguration.setMaxAge(3600L);

    // 将配置注册到CorsConfiguration中
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfiguration);

    // 放进Bean中
    FilterRegistrationBean<CorsFilter> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setFilter(new CorsFilter(source));
    registrationBean.setOrder(-1);

    return registrationBean;
  }
}
