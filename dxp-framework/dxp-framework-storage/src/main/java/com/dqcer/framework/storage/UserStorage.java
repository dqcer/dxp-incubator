package com.dqcer.framework.storage;

/**
 * @author dongqin
 * @description 用户存储
 * @date 2021/08/19
 */
public class UserStorage {

    /**
     * 统一的会话
     */
    static ThreadLocal<UnifySession> UNIFY_SESSION = new InheritableThreadLocal();

    /**
     * 获取会话
     *
     * @return {@link UnifySession}
     */
    public static UnifySession getSession() {
        return UNIFY_SESSION.get();
    }

    /**
     * 设置会话
     *
     * @param box 盒子
     */
    public static void setSession(UnifySession box) {
        UNIFY_SESSION.set(box);
    }

    /**
     * 清除会话
     */
    public static void clearSession() {
        UNIFY_SESSION.remove();
    }

}
