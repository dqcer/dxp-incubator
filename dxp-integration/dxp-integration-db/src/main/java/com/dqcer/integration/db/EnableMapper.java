package com.dqcer.integration.db;


import com.dqcer.integration.db.config.MybatisPlusConfig;
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
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({MybatisPlusConfig.class})
public @interface EnableMapper {
}
