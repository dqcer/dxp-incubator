package com.dqcer.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

/**
 * @author dongqin
 * @description 开放api过滤器
 * @date 2021/09/13
 */
public class OpenApiFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(AuthFilter.class);

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("OpenApi filter");

        boolean execute = isExecute(exchange);
        if (execute) {
            return chain.filter(exchange);
        }

        return chain.filter(exchange);
    }

    /**
     * 是否执行
     *
     * @param exchange 交换
     * @return boolean
     */
    private boolean isExecute(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        List<String> pages = excludePathPatterns();
        for (String page : pages) {
            if (antPathMatcher.match(page, path)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 要排除的路径集
     *
     * @return {@link List<String>}
     */
    private List<String> excludePathPatterns() {
        return Collections.singletonList("");
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
