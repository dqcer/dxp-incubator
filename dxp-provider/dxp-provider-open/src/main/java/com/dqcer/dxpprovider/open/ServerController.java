package com.dqcer.dxpprovider.open;

import com.dqcer.dxptools.core.IdUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @author dongqin
 * @description 服务端
 * @date 2021/08/13
 */
@RestController
public class ServerController {

    @Resource
    private SignService signService;


    private static Pattern NUMBER_PATTERN = Pattern.compile("[0-9]+");


    public static void main(String[] args) {
        Map<String,Object> param = new HashMap<>(8);
        param.put("cid","1300605621556686849");
        param.put("sysId","3");
        param.put("tableName","pub_user_role_study_env_site");
        param.put("startTime","2020-02-02"); // 公钥
        param.put("endTime","2022-02-02"); // 公钥
        //String linkString = SignUtil.createLinkString(param);
        Map<String, String> map = getHeaders(param, "3840120005", "gM6@zA0!eC0?eG0(");
        System.out.println(map.toString());
    }

    public static Map<String, String> getHeaders(Map<String, Object> map, String appId, String appSecret) {

        String times = String.valueOf(Timestamp.valueOf(LocalDateTime.now()).getTime());
        String generateId = IdUtil.generateId();

        map.put("timestamp", times);
        map.put("nonce", generateId);

        //  除去数组中的空值和签名参数
        String sign = SignUtil.sign(map, appSecret);

        Map<String, String> headers = new HashMap<>(8);
        headers.put("appid", appId);
        //  签名结果加入请求提交参数组中
        headers.put("sign", sign);
        //  时间戳 （作用：防止重放攻击 约定规定时间内，本次请求有效）
        headers.put("timestamp", times);
        // 随机数（作用：防止重放攻击 规定时间内，本次请求有效，若存在重复不反馈结果）
        headers.put("nonce", generateId);

        return headers;
    }

    @PostMapping("/sync")
    public String sync(@RequestBody SyncDto dto, HttpServletRequest request) {
        String appId = request.getHeader(SignService.APPID);
        String sign = request.getHeader(SignService.SIGN);
        String timestamp = request.getHeader(SignService.TIMESTAMP);
        String nonce = request.getHeader(SignService.NONCE);

        if (Objects.isNull(appId) || Objects.isNull(sign)
                || Objects.isNull(timestamp)
                || Objects.isNull(nonce)
                || appId.trim().length() == 0
                || sign.trim().length() == 0
                || timestamp.trim().length() == 0
                || nonce.trim().length() == 0) {
            return  "参数缺失";
        }

        boolean validAppId = signService.validAppId(appId);
        if (!validAppId) {
            return "未授权";
        }

        boolean matches = NUMBER_PATTERN.matcher(timestamp).matches();
        if (!matches) {
            return "参数非法";
        }

        boolean validTimeout = signService.validTimeout(request);
        if (!validTimeout) {
            return "已过期";
        }
        boolean validIdempotence = signService.validIdempotence(dto, request);
        if (!validIdempotence) {
            return "重复请求";
        }
        boolean validSign = signService.validSign(dto, request);
        if (!validSign) {
            return "认证失败";
        }

        return "good job";
    }
}
