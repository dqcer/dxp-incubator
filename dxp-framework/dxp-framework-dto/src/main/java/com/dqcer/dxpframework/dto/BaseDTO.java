package com.dqcer.dxpframework.dto;


import com.dqcer.dxpframework.dto.support.Validation;

import java.io.Serializable;

/**
 * @author dqcer
 * @description base DTO
 * @date 23:37 2021/5/5
 */
public class BaseDTO implements Validation, Serializable {
    private static final long serialVersionUID = -537843738855940508L;

    @Override
    public String toString() {
        return "BaseDTO{}";
    }
}
