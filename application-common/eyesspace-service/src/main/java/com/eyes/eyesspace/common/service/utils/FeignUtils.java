package com.eyes.eyesspace.common.service.utils;

import com.eyes.eyesspace.common.tool.context.ConfigContext;
import com.eyes.eyesspace.common.service.exception.CustomException;
import javax.servlet.http.HttpServletRequest;

public class FeignUtils {
    private static final String FEIGN_TOKEN = ConfigContext.getString("FEIGN_TOKEN");
    private static final String FEIGN_AUTHORIZATION = ConfigContext.getString("FEIGN_AUTHORIZATION");

    public static void permissionVeri() throws CustomException {
        HttpServletRequest request = WebUtils.getRequest();
        System.out.println(request.getHeader(FEIGN_TOKEN));
        if (!FEIGN_AUTHORIZATION.equals(request.getHeader(FEIGN_TOKEN))) {
            throw new CustomException("权限错误");
        }
    }
}
