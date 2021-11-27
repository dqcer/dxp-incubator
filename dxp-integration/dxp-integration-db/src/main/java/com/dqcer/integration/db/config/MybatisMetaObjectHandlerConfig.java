package com.dqcer.integration.db.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

/**
 * @author dongqin
 * @description mybatis 数据源处理 配置
 * @date 2021/08/21 20:08:55
 */
public class MybatisMetaObjectHandlerConfig implements MetaObjectHandler {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void insertFill(MetaObject metaObject) {
        if (log.isDebugEnabled()) {
            log.debug("start insert fill ....");
        }
        // TODO: 2021/8/23 createdBy 当前操作人 
        this.strictInsertFill(metaObject, "createTime", () -> LocalDateTime.now(), LocalDateTime.class);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
    }
}
