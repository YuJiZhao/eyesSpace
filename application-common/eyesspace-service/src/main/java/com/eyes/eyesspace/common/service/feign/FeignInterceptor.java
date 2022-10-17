package com.eyes.eyesspace.common.service.feign;

import com.eyes.eyesspace.common.tool.context.ConfigContext;
import feign.RequestInterceptor;
import feign.RequestTemplate;

public class FeignInterceptor implements RequestInterceptor {
    private static final String FEIGN_TOKEN = ConfigContext.getString("FEIGN_TOKEN");
    private static final String FEIGN_AUTHORIZATION = ConfigContext.getString("FEIGN_AUTHORIZATION");

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(FEIGN_TOKEN, FEIGN_AUTHORIZATION);
    }
}