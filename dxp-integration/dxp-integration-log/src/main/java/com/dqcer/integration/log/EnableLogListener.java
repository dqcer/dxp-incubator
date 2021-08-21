package com.dqcer.integration.log;


import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author dongqin
 * @description 启用日志监听器
 * @date 2021/08/21 19:08:71
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Import({LogAutoConfiguration.class})
public @interface EnableLogListener {
}
