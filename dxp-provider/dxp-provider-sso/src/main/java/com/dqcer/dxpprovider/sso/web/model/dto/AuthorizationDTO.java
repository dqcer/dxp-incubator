package com.dqcer.dxpprovider.sso.web.model.dto;


import com.dqcer.framework.base.DTO;

public class AuthorizationDTO implements DTO {


    public interface PasswordModel {}

    /**
     * 账号
     */
    private String ue;

    /**
     * 密码
     */
    private String slider;
}
