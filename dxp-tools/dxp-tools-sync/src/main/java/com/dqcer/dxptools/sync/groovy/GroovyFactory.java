package com.dqcer.dxptools.sync.groovy;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyCodeSource;

import java.net.URL;

public class GroovyFactory {

    private static GroovyFactory groovyFactory = new GroovyFactory();
    private GroovyClassLoader groovyClassLoader = new GroovyClassLoader();

    public static GroovyFactory getInstance() {
        return groovyFactory;
    }

    /**
     * 根据groovy文件路径生成IRule的实现
     * @param packagePath
     * @return
     * @throws Exception
     */
    public IRule getIRuleFromPackage(String filePath) throws Exception {
        URL url = this.getClass().getClassLoader().getResource(filePath);
        Class<?> clazz = groovyClassLoader.parseClass(new GroovyCodeSource(url));
        if (clazz != null) {
            Object instance = clazz.newInstance();
            if (instance != null) {
                if (instance instanceof IRule) {
                    return (IRule) instance;
                }
            }
        }
        throw new IllegalArgumentException("读取groovy文件异常");
    }

    /**
     * 根据脚本内容生成IRule的实现
     * @param code
     * @return
     * @throws Exception
     */
    public IRule getIRuleFromCode(String code) throws Exception {
        Class<?> clazz = groovyClassLoader.parseClass(code);
        if (clazz != null) {
            Object instance = clazz.newInstance();
            if (instance != null) {
                if (instance instanceof IRule) {
                    return (IRule) instance;
                }
            }
        }
        throw new IllegalArgumentException("读取groovy脚本异常");
    }

}
