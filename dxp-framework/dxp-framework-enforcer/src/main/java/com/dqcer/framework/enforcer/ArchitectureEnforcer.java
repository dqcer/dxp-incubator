package com.dqcer.framework.enforcer;

import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.GeneralCodingRules;

import java.util.LinkedList;
import java.util.List;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

/**
 * @author dqcer
 * @description 架构守护神规则库,提供了静态检查机制(配合单元测试使用)，杜绝线上出现不合规范的使用
 * @DATE 22:21 2021/4/28
 */
public final class ArchitectureEnforcer {

    public static final List<ArchRule> requiredRules = new LinkedList();

    public ArchitectureEnforcer() {
    }

    {
        requiredRules.add(dtoNamingRules());
        requiredRules.add(controllerNamingRules());
        requiredRules.add(serviceNamingRules());
        requiredRules.add(daoNamingRules());
        requiredRules.add(entityNamingRules());
        requiredRules.add(voNamingRules());
        requiredRules.add(interfacesNamingRules());

        // 禁止使用e.printStackTrace, System.err/System.out
        requiredRules.add(GeneralCodingRules.NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS);

        // 不能直接抛出 Throwable、Exception、RuntimeException异常
        requiredRules.add(GeneralCodingRules.NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS);
    }

    /**
     * service 层命名规则
     *
     * @return {@link ArchRule}
     */
    public static ArchRule serviceNamingRules() {
        return classes()
                .that()
                .resideInAPackage("..service")
                .should()
                .haveSimpleNameEndingWith("Service")
                .allowEmptyShould(true)
                .as("service 层下的类应该以'Service'结尾");
    }

    /**
     * controller 层命名规则
     *
     * @return {@link ArchRule}
     */
    public static ArchRule controllerNamingRules() {
        return classes()
                .that()
                .resideInAPackage("..controller")
                .should()
                .haveSimpleNameEndingWith("Controller")
                .allowEmptyShould(true)
                .as("controller 层下的类应该以'Controller'结尾");
    }

    /**
     * dao 层命名规则
     *
     * @return {@link ArchRule}
     */
    public static ArchRule daoNamingRules() {
        return classes()
                .that()
                .resideInAPackage("..dao")
                .should()
                .haveSimpleNameEndingWith("Dao")
                .allowEmptyShould(true)
                .as("dao 层下的类应该以'Dao'结尾");
    }

    /**
     * entity 层命名规则
     *
     * @return {@link ArchRule}
     */
    public static ArchRule entityNamingRules() {
        return classes()
                .that()
                .resideInAPackage("..entity")
                .should()
                .haveSimpleNameEndingWith("Entity")
                .allowEmptyShould(true)
                .as("entity 层下的类应该以'Entity'结尾");
    }

    /**
     * dto 层命名规则
     *
     * @return {@link ArchRule}
     */
    public static ArchRule dtoNamingRules() {
        return classes()
                .that()
                .resideInAPackage("..dto")
                .should()
                .haveSimpleNameEndingWith("DTO")
                .allowEmptyShould(true)
                .as("dto 层下的类应该以'DTO'结尾");
    }

    /**
     * vo 层命名规则
     *
     * @return {@link ArchRule}
     */
    public static ArchRule voNamingRules() {
        return classes()
                .that()
                .resideInAPackage("..vo")
                .should()
                .haveSimpleNameEndingWith("VO")
                .allowEmptyShould(true)
                .as("vo 层下的类应该以'VO'结尾");
    }


    /**
     * 接口命名规则
     *
     * @return {@link ArchRule}
     */
    public static ArchRule interfacesNamingRules() {
        return classes().that().areInterfaces().should().haveSimpleNameStartingWith("I").as("接口名称必须以I开头");
    }



}
