package com.dqcer.gateway.filter;

import com.dqcer.framework.base.constants.TraceConstants;
import com.dqcer.gateway.properties.EccGatewayProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * @author dongqin
 * @description 日志过滤器
 * @date 2022/01/13
 */
@Component
public class LogFilter implements GlobalFilter, Ordered {

	private static final Logger log = LoggerFactory.getLogger(LogFilter.class);

	private static final String START_TIME = "startTime";

	@Resource
	private EccGatewayProperties properties;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		if (!properties.getEnableLog()) {
			return chain.filter(exchange);
		}
		String requestUrl = exchange.getRequest().getURI().getRawPath();
		String traceId = exchange.getRequest().getHeaders().getFirst(TraceConstants.TRACE_ID_HEADER);
		StringBuilder beforeReqLog = new StringBuilder(512);
		// 日志参数
		List<Object> beforeReqArgs = new ArrayList<>();
		beforeReqLog.append("\n\n================ Gateway Request Start ================\n");
		beforeReqLog.append("traceId: ");
		beforeReqLog.append(traceId);

		// 打印路由
		beforeReqLog.append("===> {}: {}\n");
		// 参数
		String requestMethod = exchange.getRequest().getMethodValue();
		beforeReqArgs.add(requestMethod);
		beforeReqArgs.add(requestUrl);

		// 打印请求头
		HttpHeaders headers = exchange.getRequest().getHeaders();
		headers.forEach((headerName, headerValue) -> {
			beforeReqLog.append("===Headers===  {}: {}\n");
			beforeReqArgs.add(headerName);
			beforeReqArgs.add(":" + headerValue);
		});
		beforeReqLog.append("================  Gateway Request End =================\n");
		if (log.isDebugEnabled()) {
			// 打印执行时间
			log.debug(beforeReqLog.toString(), beforeReqArgs.toArray());
		}

		exchange.getAttributes().put(START_TIME, System.currentTimeMillis());
		return chain.filter(exchange).then(Mono.fromRunnable(() -> {
			ServerHttpResponse response = exchange.getResponse();
			Long startTime = exchange.getAttribute(START_TIME);
			long executeTime = 0L;
			if (startTime != null) {
				executeTime = (System.currentTimeMillis() - startTime);
			}

			// 构建成一条长 日志，避免并发下日志错乱
			StringBuilder responseLog = new StringBuilder(300);
			// 日志参数
			List<Object> responseArgs = new ArrayList<>();
			responseLog.append("\n\nGateway LogFilter Response Start\n");
			// 打印路由
			responseLog.append("<=== {} {}: {}: {}\n");
			// 参数
			responseArgs.add(response.getStatusCode().value());
			responseArgs.add(requestMethod);
			responseArgs.add(requestUrl);
			responseArgs.add(executeTime + "ms");

			// 打印请求头
			HttpHeaders httpHeaders = response.getHeaders();
			httpHeaders.forEach((headerName, headerValue) -> {
				responseLog.append("===Headers===  {}: {}\n");
				responseArgs.add(headerName);
				responseArgs.add(":" + headerValue);
			});

			responseLog.append("Gateway LogFilter Response End\n");
			// 打印执行时间
			if (log.isDebugEnabled()) {
				log.debug(responseLog.toString(), responseArgs.toArray());
			}
		}));
	}

	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}
}
