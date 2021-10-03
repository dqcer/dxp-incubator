package com.dqcer.integration.idempotent.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author dongqin
 * @description http上下文
 * @date 2021/10/03 12:10:93
 */
public class HttpContextUtils {

    private HttpContextUtils() {
    }

    /**
     * 获取HttpRequest
     *
     * @return HttpServletRequest
     */
    public static HttpServletRequest getHttpServletRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.nonNull(requestAttributes)) {
            return requestAttributes.getRequest();
        }
        return null;
    }

    /**
     * 获取Http头中的header
     *
     * @return String
     */
    public static String getHeader(String headerName) {
        HttpServletRequest request = getHttpServletRequest();
        return Objects.requireNonNull(request).getHeader(headerName);
    }
}
