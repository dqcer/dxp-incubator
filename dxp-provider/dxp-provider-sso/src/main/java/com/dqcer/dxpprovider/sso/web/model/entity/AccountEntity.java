package com.dqcer.dxpprovider.sso.web.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dqcer.framework.entity.core.BaseEntity;

/**
 * @author dongqin
 * @description 帐户实体
 * @date 2021/11/13
 */
@TableName("plt_account")
public class AccountEntity extends BaseEntity {

    /**
     * 账户
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    @Override
    public String toString() {
        return "AccountEntity{" +
                "createdTime=" + createdTime +
                ", createdBy=" + createdBy +
                ", updatedTime=" + updatedTime +
                ", updatedBy=" + updatedBy +
                ", status=" + status +
                ", id=" + id +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

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
