package com.dqcer.dxpprovider.sso.web.dto;

import com.dqcer.dxpframework.dto.BaseDTO;
import com.dqcer.dxpframework.dto.annontation.StrValid;

public class AuthorizationDTO extends BaseDTO {

    private static final long serialVersionUID = 2131049311091076305L;

    public interface PasswordModel {}

    /**
     * 账号
     */
    @StrValid(min = 3, max = 32, message = "{loginDTO.ue}", groups = PasswordModel.class)
    private String ue;

    /**
     * 密码
     */
    @StrValid(min = 32, max = 32, message = "{loginDTO.pd}", groups = PasswordModel.class)
    private String slider;
}
