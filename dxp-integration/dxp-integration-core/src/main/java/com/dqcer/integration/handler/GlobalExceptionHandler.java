package com.dqcer.integration.handler;

import com.dqcer.dxpframework.api.ResultApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * @author dongqin
 * @description 全局异常处理程序
 * @date 2021/08/17
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(this.getClass());


    /**
     * 异常
     *
     * @param exception 异常
     * @return {@link ResultApi}
     */
    @ExceptionHandler(value = Exception.class)
    public ResultApi exception(Exception exception) {
        log.error("系统异常: {} {}", exception.getMessage(), exception);
        return ResultApi.error(exception.getMessage());
    }

    /**
     * 方法参数无效异常处理
     *
     * @param exception 异常
     * @return {@link ResultApi}
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResultApi methodArgumentNotValidException(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        StringBuilder stringBuilder = new StringBuilder();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        ResultApi resultApi = ResultApi.warn();
        for (FieldError fieldError : fieldErrors) {
            String defaultMessage = fieldError.getDefaultMessage();
            String field = fieldError.getField();
            Object rejectedValue = fieldError.getRejectedValue();
            stringBuilder.append(field).append(":'");
            stringBuilder.append(rejectedValue);
            stringBuilder.append("' ");
            stringBuilder.append("message:");
            stringBuilder.append(defaultMessage).append("\t");
            resultApi.put(field, defaultMessage);
        }
        log.error("参数绑定异常: {} {}", exception.getParameter(), stringBuilder);
        return resultApi;
    }
}
