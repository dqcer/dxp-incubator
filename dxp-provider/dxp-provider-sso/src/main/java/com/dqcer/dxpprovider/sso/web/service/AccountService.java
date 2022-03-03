package com.dqcer.dxpprovider.sso.web.service;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dqcer.dxpprovider.sso.web.dao.UserDAO;
import com.dqcer.dxpprovider.sso.web.model.entity.AccountEntity;
import com.dqcer.tools.core.MD5Util;
import com.dqcer.tools.core.ObjUtil;
import com.dqcer.framework.base.api.Result;
import com.dqcer.framework.storage.*;
import com.dqcer.integration.cache.operation.RedissonObject;
import com.dqcer.provider.sso.api.vo.AccountVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @author dongqin
 * @description 用户服务
 * @date 2021/11/14
 */
@Service
public class AccountService {

    @Resource
    private RedissonObject redissonObject;

    @Resource
    private UserDAO userDAO;

    public static void main(String[] args) {
        System.out.println(MD5Util.getMD5("123456"));
    }

    /**
     * 身份验证
     *
     * @param username 用户名
     * @param password 密码
     * @return {@link Result}
     */
    @Transactional(readOnly = true)
    public Result auth(String username, String password) {
        AccountEntity accountEntity = userDAO.selectOne(Wrappers.lambdaQuery(AccountEntity.class).eq(AccountEntity::getAccount, username));
        if (ObjUtil.isNull(accountEntity)) {
            //return Result.error("901401");
        }

        if (StatusEnum.DISABLE.getStatus() == accountEntity.getStatus()) {
            //return Result.error("901402");
        }

        String md5Password = SecureUtil.sha256(password).toUpperCase();
        String dbPassword = accountEntity.getPassword();
        if (!md5Password.equals(dbPassword)) {
            //return Result.error("901403");
        }

        String token = UUID.randomUUID().toString();

        CacheUser cacheUser = new CacheUser().setAccountId(accountEntity.getId())
                .setLastActiveTime(LocalDateTime.now())
                .setOnlineStatus(CacheUser.ONLINE);
        redissonObject.setValue(MessageFormat.format(CacheConstant.SSO_TOKEN, token), cacheUser, CacheConstant.SSO_TOKEN_NAMESPACE_TIMEOUT);

        return Result.ok(token);
    }

    public Object getOne() {
        List<AccountEntity> sysUserEntities = userDAO.selectList(null);
        return sysUserEntities;
    }

    public List<AccountVO> baseDetail() {
        UnifySession session = UserStorage.getSession();
        Long accountId = session.getAccountId();


        return null;
    }
}
