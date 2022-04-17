package com.dqcer.provider.uac;

import com.dqcer.framework.base.bean.IResultCode;
import com.dqcer.framework.base.bean.Result;
import com.dqcer.framework.base.bean.ResultCode;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.elements.GivenClasses;
import org.junit.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

/**
 * @author dqcer
 * @description 测试架构执行者
 * @DATE 22:21 2021/4/28
 */
public class ArchitectureEnforcerTest {

    public static GivenClasses getClasses() {
        return classes();
    }

    /**
     * 验证 自定义结果错误码的类命名
     */
    @Test
    public void validCodeName() {
        JavaClasses javaClasses = new ClassFileImporter().importPackages("com.dqcer");
        ArchRule as = getClasses().that().implement(IResultCode.class).should().haveSimpleNameEndingWith("Code").as("自定义结果错误码必须以Code结尾");
        as.check(javaClasses);
    }

    @Test
    public void validCode() {
        JavaClasses javaClasses = new ClassFileImporter().importPackages("com.dqcer");
        ArchRule as = getClasses()
                // 在service包下的所有类
                .that().resideInAPackage("..service..")
                // 不能调用controller包下的任意类
                .should().accessClassesThat().resideInAPackage("..controller..")
                // 断言描述 - 不满足规则的时候打印出来的原因
                .because("不能在service包中调用controller中的类");

        as.check(javaClasses);
    }

    @Test
    public void demo() {
        JavaClasses javaClasses = new ClassFileImporter().importPackages("com.dqcer.framework.base.constants");
        ArchRule as = getClasses().that().areInterfaces().should().haveSimpleNameStartingWith("I").as("接口名称必须以I开头");
        as.check(javaClasses);
    }

}
