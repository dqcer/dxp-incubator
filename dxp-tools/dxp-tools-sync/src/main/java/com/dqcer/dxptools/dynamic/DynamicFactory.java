package com.dqcer.dxptools.dynamic;

import com.dqcer.dxptools.dynamic.dao.BaseDAO;
import com.dqcer.dxptools.dynamic.dao.SqlProvider;
import com.dqcer.dxptools.dynamic.service.IBaseService;
import com.dqcer.dxptools.dynamic.service.MD5Utils;
import groovy.lang.GroovyClassLoader;

import java.lang.reflect.Constructor;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dongqin
 * @description groovy 工具
 * @date 2021/07/28
 */
public class DynamicFactory {

    private static DynamicFactory dynamicFactory = new DynamicFactory();

    /**
     * groovy类加载器
     */
    private GroovyClassLoader groovyClassLoader = new GroovyClassLoader();

    /**
     * 缓存Class
     */
    private ConcurrentHashMap<String, Class<?>> classCache = new ConcurrentHashMap<>();

    public static DynamicFactory getInstance() {
        return dynamicFactory;
    }

    /**
     * 加载创建实例，prototype
     *
     * @param codeSource 源代码
     * @return 实例
     * @throws Exception 异常
     */
    public IBaseService loadNewInstanceService(String codeSource, BaseDAO baseDAO) throws Exception {
        Class<?> aClass = getCodeSourceClass(codeSource);
        if (aClass != null) {
            Constructor<?> constructor = aClass.getConstructor(BaseDAO.class);
            Object instance = constructor.newInstance(baseDAO);
//            Object instance = aClass.newInstance();
            if (instance != null) {
                if (instance instanceof IBaseService) {
                    this.inject((IBaseService) instance);
                    return (IBaseService) instance;
                } else {
                    throw new IllegalArgumentException(String.format("创建实例失败，[{}]不是IScript的子类", instance.getClass()));
                }
            }
        }
        throw new IllegalArgumentException("创建实例失败，instance is null");
    }

    public SqlProvider loadNewInstanceDAO(String codeSource) throws Exception {
        Class<?> aClass = getCodeSourceClass(codeSource);
        if (aClass != null) {
            Object instance = aClass.newInstance();
            if (instance != null) {
                if (instance instanceof SqlProvider) {
                    this.inject((SqlProvider) instance);
                    return (SqlProvider) instance;
                } else {
                    throw new IllegalArgumentException(String.format("创建实例失败，[{}]不是IScript的子类", instance.getClass()));
                }
            }
        }
        throw new IllegalArgumentException("创建实例失败，instance is null");
    }

    /**
     * code text -> class
     * 通过类加载器生成class
     *
     * @param codeSource 源代码
     * @return class
     */
    private Class<?> getCodeSourceClass(String codeSource) {
        String md5 = MD5Utils.hashMd5(codeSource);
        Class<?> aClass = classCache.get(md5);
        if (aClass == null) {
            aClass = groovyClassLoader.parseClass(codeSource);
            classCache.putIfAbsent(md5, aClass);
        }
        return aClass;
    }


    /**
     * 对script对象处理
     *
     * @param script {@link IBaseService}
     */
    public void inject(IBaseService script) {
        // to do something
    }

    public void inject(SqlProvider script) {
        // to do something
    }

}
