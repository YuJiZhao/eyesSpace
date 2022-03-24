package com.eyes.eyesblog.config;

import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Configuration
public class FeignHeaderConfig {
    final String[] copyHeaders = new String[]{"cookie"};

    /**
     * 重写后feign转发请求会携带原请求的Head信息
     */
    private class FeignBasicAuthRequestInterceptor implements RequestInterceptor {
        @Override
        public void apply(RequestTemplate requestTemplate) {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                Enumeration<String> headerNames = request.getHeaderNames();
                if (headerNames != null) {
                    while (headerNames.hasMoreElements()) {
                        String name = headerNames.nextElement();
                        String values = request.getHeader(name);
                        if (iscopy(name)) {
                            requestTemplate.header(name, values);
                        }
                    }
                }
            }
        }
    }

    /**
     * @param name String
     * @return boolean
     */
    private Boolean iscopy(String name) {
        for (String header : copyHeaders) {
            if (header.equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * feign请求拦截器
     *
     * @return RequestInterceptor
     */
    @Bean
    public RequestInterceptor requestInterceptor() {
        return new FeignBasicAuthRequestInterceptor();
    }

    /**
     * 开启feign调用日志打印的debug模式
     *
     * @return
     */
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}