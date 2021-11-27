package com.dqcer.provider.sso.api.vo;

import java.io.Serializable;
import java.util.StringJoiner;

/**
 * @author dongqin
 * @description 账户VO
 * @date 2021/11/22
 */
public class AccountVO implements Serializable {

    private static final long serialVersionUID = -6956152509701548630L;


    /**
     * 租户id
     */
    private Long tenantId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 名
     */
    private String firstName;

    /**
     * 姓
     */
    private String lastName;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * 电话
     */
    private String phone;

    @Override
    public String toString() {
        return new StringJoiner(", ", AccountVO.class.getSimpleName() + "[", "]")
                .add("tenantId=" + tenantId)
                .add("userId=" + userId)
                .add("firstName='" + firstName + "'")
                .add("lastName='" + lastName + "'")
                .add("gender=" + gender)
                .add("email='" + email + "'")
                .add("phone='" + phone + "'")
                .toString();
    }

    public Long getTenantId() {
        return tenantId;
    }

    public AccountVO setTenantId(Long tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public AccountVO setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public AccountVO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public AccountVO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Integer getGender() {
        return gender;
    }

    public AccountVO setGender(Integer gender) {
        this.gender = gender;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public AccountVO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public AccountVO setPhone(String phone) {
        this.phone = phone;
        return this;
    }
}
