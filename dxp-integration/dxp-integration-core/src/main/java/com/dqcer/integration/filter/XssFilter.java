package com.dqcer.integration.filter;

import com.dqcer.dxptools.core.StrUtil;
import com.dqcer.integration.filter.wrapper.XssHttpServletRequestWrapper;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author dqcer
 * @description xss过滤器
 * @date 22:21 2021/4/28
 */
public class XssFilter implements Filter {

    private static Logger log = LoggerFactory.getLogger(XssFilter.class);

    /**
     * 是包含富文本
     */
    private static boolean IS_INCLUDE_RICH_TEXT = false;

    public List<String> excludes = new ArrayList();

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException,ServletException {
        if(log.isDebugEnabled()){
            log.debug("XssFilter is open");
        }

        HttpServletRequest req = (HttpServletRequest) request;
        if(handleExcludeURL(req)){
            if (log.isDebugEnabled()) {
                log.debug("XssFilter not exclude");
            }
            filterChain.doFilter(request, response);
            return;
        }

        XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest) request,IS_INCLUDE_RICH_TEXT);
        filterChain.doFilter(xssRequest, response);
    }

    /**
     * 处理排除url
     *
     * @param request  请求
     * @return boolean
     */
    private boolean handleExcludeURL(HttpServletRequest request) {

        if (excludes == null || excludes.isEmpty()) {
            return false;
        }

        String url = request.getServletPath();
        for (String pattern : excludes) {
            Pattern p = Pattern.compile("^" + pattern);
            Matcher m = p.matcher(url);
            if (m.find()) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void init(FilterConfig filterConfig) {
        if(log.isDebugEnabled()){
            log.debug("XssFilter init");
        }
        String isIncludeRichText = filterConfig.getInitParameter("isIncludeRichText");
        if(StrUtil.isNotBlank(isIncludeRichText)){
            IS_INCLUDE_RICH_TEXT = BooleanUtils.toBoolean(isIncludeRichText);
        }

        String temp = filterConfig.getInitParameter("excludes");
        if (temp != null) {
            String[] url = temp.split(",");
            excludes.addAll(Arrays.asList(url));
        }
    }


    @Override
    public void destroy() {
        if (log.isDebugEnabled()) {
            log.debug("XssFilter destroy ...");
        }
    }
}

