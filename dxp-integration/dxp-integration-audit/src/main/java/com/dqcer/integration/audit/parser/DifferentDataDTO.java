package com.dqcer.integration.audit.parser;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * @author dongqin
 * @description 差异化dto
 * @date 2021/12/28
 */
public class DifferentDataDTO {

    /**
     * 字段名
     */
    private String fieldName;
    /**
     * 新字段值
     */
    private Object newFieldValue;
    /**
     * 旧字段值
     */
    private Object oldFieldValue;

    @Override
    public boolean equals(Object o) {
        if (this == o){ return true;}

        if (!(o instanceof DifferentDataDTO)){ return false;}

        DifferentDataDTO dto = (DifferentDataDTO) o;
        return Objects.equals(getFieldName(), dto.getFieldName()) &&
                Objects.equals(getNewFieldValue(), dto.getNewFieldValue()) &&
                Objects.equals(getOldFieldValue(), dto.getOldFieldValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFieldName(), getNewFieldValue(), getOldFieldValue());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", DifferentDataDTO.class.getSimpleName() + "[", "]")
                .add("fieldName='" + fieldName + "'")
                .add("newFieldValue=" + newFieldValue)
                .add("oldFieldValue=" + oldFieldValue)
                .toString();
    }

    public String getFieldName() {
        return fieldName;
    }

    public DifferentDataDTO setFieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

    public Object getNewFieldValue() {
        return newFieldValue;
    }

    public DifferentDataDTO setNewFieldValue(Object newFieldValue) {
        this.newFieldValue = newFieldValue;
        return this;
    }

    public Object getOldFieldValue() {
        return oldFieldValue;
    }

    public DifferentDataDTO setOldFieldValue(Object oldFieldValue) {
        this.oldFieldValue = oldFieldValue;
        return this;
    }
}
