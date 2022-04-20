package com.dqcer.provider.uac.handler;

public interface ServiceTemplate {

    Object invoke(Context context, ActionHandler action);
}
