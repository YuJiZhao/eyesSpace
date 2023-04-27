package com.eyes.eyesspace.sync.common.exception;

import com.eyes.eyesAuth.exception.EyesAuthException;
import com.eyes.eyesAuth.exception.EyesLimiterException;
import com.eyes.eyesTools.common.exception.CustomException;
import com.eyes.eyesTools.common.result.Result;
import com.eyes.eyesspace.sync.common.result.ResultCode;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 自定义异常
     * @param e CustomException
     * @return Result<Void>
     */
    @ExceptionHandler(value = CustomException.class)
    @ResponseBody
    public Result<Void> error(CustomException e) {
        log.warn("CustomException: {}", e.getErrorMsg());
        return Result.fail(e.getErrorCode(), e.getErrorMsg());
    }

    /**
     * 未知异常
     * @param e Exception
     * @return Result<Void>
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result<Void> error(Exception e) {
        log.error("未知异常:{}", e.getMessage());
        e.printStackTrace();
        return Result.fail();
    }

    /**
     * 验证异常(controller中使用注解)
     * @param e ConstraintViolationException
     * @return Result<Void>
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseBody
    public Result<Void> error(ConstraintViolationException e) {
        StringBuffer errorMsg = new StringBuffer();
        e.getConstraintViolations().forEach((v) -> errorMsg.append(v.getMessage()).append(";"));
        log.warn("controller验证异常：{}", errorMsg);
        return Result.fail(errorMsg.toString());
    }

    /**
     * 验证异常(module中使用注解)
     * @param e MethodArgumentNotValidException
     * @return Result<Void>
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public Result<Void> error(MethodArgumentNotValidException e) {
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        String message = allErrors.stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(";"));
        log.warn("module验证异常：{}", message);
        return Result.fail(message);
    }

    /**
     * 权限异常
     * @param e EyesAuthException
     * @return Result<Void>
     */
    @ExceptionHandler(value = EyesAuthException.class)
    @ResponseBody
    public Result<Void> error(EyesAuthException e) {
        log.warn("EyesAuthException: {}", e.getMessage());
        return Result.fail(ResultCode.FAILURE.getCode(), e.getMessage());
    }

    /**
     * 接口限流
     * @param e EyesLimiterException
     * @return Result<Void>
     */
    @ExceptionHandler(value = EyesLimiterException.class)
    @ResponseBody
    public Result<Void> error(EyesLimiterException e) {
        log.warn("EyesLimiterException: {}", e.getMessage());
        return Result.fail(ResultCode.FAILURE.getCode(), e.getMessage());
    }
}
