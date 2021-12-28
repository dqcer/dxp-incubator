package com.dqcer.integration.audit;

import com.dqcer.integration.audit.bean.AuditLogBean;
import com.dqcer.integration.audit.bean.AuditLogDetailBean;

import java.util.Collection;

/**
 * @author dongqin
 * @description 稽查日志
 * @date 2021/12/28
 */
public interface AuditProvider<T extends AuditLogBean, Sub extends AuditLogDetailBean> {

    /**
     * 保存
     *
     * @param entity 实体
     */
    void save(T entity);

    /**
     * 批量保存明细
     *
     * @param entities 实体集
     */
    void batchSaveSub(Collection<Sub> entities);
}
