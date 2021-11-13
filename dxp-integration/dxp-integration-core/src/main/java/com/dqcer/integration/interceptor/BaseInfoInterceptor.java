package com.dqcer.integration.interceptor;

import com.alibaba.fastjson.JSON;
import com.dqcer.dxpframework.api.ResultApi;
import com.dqcer.dxptools.core.IpAddressUtil;
import com.dqcer.dxptools.core.ObjUtil;
import com.dqcer.framework.storage.*;
import com.dqcer.integration.operation.RedissonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
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

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    private RedissonObject redissonObject;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (log.isDebugEnabled()) {
            log.debug("Authorization: {}", authorization);
        }

        if (!authorization.startsWith(HeaderConstant.BEARER)) {
            response.getWriter().write(JSON.toJSONString(ResultApi.error("999400")));
            //  认证失败
            return false;
        }

        String token = authorization.substring(HeaderConstant.BEARER.length());

        Object obj = redissonObject.getValue(MessageFormat.format(CacheConstant.SSO_TOKEN, token));
        if (ObjUtil.isNull(obj)) {
            response.getWriter().write(JSON.toJSONString(ResultApi.error("999401")));
            //  7天已过期
            return false;
        }

        CacheUser user = (CacheUser) obj;

        if (log.isDebugEnabled()) {
            log.debug("CacheUser:{}", user);
        }

        if (CacheUser.OFFLINE.equals(user.getOnlineStatus())) {
            response.getWriter().write(JSON.toJSONString(ResultApi.error("999402")));
            //  异地登录
            return false;
        }

        LocalDateTime lastActiveTime = user.getLastActiveTime();
        LocalDateTime now = LocalDateTime.now();

        if (lastActiveTime.plusMinutes(30).isBefore(now)) {
            response.getWriter().write(JSON.toJSONString(ResultApi.error("999401")));
            //  用户操作已过期
            return false;
        }


        redissonObject.setValue(MessageFormat.format(CacheConstant.SSO_TOKEN, token), user.setLastActiveTime(now));


        // TODO: 2021/8/21 获取当前用户信息
        UnifySession unifySession = new UnifySession();
        unifySession.setAccountId(user.getAccountId());
        unifySession.setLanguage(request.getHeader(HttpHeaders.ACCEPT_LANGUAGE));
        unifySession.setIp(IpAddressUtil.getIpAddr(request));
        UserStorage.setSession(unifySession);
        // TODO: 2021/11/13 请求参数
        HandlerMethod method = (HandlerMethod) handler;
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
