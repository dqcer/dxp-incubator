package com.dqcer.dxpframework.dto.support;

import java.io.Serializable;

/**
 * @author dongqin
 * @description 参数效验
 * @date 0:30 2021/5/25
 */
public interface Validation extends Serializable {

    /**
     * group: save
     */
    public interface Save {
    }

    /**
     * group: update
     */
    public interface Update {
    }

    /**
     * group: delete
     */
    public interface Delete {
    }

    /**
     * group: query
     */
    public interface Query {
    }

}
