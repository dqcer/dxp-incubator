package com.dqcer.gateway.handler;

import com.dqcer.dxpframework.api.Result;
import com.dqcer.dxpframework.api.ResultCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * @author dongqin
 * @description 异常处理程序
 * @date 2021/12/20 00:12:30
 */
public class ExceptionHandler implements WebExceptionHandler, Ordered {

    private static final Logger log = LoggerFactory.getLogger(ExceptionHandler.class);

    @Resource
    private ObjectMapper objectMapper;


    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();

        if (response.isCommitted()) {
            return Mono.error(ex);
        }

        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        if (ex instanceof ResponseStatusException) {
            response.setStatusCode(((ResponseStatusException) ex).getStatus());
        }

        return response.writeWith(Mono.fromSupplier(() -> {
                    DataBufferFactory bufferFactory = response.bufferFactory();
                    try {
                        Result error = Result.error(ResultCode.ERROR_UNKNOWN);
                        log.warn("网关异常处理", ex);
                        return bufferFactory.wrap(objectMapper.writeValueAsBytes(error));

                    } catch (JsonProcessingException e) {
                        log.error("json 处理异常", ex);
                        return bufferFactory.wrap(new byte[0]);
                    }
                }));
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
