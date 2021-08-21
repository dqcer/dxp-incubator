package com.dqcer.integration.ds;


import com.dqcer.integration.ds.configuration.AutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author dongqin
 * @description 启用动态数据源
 * @date 2021/08/21 19:08:69
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Import({AutoConfiguration.class})
public @interface EnableDynamicDataSource {
}
