package com.dqcer.integration.encrypt.advice;

import com.alibaba.fastjson.JSON;
import com.dqcer.dxptools.core.Base64Util;
import com.dqcer.dxptools.core.RSAUtil;
import com.dqcer.integration.encrypt.annotation.Encrypt;
import com.dqcer.integration.encrypt.config.SecretKeyConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.Resource;

/**
 * @author dongqin
 * @description 响应加密
 * @date 2021/10/08 21:10:55
 */
@ControllerAdvice
public class EncryptResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private boolean encrypt;

    @Resource
    private SecretKeyConfig secretKeyConfig;

    private static ThreadLocal<Boolean> ENCRYPT_LOCAL = new ThreadLocal<>();

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        encrypt = false;
        if (returnType.getMethod().isAnnotationPresent(Encrypt.class) && secretKeyConfig.isOpen()) {
            encrypt = true;
        }
        return encrypt;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // EncryptResponseBodyAdvice.setEncryptStatus(false);
        // 动态设置不加密的
        Boolean status = ENCRYPT_LOCAL.get();
        if (null != status && !status) {
            ENCRYPT_LOCAL.remove();
            return body;
        }
        if (encrypt) {
            String publicKey = secretKeyConfig.getPublicKey();
            try {
                String content = JSON.toJSONString(body);
                byte[] data = content.getBytes();
                byte[] encodedData = RSAUtil.encrypt(data, publicKey);
                String result = Base64Util.encoderByte(encodedData);
                if(secretKeyConfig.isShowLog()) {
                    log.info("加密前：{}，加密后：{}", content, result);
                }
                return result;
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return body;
    }
}