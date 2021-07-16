package com.dqcer.dxpprovider.sso.web.dto;

import com.dqcer.dxpframework.dto.annontation.StrValid;
import com.dqcer.dxpframework.dto.support.Validation;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * @author dongqin
 * @description 登录dto
 * @date 2021/07/16 23:07:48
 */
public class LoginDTO implements Validation {

    private static final long serialVersionUID = -7311088026242433898L;

    /**
     * 账号
     */
    @StrValid(min = 3, max = 32)
    private String ue;

    /**
     * 密码
     */
    @StrValid(min = 3, max = 32)
    private String pd;

    public String getUe() {
        return ue;
    }

    public void setUe(String ue) {
        this.ue = ue;
    }

    public String getPd() {
        return pd;
    }

    public void setPd(String pd) {
        this.pd = pd;
    }
}
