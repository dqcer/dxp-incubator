package com.dqcer.dxpprovider.sso.web.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dqcer.dxpframework.api.ResultApi;
import com.dqcer.dxpprovider.sso.web.dao.UserDAO;
import com.dqcer.dxpprovider.sso.web.model.entity.SysUserEntity;
import com.dqcer.dxptools.core.MD5Util;
import com.dqcer.dxptools.core.ObjUtil;
import com.dqcer.framework.storage.GlobalConstant;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService  {

    @Resource
    private UserDAO userDAO;

    public ResultApi auth(String username, String password) {
        SysUserEntity sysUserEntity = userDAO.selectOne(Wrappers.lambdaQuery(SysUserEntity.class).eq(SysUserEntity::getLoginName, username));
        if (ObjUtil.isNull(sysUserEntity)) {
            return ResultApi.warn("999401");
        }

        if (GlobalConstant.Status.ENABLE.ordinal() != sysUserEntity.getStatus()) {
            return ResultApi.warn("999402");
        }

        String salt = sysUserEntity.getSalt();
        String oldPassword = MD5Util.getMD5(password + salt);
        if (!oldPassword.equals(oldPassword)) {
            return ResultApi.warn("999402");
        }


        return ResultApi.ok("good job");
    }

    public Object getOne() {
        List<SysUserEntity> sysUserEntities = userDAO.selectList(null);
        return sysUserEntities;
    }
}
