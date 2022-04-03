package com.dqcer.integration.datasource.interceptor;

import com.dqcer.framework.base.constants.HttpHeaderConstants;
import com.dqcer.integration.datasource.config.DynamicContextHolder;
import com.dqcer.integration.datasource.properties.DataSourceProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author dongqin
 * @description 动态数据源拦截器
 * @date 2022/01/11
 */
public class DynamicDatasourceInterceptor implements HandlerInterceptor {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    private DataSourceProperties properties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String tenant = request.getHeader(HttpHeaderConstants.T_ID);
        boolean isNotEmpty = null != tenant && tenant.trim().length() > 0;
        if (log.isDebugEnabled()) {
            log.debug("DynamicDatasource Interceptor tenant : [{}]  default: [{}]", tenant, properties.getDefaultName());
        }
        if (isNotEmpty) {
            DynamicContextHolder.push(tenant);
        }
        // 底层处理null逻辑
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        DynamicContextHolder.clear();
    }
}
