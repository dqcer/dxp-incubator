package com.dqcer.dxptools.dynamic.service;
/**
 * @author dongqin
 * @description ibase服务
 * @date 2021/07/28
 */

public abstract class IBaseService {


    /**
     * 创建业务表
     */
    abstract void createBizTable();

    /**
     * 目标列表
     *
     * @return {@link Object}
     */
    abstract Object targetList();

    /**
     * 批量导入
     *
     * @param targetList 目标列表
     */
    abstract void batchImport(Object targetList);


    /**
     * 开始进行统计
     */
    public void statistical() {
        createBizTable();
        Object targetList = targetList();
        batchImport(targetList);
    }


}
