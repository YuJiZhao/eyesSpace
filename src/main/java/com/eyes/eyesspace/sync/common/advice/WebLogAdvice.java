package com.eyes.eyesspace.sync.common.advice;

import com.alibaba.fastjson.JSON;
import com.eyes.eyesAuth.context.UserInfoHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author eyesYeager
 */
@Aspect
@Component
@Order
@Slf4j
public class WebLogAdvice {
    @Pointcut(value = "execution (* com.eyes.eyesspace.sync.controller..*.*(..))")
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
        log.info("method: {}, paras: {}", method.getDeclaringClass() + "." + method.getName(), Arrays.toString(args));
        long start = System.currentTimeMillis();
        Object result = pjp.proceed();
        log.info("result: {}, time: {}", JSON.toJSON(result), System.currentTimeMillis() - start + "ms");
        // TODO: 封装好一点
        // 清除ThreadLocal
        UserInfoHolder.removeAll();
        return result;
    }
}