package com.dqcer.integration.handler;

import com.dqcer.dxpframework.api.ResultApi;
import com.dqcer.integration.util.ResourceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author dongqin
 * @description 全局响应处理
 * @date 2021/10/04 23:10:42
 */
@Order(-200)
@RestControllerAdvice
public class GlobalResponseAdvice implements ResponseBodyAdvice<Object> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object object, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (log.isInfoEnabled()) {
            log.info("GlobalResponseAdvice... object: {} methodParameter: {}", object, methodParameter);
        }
        if(object instanceof ResultApi){
            ResultApi resultApi = (ResultApi) object;
            if (!resultApi.getSuccess()) {
                resultApi.setMessage(ResourceUtils.get(resultApi.getCode()));
            }
        }
        return object;

    }
}
