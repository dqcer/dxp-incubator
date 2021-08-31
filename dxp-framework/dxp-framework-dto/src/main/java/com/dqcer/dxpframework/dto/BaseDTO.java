package com.dqcer.dxpframework.dto;


import com.dqcer.dxpframework.dto.support.Validation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
