package com.dqcer.dxpprovider.sso.web.dto;

import com.dqcer.dxpframework.dto.BaseDTO;
import com.dqcer.dxpframework.dto.annontation.StrValid;

/**
 * @author dongqin
 * @description 登录dto
 * @date 2021/07/16 23:07:48
 */
public class LoginDTO extends BaseDTO {

    private static final long serialVersionUID = -7311088026242433898L;

    /**
     * 账号
     */
    @StrValid(min = 3, max = 32, message = "{loginDTO.ue}")
    private String ue;

    /**
     * 密码
     */
    @StrValid(min = 3, max = 32, message = "{loginDTO.pd}")
    private String pd;


    @Override
    public String toString() {
        return super.toString();
    }

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
