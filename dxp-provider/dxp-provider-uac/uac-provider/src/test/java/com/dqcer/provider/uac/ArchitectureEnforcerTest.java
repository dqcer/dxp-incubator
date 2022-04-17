package com.dqcer.provider.uac;

import com.dqcer.framework.enforcer.ArchitectureEnforcer;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.core.importer.ImportOptions;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.Before;
import org.junit.Test;

/**
 * @author dqcer
 * @description 测试架构执行者
 * @DATE 22:21 2021/4/28
 */
public class ArchitectureEnforcerTest {


    private JavaClasses classes;

    @Before
    public void setUp() {
        ImportOptions importOptions = new ImportOptions();
        importOptions.with(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS);
        //  当前模块com.dqcer
        classes = new ClassFileImporter().importPackages("com.dqcer");
    }

    @Test
    public void requiredRules() {
        for (ArchRule rule : ArchitectureEnforcer.requiredRules) {
            rule.check(classes);
        }
    }

}
