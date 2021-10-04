package com.dqcer.dxpprovider.sso.web.service;

import com.dqcer.dxpframework.api.ResultApi;

public abstract class AuthTemplatePattern {

    public Object auth() {
        ResultApi resultApi = verificationCode();
        if (!resultApi.getSuccess()) {
            return resultApi;
        }

        resultApi = validPermissions();
        if (!resultApi.getSuccess()) {
            return resultApi;
        }
        return resultApi;
    }

    protected abstract ResultApi verificationCode();

    protected abstract ResultApi validPermissions();

}
