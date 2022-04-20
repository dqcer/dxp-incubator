package com.dqcer.provider.uac.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ServiceTemplateImpl implements ServiceTemplate {

    private static final Logger log = LoggerFactory.getLogger(ServiceTemplateImpl.class);

    @Override
    public Object invoke(Context context, ActionHandler action) {
        paramValidate(context, action);
        bizValidate(context, action);
        beforeProcess(context, action);
        process(context, action);
        afterProcess(context, action);
        return assemble(context, action);
    }

    private void paramValidate(Context context, ActionHandler action) {
        log.info("paramValidate...");
        action.paramValidate(context);
    }

    private void bizValidate(Context context, ActionHandler action) {
        log.info("bizValidate...");
        action.bizValidate(context);
    }

    private void beforeProcess(Context context, ActionHandler action) {
        log.info("beforeProcess...");
        action.beforeProcess(context);
    }

    private void process(Context context, ActionHandler action) {
        log.info("process...");
        action.process(context);
    }

    private void afterProcess(Context context, ActionHandler action) {
        log.info("afterProcess...");
        action.afterProcess(context);
    }

    private Object assemble(Context context, ActionHandler action) {
        log.info("assemble...");
        return action.assemble(context);
    }
}
