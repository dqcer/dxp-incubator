package com.dqcer.dxptools.sync.groovy.demo;

/**
 * @author dongqin
 * @description ibase服务
 * @date 2021/07/28
 */
public interface IBaseService {


    /**
     * 视图
     *
     * @return {@link Object}
     */
    Object view();

    /**
     * 导出excel
     */
    void exportExcel();
}
