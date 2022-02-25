package com.dqcer.gateway.filter;

import com.dqcer.framework.base.api.Result;
import com.dqcer.framework.base.api.ResultCode;
import com.dqcer.framework.base.constants.HttpHeaderConstants;
import com.dqcer.gateway.config.EccGatewayProperties;
import com.dqcer.gateway.config.GatewayResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * @author dongqin
 * @description 身份验证过滤器
 * @date 2022/01/13
 */
@Component
public class AuthFiler implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(AuthFiler.class);

    @Resource
    private EccGatewayProperties properties;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        ServerHttpRequest.Builder mutate = request.mutate();
        if (isIgnoreToken(request.getPath().toString())) {
            if (log.isDebugEnabled()) {
                log.debug("Gateway Filer ignore url：{}", request.getPath());
            }
            return chain.filter(exchange);
        }

        HttpHeaders headers = request.getHeaders();

        String authorization = headers.getFirst(HttpHeaderConstants.AUTHORIZATION);
        if (log.isDebugEnabled()) {
            log.debug("Gateway Filer Authorization: {}", authorization);
        }

        if (null == authorization || authorization.trim().length() == 0) {
            return errorResponse(response, ResultCode.UN_AUTHORIZATION.code(), ResultCode.UN_AUTHORIZATION.msg());
        }
        String token = authorization.substring(HttpHeaderConstants.BEARER.length());
        if (null == token || token.length() == 0) {
            return errorResponse(response, ResultCode.UN_AUTHORIZATION.code(), ResultCode.UN_AUTHORIZATION.msg());
        }

        //  租户
        String tenantIdStr = headers.getFirst(HttpHeaderConstants.T_ID);
        if (log.isDebugEnabled()) {
            log.debug("Gateway Filer TenantId: [{}]", tenantIdStr);
        }
        if (null == tenantIdStr || tenantIdStr.trim().length() == 0) {
            log.error("Gateway Filer tenantIdStr is null");
            return errorResponse(response, GatewayResultCode.PARAM_TENANT_NULL.code(), GatewayResultCode.PARAM_TENANT_NULL.msg());
        }

        if (!isNumber(tenantIdStr)) {
            log.error("Gateway Filer tenantId: [{}] is no number", tenantIdStr);
            return errorResponse(response, ResultCode.ERROR_PARAMETERS.code(), ResultCode.ERROR_PARAMETERS.msg());
        }

        // 获取accountId
        Long tenantId = Long.valueOf(tenantIdStr);

        //  todo Result result = validToken(token, tenantId) 验证token
        Result result = Result.ok();

        if (log.isDebugEnabled()) {
            log.debug("Gateway Filer 'authServiceClient.tokenValid(validDTO)' result : {}", result);
        }

        if (!result.isOk()) {
            return errorResponse(response, result.getCode(), result.getMsg());
        }

        return chain.filter(exchange.mutate().request(mutate.build()).build());
    }

    @Override
    public int getOrder() {
        return -1000;
    }

    /**
     * 忽略 用户token
     */
    protected boolean isIgnoreToken(String path) {
        return properties.getIgnore().isIgnoreToken(path);
    }

    /**
     * 添加头部
     *
     * @param mutate 变异
     * @param name   的名字
     * @param value  值
     */
    private void addHeader(ServerHttpRequest.Builder mutate, String name, Object value) {
        if (value == null) {
            return;
        }
        String valueStr = value.toString();
        mutate.header(name, valueStr);
    }


    /**
     * 错误响应
     *
     * @param response 响应
     * @param code     代码
     * @param msg      味精
     * @return {@link Mono<Void>}
     */
    protected Mono<Void> errorResponse(ServerHttpResponse response, int code, String msg) {
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.setStatusCode(HttpStatus.OK);
        String m = "{\"code\":" + code +", \"data\":null, \"msg\":\"" + msg  +"\"}";
        DataBuffer dataBuffer = response.bufferFactory().wrap(m.getBytes());
        return response.writeWith(Mono.just(dataBuffer));
    }


    /**
     * 是否为数字
     *
     * @param str str
     * @return boolean
     */
    private boolean isNumber(String str) {
        return str.matches("^[0-9]*$");
    }
}
