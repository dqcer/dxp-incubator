package com.dqcer.dxpprovider.open;

import com.dqcer.tools.core.BeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dongqin
 * @description 签名服务类
 * @date 2021/08/13
 */
@Component
public class SignService {

    private static Logger logger = LoggerFactory.getLogger(SignService.class);

    public static final String APPID = "appid";
    public static final String SIGN = "sign";
    public static final String TIMESTAMP = "timestamp";
    public static final String NONCE = "nonce";

    /** 超时时间 */
    private final static int TIME_OUT = 10*60*1000;

    private static Map<String, String> cache = new ConcurrentHashMap<>();

    /**
     * 是否超时
     *
     * @param request 请求
     * @return boolean
     */
    public boolean validTimeout(HttpServletRequest request) {
        String timestamp = request.getHeader(TIMESTAMP);
        long nowTimestamp = Timestamp.valueOf(LocalDateTime.now()).getTime();
        if (Long.parseLong(timestamp) + TIME_OUT > nowTimestamp) {
            return true;
        }
        logger.error("签发超时 nowTimestamp: {} requestTimestamp: {}", nowTimestamp, timestamp);
        return false;
    }

    /**
     * 是否幂等性
     *
     * @param dto     dto
     * @param request 请求
     * @return boolean
     */
    public boolean validIdempotence(SyncDto dto, HttpServletRequest request) {
        String sign = request.getHeader(SIGN);
        String timestamp = request.getHeader(TIMESTAMP);
        String nonce = request.getHeader(NONCE);
        Map<String, Object> map = BeanUtil.beanToMap(dto);
        map.put(SIGN, sign);
        map.put(TIMESTAMP, timestamp);
        map.put(NONCE, nonce);

        //   方便演示
        String o = cache.get(MessageFormat.format("open_api:sign:nonce:{0}", nonce));
        if (o != null) {
            logger.error("随机数重复 nonce: {}", nonce);
            return false;
        }

        // 需要redis有效期的value
//        RedisTemplate template = redisService.getRedisTemplate();
//        Object o = template.opsForValue().get(MessageFormat.format("open_api:sign:nonce:{0}", nonce));
//        if (o != null) {
//            logger.error("随机数重复 nonce: {}", nonce);
//            return false;
//        }
//        template.opsForValue().set(MessageFormat.format("open_api:sign:nonce:{0}", nonce), new Date(), 60, TimeUnit.SECONDS);

        cache.put(MessageFormat.format("open_api:sign:nonce:{0}", nonce), nonce);

        return true;
    }

    /**
     * 有效的签名
     *
     * @param dto     dto
     * @param request 请求
     * @return boolean
     */
    public boolean validSign(SyncDto dto, HttpServletRequest request) {
        String appId = request.getHeader(APPID);
        String sign = request.getHeader(SIGN);
        String timestamp = request.getHeader(TIMESTAMP);
        String nonce = request.getHeader(NONCE);
        Map<String, Object> map = BeanUtil.beanToMap(dto);
        map.put(TIMESTAMP, timestamp);
        map.put(NONCE, nonce);

        Map<String, String> authorization = authorization();
        String appSecret = authorization.get(appId);

        String nowSign = SignUtil.sign(map, appSecret);

        return nowSign.equals(sign);
    }

    /**
     * 验证appId
     *
     * @param appId 应用程序id
     * @return boolean
     */
    public boolean validAppId(String appId) {
        Map<String, String> authorization = authorization();
        return authorization.containsKey(appId);
    }

    /**
     * 授权的数据 key:appId value:appSecret
     *
     * @return {@link Map}
     */
    public Map<String, String> authorization() {
        Map<String, String> map = new HashMap<>(16);
        map.put("3840120005", "gM6@zA0!eC0?eG0(");
        return map;
    }

}
