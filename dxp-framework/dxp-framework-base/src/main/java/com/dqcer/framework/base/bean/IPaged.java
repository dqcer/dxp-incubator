package com.dqcer.framework.base.bean;

/**
 * 分页接口
 *
 * @author dongqin
 * @date 2022/07/26
 */
@SuppressWarnings("unused")
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
