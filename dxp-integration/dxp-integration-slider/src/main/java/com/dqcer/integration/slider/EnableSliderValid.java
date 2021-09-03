package com.dqcer.integration.slider;


import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author dongqin
 * @description 启动滑块验证
 * @date 2021/09/03 22:09:76
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({SliderAutoConfiguration.class})
public @interface EnableSliderValid {
}
