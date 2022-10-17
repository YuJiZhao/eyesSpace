package com.eyes.eyesspace.common.service.exception;

import com.eyes.eyesspace.common.tool.result.Result;
import com.eyes.eyesspace.common.tool.result.StandardResult;
import com.eyes.eyesspace.common.tool.utils.LogUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;


@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 自定义异常
     * @param e CustomException
     * @return Result<Void>
     */
    @ExceptionHandler(value = CustomException.class)
    @ResponseBody
    public StandardResult<Void> error(CustomException e, HttpServletRequest request) {
        LogUtils.warn(request, e.getErrorTrueMsg());
        return Result.failure(e.getErrorCode(), e.getErrorMsg());
    }

    /**
     * 未知异常
     * @param e Exception
     * @return Result<Void>
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public StandardResult<Void> error(Exception e, HttpServletRequest request) {
        LogUtils.error(request, e.toString());
        return Result.failure(e.getMessage());
    }

    /**
     * 验证异常(controller中使用注解)
     * @param e ConstraintViolationException
     * @return Result<Void>
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseBody
    public StandardResult<Void> error(ConstraintViolationException e, HttpServletRequest request) {
        StringBuffer errorMsg = new StringBuffer();
        e.getConstraintViolations().forEach((v) -> {
            errorMsg.append(v.getMessage()).append(";");
        });
        LogUtils.warn(request, errorMsg.toString());
        return Result.failure(errorMsg.toString());
    }

    /**
     * 验证异常(module中使用注解)
     * @param e MethodArgumentNotValidException
     * @return Result<Void>
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public StandardResult<Void> error(MethodArgumentNotValidException e, HttpServletRequest request) {
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        String message = allErrors.stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(";"));
        LogUtils.warn(request, message);
        return Result.failure(message);
    }
}
