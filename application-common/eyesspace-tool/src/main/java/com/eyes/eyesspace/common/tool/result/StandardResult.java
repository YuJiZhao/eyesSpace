package com.eyes.eyesspace.common.tool.result;

public interface StandardResult<T> {
    Integer getCode();

    String getMsg();

    T getData();
}
