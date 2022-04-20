package com.dqcer.provider.uac.web.action;

import com.dqcer.provider.uac.handler.Context;
import com.dqcer.provider.uac.handler.GeneralActionHandler;
import org.springframework.stereotype.Service;

@Service
public class UserSaveAction extends GeneralActionHandler {


    /**
     * 组装
     *
     * @param context 上下文
     * @return {@link Object}
     */
    @Override
    public Object assemble(Context context) {
        return context.get(String.class);
    }
}
