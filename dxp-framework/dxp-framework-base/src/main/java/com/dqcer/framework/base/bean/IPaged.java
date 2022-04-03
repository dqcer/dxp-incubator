package com.dqcer.framework.base.bean;

/**
 * @author dqcer
 * @description ipaged
 * @date  22:21 2021/4/28
 */
public interface IPaged {

    /**
     * 当前页
     *
     * @return {@link Integer}
     */
    Integer getPageNum();


    /**
     * 每页数量
     *
     * @return {@link Integer}
     */
    Integer getLimit();

    // TODO: 2022/4/3 排序处理

}
