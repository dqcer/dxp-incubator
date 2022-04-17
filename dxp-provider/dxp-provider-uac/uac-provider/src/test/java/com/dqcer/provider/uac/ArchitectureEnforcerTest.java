package com.dqcer.provider.uac;

import com.dqcer.framework.base.bean.IResultCode;
import com.dqcer.framework.base.bean.Result;
import com.dqcer.framework.base.bean.ResultCode;
import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.base.PackageMatchers;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaMember;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTag;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.elements.ClassesShouldConjunction;
import com.tngtech.archunit.lang.syntax.elements.GivenClasses;
import org.junit.Test;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import static com.tngtech.archunit.core.domain.JavaClass.Functions.GET_PACKAGE_NAME;
import static com.tngtech.archunit.core.domain.JavaMember.Predicates.declaredIn;
import static com.tngtech.archunit.core.domain.properties.CanBeAnnotated.Predicates.annotatedWith;
import static com.tngtech.archunit.lang.conditions.ArchPredicates.are;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

/**
 * @author dqcer
 * @description 测试架构执行者
 * @DATE 22:21 2021/4/28
 */
public class ArchitectureEnforcerTest {


    private final JavaClasses importedClasses = new ClassFileImporter().importPackages("com.dqcer");

    public static GivenClasses getClasses() {
        return classes();
    }
    @Test
    public void file_name_should_end_with_package_name() {
//        JavaClasses importedClasses = new ClassFileImporter().importPackages(this.getClass().getPackage().getName());

//        classes().that().resideInAPackage("..controller")
//                .should().haveSimpleNameEndingWith("Controller")
//                .check(importedClasses);
        ArchRule as = classes().that().resideInAPackage("..service")
                .should().haveSimpleNameEndingWith("Service").allowEmptyShould(true).as("service 层下的类应该以'Service'结尾");

        as.check(importedClasses);


//        classes().that().resideInAPackage("..dao")
//                .should().haveSimpleNameEndingWith("Dao")
//                .check(importedClasses);
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
