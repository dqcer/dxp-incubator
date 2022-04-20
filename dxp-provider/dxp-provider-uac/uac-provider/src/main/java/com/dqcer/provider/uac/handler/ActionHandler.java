package com.dqcer.provider.uac.handler;

/**
 * @author dongqin
 * @description 操作处理
 * @date 2022/04/20
 */
public interface ActionHandler {

    /**
     * 参数验证
     *
     * @param context 上下文
     */
    void paramValidate(Context context);

    /**
     * biz验证
     *
     * @param context 上下文
     */
    void bizValidate(Context context);

    /**
     * 之前
     *
     * @param context 上下文
     */
    void beforeProcess(Context context);

    /**
     * 业务处理
     *
     * @param context 上下文
     */
    void process(Context context);

    /**
     * 之后
     *
     * @param context 上下文
     */
    void afterProcess(Context context);

    /**
     * 组装
     *
     * @param context 上下文
     * @return {@link Object}
     */
    Object assemble(Context context);
}
