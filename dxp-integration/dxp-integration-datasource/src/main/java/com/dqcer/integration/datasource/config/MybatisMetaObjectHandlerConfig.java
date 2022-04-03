package com.dqcer.integration.datasource.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/**
 * @author dongqin
 * @description mybatis 数据源处理 配置
 * @date 2021/08/21 20:08:55
 */
public class MybatisMetaObjectHandlerConfig implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createdTime", () -> new Date(), Date.class);
        this.strictInsertFill(metaObject, "delFlag", () -> 1, Integer.class);
        this.strictInsertFill(metaObject, "status", () -> 1, Integer.class);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updatedTime", () -> new Date(), Date.class);
    }
}
