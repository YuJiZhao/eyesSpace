package com.eyes.eyesspace.common.tool.result;

import lombok.Data;

/**
 * 统一返回格式
 */
public class Result{
    /**
     * 成功（无返回数据、默认成功信息）
     * @return Result<Void>
     */
    public static StandardResult<Void> success() {
        NoDataResult noDataResult = new NoDataResult();
        noDataResult.setCode(ResultCode.SUCCESS.getCode());
        noDataResult.setMsg(ResultCode.SUCCESS.getMessage());
        return noDataResult;
    }

    /**
     * 成功（无返回数据、自定义成功信息）
     * @return Result<Void>
     */
    public static StandardResult<Void> success(String message) {
        NoDataResult noDataResult = new NoDataResult();
        noDataResult.setCode(ResultCode.SUCCESS.getCode());
        noDataResult.setMsg(message);
        return noDataResult;
    }

    /**
     * 成功（有返回数据，默认成功信息）
     * @return Result<V>
     */
    public static <V> StandardResult<V> success(V data) {
        DataResult<V> dataResult = new DataResult<>();
        dataResult.code = ResultCode.SUCCESS.getCode();
        dataResult.msg = ResultCode.SUCCESS.getMessage();
        dataResult.data = data;
        return dataResult;
    }

    /**
     * 成功（有返回数据，自定义成功信息）
     * @return Result<V>
     */
    public static <V> StandardResult<V> success(String message, V data) {
        DataResult<V> dataResult = new DataResult<>();
        dataResult.code = ResultCode.SUCCESS.getCode();
        dataResult.msg = message;
        dataResult.data = data;
        return dataResult;
    }

    /**
     * 失败(默认错误信息)
     * @return Result<Void>
     */
    public static StandardResult<Void> failure() {
        NoDataResult noDataResult = new NoDataResult();
        noDataResult.setCode(ResultCode.FAILURE.getCode());
        noDataResult.setMsg(ResultCode.FAILURE.getMessage());
        return noDataResult;
    }

    /**
     * 失败，使用已定义枚举
     */
    public static StandardResult<Void> failure(ResultCode resultCode) {
        NoDataResult noDataResult = new NoDataResult();
        noDataResult.setCode(resultCode.getCode());
        noDataResult.setMsg(resultCode.getMessage());
        return noDataResult;
    }

    /**
     * 失败，使用自定义错误信息
     */
    public static StandardResult<Void> failure(String message) {
        NoDataResult noDataResult = new NoDataResult();
        noDataResult.setCode(ResultCode.FAILURE.getCode());
        noDataResult.setMsg(message);
        return noDataResult;
    }

    /**
     * 失败，使用自定义错误码和错误信息
     */
    public static StandardResult<Void> failure(Integer code, String message) {
        NoDataResult noDataResult = new NoDataResult();
        noDataResult.setCode(code);
        noDataResult.setMsg(message);
        return noDataResult;
    }

    /**
     * 无数据返回模板
     */
    @Data
    public static class NoDataResult implements StandardResult<Void>{
        private Integer code;
        private String msg;

        @Override
        public Void getData() {
            return null;
        }
    }

    /**
     * 有数据返回模板
     */
    @Data
    public static class DataResult<V> implements StandardResult<V>{
        private Integer code;
        private String msg;
        private V data;
    }
}
