package com.dqcer.dxpprovider.sso.web.controller;

import com.dqcer.dxpframework.api.Result;
import com.dqcer.dxpprovider.sso.web.model.dto.LoginDTO;
import com.dqcer.dxpprovider.sso.web.service.AccountService;
import com.dqcer.dxptools.core.IpAddressUtil;
import com.dqcer.framework.storage.CacheConstant;
import com.dqcer.integration.annotation.UnAuthorize;
import com.dqcer.integration.operation.RedissonObject;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author dongqin
 * @description 授权控制器
 * @date 2021/09/10
 */
@RestController
@RequestMapping("auth")
public class AuthorizationController {

    @Resource
    private RedissonObject redissonObject;

    @Resource
    private AccountService userService;

    /**
     * 账号登录
     *
     * @param loginDTO 登录dto E0A28F0F66C369000BA6590857885B27
     * @param request  请求
     * @return {@link Result}
     */
    @UnAuthorize
    @PostMapping("account/login")
    public Result auth(@RequestBody @Validated(LoginDTO.Account.class) LoginDTO loginDTO, HttpServletRequest request) {

        String id = request.getSession().getId();
        String key = MessageFormat.format(CacheConstant.SLIDE_CODE_IP_USERNAME, IpAddressUtil.getHostIp(request), id);

        // 从缓存获取验证码的值 实际项目应该根据用户令牌等获取
//        SlideCodePlace slideCodePlace = (SlideCodePlace) redissonObject.getValue(key);
//        if (ObjUtil.isNull(slideCodePlace)) {
//            return Result.error("901001");
//        }
//
//        boolean valid = slideCodePlace.valid(loginDTO.getNewXPosition());
//        if (!valid) {
//            return Result.error("901002");
//        }

        return userService.auth(loginDTO.getUe(), loginDTO.getPd());
    }

    @UnAuthorize
    @PostMapping("account/login1")
    public Callable<Object> auth2(@RequestBody @Validated(LoginDTO.Account.class) LoginDTO loginDTO, HttpServletRequest request) {
        long nanoTime = System.nanoTime();
        //  开辟一个子线程
        Callable<Object> callable = new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                long time = System.nanoTime();
                Result auth = userService.auth(loginDTO.getUe(), loginDTO.getPd());
                System.out.println(Thread.currentThread() + ": "+ (System.nanoTime() - time));
                return auth;
            }
        };
        System.out.println(Thread.currentThread() + ": "+ (System.nanoTime() - nanoTime));
        return callable;
    }

//    @PostConstruct
//    public void task() {
//        CompletableFuture<Map<String, Object>> future;
//        ScheduledThreadPoolExecutor
//        future.complete()
//    }

    public static void main(String[] args) {
        SpelExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression("#root.purchaseName");
        Map order = new HashMap<>();
        order.put("purchaseName", "张三");
        System.out.println(expression.getValue(order));
    }

}
