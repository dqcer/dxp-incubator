package com.dqcer.dxpprovider.sso.web.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dqcer.integration.db.entity.AttribEntity;

/**
 * @author dongqin
 * @description 帐户实体
 * @date 2021/11/13
 */
@TableName("plt_account")
public class AccountEntity extends AttribEntity<Long> {

    /**
     * 账户
     */
    private String account;

    /**
     * 密码
     */
    private String password;


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
