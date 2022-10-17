package com.eyes.eyesspace.common.service.utils;

import com.eyes.eyesspace.common.service.exception.CustomException;
import com.eyes.eyesspace.common.tool.result.ResultCode;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebUtils {
    /**
     * 获取HttpServletRequest
     * @return
     */
    public static HttpServletRequest getRequest() throws CustomException {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null){
            throw new CustomException(ResultCode.NONE_HTTP_SERVLET_REQUEST);
        }
        return ((ServletRequestAttributes)requestAttributes).getRequest();
    }

    /**
     * 获取HttpServletResponse
     * @return
     */
    public static HttpServletResponse getResponse(){
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null){
            return null;
        }
        return ((ServletRequestAttributes)requestAttributes).getResponse();
    }
}
