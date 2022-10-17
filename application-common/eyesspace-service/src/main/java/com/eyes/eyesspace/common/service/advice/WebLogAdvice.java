package com.eyes.eyesspace.common.service.advice;

import com.alibaba.fastjson.JSON;
import com.eyes.eyesspace.common.tool.utils.LogUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Component
@Order
public class WebLogAdvice {
    private final HttpServletRequest httpServletRequest;

    public WebLogAdvice(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    @Pointcut(value = "execution (* com.eyes.eyesspace.*.*.controller..*.*(..))")
    public void controllerPointCut() {
    }

    /**
     * 打印日志,包括请求参数、返回结果、所耗时间
     * @param pjp ProceedingJoinPoint
     * @return Object
     */
    @Around(value = "controllerPointCut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();

        Object[] args = pjp.getArgs();

        LogUtils.info(httpServletRequest, LogUtils.logMap(
                "method", method.getDeclaringClass() + "." + method.getName() + "\n",
                "paras", Arrays.toString(args)));

        long start = System.currentTimeMillis();
        Object result = pjp.proceed();
        long timeConsuming = System.currentTimeMillis() - start;

        LogUtils.info(LogUtils.logMap(
                "com/eyes/eyesspace/common/tool/result", JSON.toJSON(result) + "\n",
                "time", timeConsuming + "ms"));
        return result;
    }
}