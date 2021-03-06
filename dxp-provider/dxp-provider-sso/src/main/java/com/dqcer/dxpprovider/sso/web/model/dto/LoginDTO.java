package com.dqcer.dxpprovider.sso.web.model.dto;



import com.dqcer.framework.base.DTO;

import javax.validation.constraints.NotNull;

/**
 * @author dongqin
 * @description 登录dto
 * @date 2021/09/10
 */
public class LoginDTO extends DTO {

    private static final long serialVersionUID = -7311088026242433898L;

    public interface Account {}

    /**
     * 账号
     */
    private String ue;

    /**
     * 密码
     */
    private String pd;

    /**
     * 偏差值，由前端计算后得出，用于滑块验证
     */
    @NotNull(groups = Account.class, message = "{loginDTO.deviation}")
    private Integer newXPosition;

    @Override
    public String toString() {
        return "LoginDTO{" +
                "ue='" + ue + '\'' +
                ", pd='" + pd + '\'' +
                ", newXPosition=" + newXPosition +
                '}';
    }

    public Integer getNewXPosition() {
        return newXPosition;
    }

    public void setNewXPosition(Integer newXPosition) {
        this.newXPosition = newXPosition;
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
