package com.dqcer.dxpprovider.sso.web.dto;

import com.dqcer.dxpframework.dto.BaseDTO;
import com.dqcer.dxpframework.dto.annontation.StrValid;

import javax.validation.constraints.NotNull;

/**
 * @author dongqin
 * @description 登录dto
 * @date 2021/09/10
 */
public class LoginDTO extends BaseDTO {

    private static final long serialVersionUID = -7311088026242433898L;

    public interface Account {}

    /**
     * 账号
     */
    @StrValid(min = 3, max = 32, groups = Account.class,  message = "{loginDTO.ue}")
    private String ue;

    /**
     * 密码
     */
    @StrValid(min = 3, max = 32, groups = Account.class, message = "{loginDTO.pd}")
    private String pd;

    /**
     * 偏差值，由前端计算后得出，用于滑块验证
     */
    @NotNull(groups = Account.class, message = "{loginDTO.deviation}")
    private Integer deviation;

    public Integer getDeviation() {
        return deviation;
    }

    public void setDeviation(Integer deviation) {
        this.deviation = deviation;
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
