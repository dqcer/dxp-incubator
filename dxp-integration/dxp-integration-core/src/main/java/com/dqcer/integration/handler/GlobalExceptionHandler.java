package com.dqcer.integration.handler;

import com.dqcer.dxpframework.api.ResultApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author dongqin
 * @description 全局异常处理程序
 * @date 2021/08/17
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResultApi methodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException exception) {

        BindingResult bindingResult = exception.getBindingResult();
        StringBuffer stringBuffer = new StringBuffer();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            String defaultMessage = fieldError.getDefaultMessage();
            String field = fieldError.getField();
            Object rejectedValue = fieldError.getRejectedValue();
            stringBuffer.append(field + ":'");
            stringBuffer.append(rejectedValue);
            stringBuffer.append("' ");
            stringBuffer.append("message:");
            stringBuffer.append(defaultMessage + "\t");
        }
        log.error("参数绑定异常: {} {}", exception.getParameter(), stringBuffer.toString());
        return ResultApi.info(stringBuffer.toString());
    }
}
