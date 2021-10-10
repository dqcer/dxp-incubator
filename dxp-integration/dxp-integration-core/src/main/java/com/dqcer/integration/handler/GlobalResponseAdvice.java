package com.dqcer.integration.handler;

import com.dqcer.dxpframework.api.ResultApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.Resource;

/**
 * @author dongqin
 * @description 全局响应处理
 * @date 2021/10/04 23:10:42
 */
@Order(-200)
@RestControllerAdvice
public class GlobalResponseAdvice implements ResponseBodyAdvice<Object> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    private MessageSource messageSource;

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object object, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if(object instanceof ResultApi){
            if (log.isInfoEnabled()) {
                log.info("ResultApi 国际化处理...");
            }
            ResultApi resultApi = (ResultApi) object;
            //resultApi.setMessage(ResourceUtils.get(resultApi.getCode()));
            resultApi.setMessage(messageSource.getMessage(resultApi.getCode(), null, ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getLocale()));
        }
        return object;

    }
}
