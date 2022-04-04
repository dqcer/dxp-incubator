package com.dqcer.integration.interceptor;

import com.alibaba.fastjson.JSON;
import com.dqcer.integration.cache.operation.CacheChannel;
import com.dqcer.tools.core.IpAddressUtil;
import com.dqcer.tools.core.ObjUtil;
import com.dqcer.tools.core.StrUtil;
import com.dqcer.framework.base.bean.Result;
import com.dqcer.framework.base.bean.ResultCode;
import com.dqcer.framework.base.constants.HttpHeaderConstants;
import com.dqcer.framework.storage.*;
import com.dqcer.integration.annotation.UnAuthorize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dongqin
 * @description 基础信息拦截器
 * @date 2021/08/19
 */
public class BaseInfoInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(BaseInfoInterceptor.class);

    private final CacheChannel redissonObject;

    public BaseInfoInterceptor(CacheChannel redissonObject) {
        this.redissonObject = redissonObject;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String requestURI = request.getRequestURI();
        if (log.isDebugEnabled()) {
            log.debug("BaseInfoInterceptor#preHandle requestURI:[{}]", requestURI);
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        HandlerMethod method = (HandlerMethod) handler;

        // 请求参数
        LocalDateTime now = LocalDateTime.now();
        MethodParameter[] methodParameters = method.getMethodParameters();
        StringBuilder key = new StringBuilder();
        if (ObjUtil.isNotNull(methodParameters)) {
            for (Object arg : methodParameters) {
                if (ObjUtil.isNotNull(arg)) {
                    boolean execute = isExecute(arg);
                    if (execute) {
                        if (arg instanceof MultipartFile) {
                            MultipartFile multipartFile = (MultipartFile) arg;
                            Map<String, Object> map = new HashMap<>(8);
                            map.put("fileName", multipartFile.getOriginalFilename());
                            map.put("size", multipartFile.getSize());
                            String stringPretty = map.toString();
                            key.append(stringPretty);
                        } else {
                            String stringPretty = arg.toString();
                            key.append(stringPretty);
                        }
                    }
                }
            }
        }
        UnifyParameter parameter = new UnifyParameter();
        parameter.setParameter(key.toString());
        parameter.setStartTime(now);
        QueryStorage.setParameter(parameter);

        // 获取当前用户信息
        UnifySession unifySession = new UnifySession();
        unifySession.setLanguage(request.getHeader(HttpHeaders.ACCEPT_LANGUAGE));
        unifySession.setIp(IpAddressUtil.getIpAddr(request));

        UnAuthorize unauthorize = method.getMethodAnnotation(UnAuthorize.class);
        if (null != unauthorize) {
            if (log.isDebugEnabled()) {
                log.debug("UnAuthorize: {}", requestURI);
            }
            UserStorage.setSession(unifySession);
            return true;
        }

        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (log.isDebugEnabled()) {
            log.debug("Authorization: {}", authorization);
        }

        if (StrUtil.isBlank(authorization) || !authorization.startsWith(HttpHeaderConstants.BEARER)) {
            response.getWriter().write(JSON.toJSONString(Result.error(ResultCode.UN_AUTHORIZATION)));
            //  认证失败
            return false;
        }

        String token = authorization.substring(HttpHeaderConstants.BEARER.length());

        Object obj = redissonObject.get(MessageFormat.format(CacheConstant.SSO_TOKEN, token), String.class);
        if (ObjUtil.isNull(obj)) {
            log.warn("BaseInfoInterceptor:  7天已过期");
            response.getWriter().write(JSON.toJSONString(Result.error(ResultCode.UN_AUTHORIZATION)));
            return false;
        }

        CacheUser user = (CacheUser) obj;

        if (log.isDebugEnabled()) {
            log.debug("CacheUser:{}", user);
        }

        if (CacheUser.OFFLINE.equals(user.getOnlineStatus())) {
            log.warn("BaseInfoInterceptor:  异地登录");
            response.getWriter().write(JSON.toJSONString(Result.error(ResultCode.UN_AUTHORIZATION)));
            //  异地登录
            return false;
        }

        LocalDateTime lastActiveTime = user.getLastActiveTime();


        if (lastActiveTime.plusMinutes(30).isBefore(now)) {
            log.warn("BaseInfoInterceptor:  用户操作已过期");
            response.getWriter().write(JSON.toJSONString(Result.error(ResultCode.UN_AUTHORIZATION)));
            return false;
        }


        redissonObject.put(MessageFormat.format(CacheConstant.SSO_TOKEN, token), user.setLastActiveTime(now), 10);

        unifySession.setAccountId(user.getAccountId());
        unifySession.setTenantId(user.getTenantId());
        UserStorage.setSession(unifySession);

        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        UserStorage.clearSession();
        QueryStorage.clearParameter();
    }

    /**
     * 是否执行
     *
     * @param object 对象
     * @return boolean
     */
    public boolean isExecute(Object object) {
        if (object instanceof HttpServletRequest) {
            return false;
        }
        return !(object instanceof HttpServletResponse);
    }
}
