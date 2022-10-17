package com.eyes.eyesspace.infrastructure.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Configuration
public class RateLimitConfig {
    /**
     * 接口限流
     */
//    @Bean
//    @Primary
//    public KeyResolver pathKeyResolver() {
//        return exchange -> Mono.just(
//                exchange.getRequest()
//                        .getPath()
//                        .toString()
//        );
//    }

    /**
     * IP限流
     */
    @Bean
    public KeyResolver ipKeyResolver() {
        return exchange -> Mono.just(
                Objects.requireNonNull(exchange.getRequest()
                        .getRemoteAddress())
                        .getHostName()
        );
    }
}