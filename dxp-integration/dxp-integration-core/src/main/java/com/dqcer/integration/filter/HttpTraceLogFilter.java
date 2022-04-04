package com.dqcer.integration.filter;

import com.dqcer.framework.base.constants.HttpHeaderConstants;
import com.dqcer.framework.base.constants.TraceConstants;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalTime;
import java.util.*;

/**
 * @author huangzipeng
 * 2022/2/23 4:06 PM
 */
public class HttpTraceLogFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(HttpTraceLogFilter.class);

    private static final String[] headerNames = {
            TraceConstants.TRACE_ID_HEADER,
            HttpHeaderConstants.AUTHORIZATION
    };

    private static final String IGNORE_CONTENT_TYPE = "multipart/form-data";


    /**
     * 设置TraceId
     *
     * @param request 请求
     * @return boolean
     */
    private boolean setTraceId(HttpServletRequest request) {
        String traceId = request.getHeader(TraceConstants.TRACE_ID_HEADER);
        if(null == traceId || traceId.trim().length() == 0) {
            traceId = UUID.randomUUID().toString();
        }
        MDC.put(TraceConstants.LOG_TRACE_ID, traceId);
        return true;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (!isRequestValid(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        if (!(request instanceof ContentCachingRequestWrapper)) {
            request = new ContentCachingRequestWrapper(request);
        }
        if (!(response instanceof ContentCachingResponseWrapper)) {
            response = new ContentCachingResponseWrapper(response);
        }
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        long startTime = System.currentTimeMillis();
        boolean isSetTraceId = false;
        try {
            isSetTraceId = setTraceId(request);
            filterChain.doFilter(request, response);
            status = response.getStatus();
        } finally {
            String path = request.getRequestURI();
            if (!Objects.equals(IGNORE_CONTENT_TYPE, request.getContentType())) {
                //1. 记录日志
                HttpTraceLog traceLog = new HttpTraceLog();
                traceLog.setPath(path);
                traceLog.setMethod(request.getMethod());
                long latency = System.currentTimeMillis() - startTime;
                traceLog.setTimeTaken(latency);
                traceLog.setTime(LocalTime.now().toString());
                traceLog.setHeaders(getHeaders(request));
                traceLog.setParameterMap(getParamsMap(request));
                traceLog.setStatus(status);
                traceLog.setRequestBody(getRequestBody(request));
                traceLog.setResponseBody(getResponseBody(response));
                log.info("Http trace log: {}", buildTraceLog(traceLog));
                if(isSetTraceId) {
                    MDC.remove(TraceConstants.LOG_TRACE_ID);
                }
            }
            updateResponse(response);
        }
    }


    /**
     * @param traceLog traceLog
     * @return log str
     */
    private String buildTraceLog(HttpTraceLog traceLog) {
        return String.format("%s %s %s %d cost %sms parameterMap=%s requestBody=%s, responseBody=%s, header=%s",
                traceLog.getTime(),
                traceLog.getMethod(),
                traceLog.getPath(),
                traceLog.getStatus(),
                traceLog.getTimeTaken().toString(),
                traceLog.getParameterMap(),
                traceLog.getRequestBody(),
                traceLog.getResponseBody(),
                traceLog.getHeaders());
    }

    /**
     * @param request http request
     * @return boolean
     */
    private boolean isRequestValid(HttpServletRequest request) {
        try {
            new URI(request.getRequestURL().toString());
            return true;
        } catch (URISyntaxException ex) {
            return false;
        }
    }

    /**
     * @param request req
     * @return headers
     */
    private String getHeaders(HttpServletRequest request) {
        StringBuilder headerStr = new StringBuilder();
        headerStr.append('{');
        for (String headerName : headerNames) {
            String value = request.getHeader(headerName);
            headerStr.append(headerName).append('=');
            if(null != value && value.trim().length() != 0) {
                headerStr.append(value);
            }
            headerStr.append(',');
        }
        if(headerStr.length() > 1) {
            headerStr.deleteCharAt(headerStr.length() - 1);
        }
        headerStr.append('}');
        return headerStr.toString();
    }

    /**
     * @param request req
     * @return headers
     */
    private String getParamsMap(HttpServletRequest request) {
        StringBuilder paramsStr = new StringBuilder();
        paramsStr.append('{');
        Map<String, String[]> map = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            String[] values = entry.getValue();
            paramsStr.append(entry.getKey()).append('=');
            if(values != null && values.length > 0) {
                List<String> valueList = Arrays.asList(values);
                paramsStr.append(valueList);
            } else {
                paramsStr.append("[]");
            }
            paramsStr.append(',');
        }
        if(paramsStr.length() > 1) {
            paramsStr.deleteCharAt(paramsStr.length() - 1);
        }
        paramsStr.append('}');
        return paramsStr.toString();
    }

    /**
     * @param request req
     * @return request body
     */
    private String getRequestBody(HttpServletRequest request) {
        String requestBody = "";
        ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
        if (wrapper != null) {
            try {
                requestBody = IOUtils.toString(wrapper.getContentAsByteArray(), "UTF-8");
            } catch (IOException e) {
                // NOOP
            }
        }
        return requestBody;
    }

    /**
     * @param response resp
     * @return response body
     */
    private String getResponseBody(HttpServletResponse response) {
        String responseBody = "";
        ContentCachingResponseWrapper wrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        if (wrapper != null) {
            try {
                responseBody = IOUtils.toString(wrapper.getContentAsByteArray(), "UTF-8");
            } catch (IOException e) {
                // NOOP
            }
        }
        return responseBody;
    }

    /**
     * @param response resp
     * @throws IOException ex
     */
    private void updateResponse(HttpServletResponse response) throws IOException {
        ContentCachingResponseWrapper responseWrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        Objects.requireNonNull(responseWrapper).copyBodyToResponse();
    }


    /**
     * 日志记录
     */
    private static class HttpTraceLog {

        private String time;
        private String method;
        private String path;
        private Long timeTaken;
        private Integer status;
        private String parameterMap;
        private String requestBody;
        private String headers;
        private String responseBody;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getParameterMap() {
            return parameterMap;
        }

        public void setParameterMap(String parameterMap) {
            this.parameterMap = parameterMap;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public Long getTimeTaken() {
            return timeTaken;
        }

        public void setTimeTaken(Long timeTaken) {
            this.timeTaken = timeTaken;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getRequestBody() {
            return requestBody;
        }

        public void setRequestBody(String requestBody) {
            this.requestBody = requestBody;
        }

        public String getHeaders() {
            return headers;
        }

        public void setHeaders(String headers) {
            this.headers = headers;
        }

        public String getResponseBody() {
            return responseBody;
        }

        public void setResponseBody(String responseBody) {
            this.responseBody = responseBody;
        }
    }

}
