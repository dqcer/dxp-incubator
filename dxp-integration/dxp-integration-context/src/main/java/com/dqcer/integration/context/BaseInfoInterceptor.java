package com.dqcer.integration.context;

import com.dqcer.framework.storage.UserStorage;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author dongqin
 * @description 基础信息拦截器
 * @date 2021/08/19
 */
public class BaseInfoInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // TODO: 2021/8/21 获取当前用户信息
        UserStorage.Box box = new UserStorage.Box();
        box.setUserId(123456789L);
        box.setUsername("dqcer@sina.com");
        UserStorage.setBox(box);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        UserStorage.clearBox();
    }
}
