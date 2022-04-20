package com.dqcer.provider.uac.web.service.impl;

import com.dqcer.framework.base.bean.Result;
import com.dqcer.provider.uac.handler.Context;
import com.dqcer.provider.uac.handler.ServiceTemplate;
import com.dqcer.provider.uac.web.action.UserSaveAction;
import com.dqcer.provider.uac.web.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements IUserService {


    @Resource
    private ServiceTemplate serviceTemplate;

    @Resource
    private UserSaveAction userSaveAction;

    @Override
    public Result<Object> getOne() {
        Context context = new Context();
        context.put("这是来自于上下文的参数");
        return Result.ok(serviceTemplate.invoke(context, userSaveAction));
    }
}
