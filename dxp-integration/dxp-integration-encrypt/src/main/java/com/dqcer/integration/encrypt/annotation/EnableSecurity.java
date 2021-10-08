package com.dqcer.integration.encrypt.annotation;

import com.dqcer.integration.encrypt.advice.EncryptRequestBodyAdvice;
import com.dqcer.integration.encrypt.advice.EncryptResponseBodyAdvice;
import com.dqcer.integration.encrypt.config.SecretKeyConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author dongqin
 * @description 启用安全
 * @date 2021/10/08 20:10:97
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Import({SecretKeyConfig.class,
        EncryptResponseBodyAdvice.class,
        EncryptRequestBodyAdvice.class})
public @interface EnableSecurity {
}
