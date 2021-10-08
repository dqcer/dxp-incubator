package com.dqcer.integration.encrypt.advice;

import com.dqcer.dxptools.core.Base64Util;
import com.dqcer.dxptools.core.RSAUtil;
import com.dqcer.dxptools.core.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

/**
 * @author dongqin
 * @description 消息解密
 * @date 2021/10/08 21:10:70
 */
public class DecryptHttpInputMessage implements HttpInputMessage {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    private HttpHeaders headers;
    private InputStream body;

    public DecryptHttpInputMessage(HttpInputMessage inputMessage, String privateKey, String charset, boolean showLog) throws Exception {

        if (StrUtil.isBlank(privateKey)) {
            throw new IllegalArgumentException("privateKey为空");
        }

        this.headers = inputMessage.getHeaders();
        String content = new BufferedReader(new InputStreamReader(inputMessage.getBody()))
                .lines().collect(Collectors.joining(System.lineSeparator()));
        String decryptBody;
        if (content.startsWith("{")) {
            log.info("未加密没有解密 :{}", content);
            decryptBody = content;
        } else {
            StringBuilder json = new StringBuilder();
            content = content.replaceAll(" ", "+");
            if (!StrUtil.isBlank(content)) {
                String[] contents = content.split("\\|");
                for (String value : contents) {
                    value = new String(RSAUtil.decrypt(Base64Util.decoderByte(value.getBytes(StandardCharsets.UTF_8)), privateKey), charset);
                    json.append(value);
                }
            }
            decryptBody = json.toString();
            if(showLog) {
                log.info("加密的数据：{}, 解密后：{}", content, decryptBody);
            }
        }
        this.body = new ByteArrayInputStream(decryptBody.getBytes());
    }


    /**
     * Return the body of the message as an input stream.
     *
     * @return the input stream body (never {@code null})
     * @throws IOException in case of I/O errors
     */
    @Override
    public InputStream getBody() throws IOException {
        return body;
    }

    /**
     * Return the headers of this message.
     *
     * @return a corresponding HttpHeaders object (never {@code null})
     */
    @Override
    public HttpHeaders getHeaders() {
        return headers;
    }
}
