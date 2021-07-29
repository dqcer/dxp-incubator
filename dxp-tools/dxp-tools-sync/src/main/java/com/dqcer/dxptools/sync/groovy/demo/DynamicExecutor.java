package com.dqcer.dxptools.sync.groovy.demo;

import java.util.concurrent.ConcurrentHashMap;

public class DynamicExecutor {

    /**
     * 缓存实例
     */
    private ConcurrentHashMap<String, IBaseService> objCache = new ConcurrentHashMap<>(32);

    /**
     * 执行脚本
     *
     * @param id 实例Id
     * @return 运行结果
     */
    public Object run(String id, String param) {
        IBaseService script = objCache.get(id);
        if (script == null) {
            throw new IllegalArgumentException("未找到实例, id = [" + id + "]");
        }
//            return script.view(param);
        return script.view();

    }

    /**
     * 注册实例
     *
     * @param id 实例id
     * @param script 实例
     * @return 返回前一个实例，如果为null，则是新插入
     */
    public IBaseService register(String id, IBaseService script) {
        return objCache.put(id, script);
    }

    /**
     * 移除实例
     *
     * @param id 实例id
     * @return 移除的实例
     */
    public IBaseService remove(String id) {
        return objCache.remove(id);
    }

}
