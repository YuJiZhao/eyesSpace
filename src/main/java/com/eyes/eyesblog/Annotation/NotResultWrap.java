package com.eyes.eyesblog.Annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 不进行返回值包装注解
 */
@Target({METHOD})
@Retention(RUNTIME)
public @interface NotResultWrap {
}
