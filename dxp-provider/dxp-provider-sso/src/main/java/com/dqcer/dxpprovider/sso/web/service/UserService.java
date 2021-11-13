package com.dqcer.dxpprovider.sso.web.service;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dqcer.dxpframework.api.ResultApi;
import com.dqcer.framework.storage.CacheConstant;
import com.dqcer.dxpprovider.sso.web.dao.UserDAO;
import com.dqcer.dxpprovider.sso.web.model.entity.AccountEntity;
import com.dqcer.dxptools.core.MD5Util;
import com.dqcer.dxptools.core.ObjUtil;
import com.dqcer.framework.storage.CacheUser;
import com.dqcer.framework.storage.StatusEnum;
import com.dqcer.integration.datasource.annotation.DynamicDataSource;
import com.dqcer.integration.operation.RedissonObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author dongqin
 * @description 用户服务
 * @date 2021/11/14
 */
@Service
public class UserService  {

    @Resource
    private RedissonObject redissonObject;

    @Resource
    private UserDAO userDAO;

    @Transactional(readOnly = true)
    @DynamicDataSource
    public ResultApi auth(String username, String password) {
        AccountEntity accountEntity = userDAO.selectOne(Wrappers.lambdaQuery(AccountEntity.class).eq(AccountEntity::getAccount, username));
        if (ObjUtil.isNull(accountEntity)) {
            return ResultApi.error("901401");
        }

        if (StatusEnum.DISABLE.getStatus() == accountEntity.getStatus()) {
            return ResultApi.error("901402");
        }

        String md5Password = SecureUtil.sha256(password).toUpperCase();
        String dbPassword = accountEntity.getPassword();
        if (!md5Password.equals(dbPassword)) {
            return ResultApi.error("901403");
        }

        String token = MD5Util.getMD5(MD5Util.getMD5(accountEntity.getAccount().concat(dbPassword)));

        CacheUser cacheUser = new CacheUser().setAccountId(accountEntity.getId())
                .setLastActiveTime(LocalDateTime.now())
                .setOnlineStatus(CacheUser.ONLINE);
        redissonObject.setValue(MessageFormat.format(CacheConstant.SSO_TOKEN, token), cacheUser, CacheConstant.SSO_TOKEN_NAMESPACE_TIMEOUT);

        return ResultApi.ok(token);
    }

    public Object getOne() {
        List<AccountEntity> sysUserEntities = userDAO.selectList(null);
        return sysUserEntities;
    }

    public static void main(String[] args) {
        System.out.println(MD5Util.getMD5("P@ssw0rdP@ssw0rd"));
    }
}
