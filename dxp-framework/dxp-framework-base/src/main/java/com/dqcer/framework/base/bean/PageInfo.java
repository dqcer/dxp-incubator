package com.dqcer.framework.base.bean;

import java.io.Serializable;
import java.util.List;


/**
 * @author dqcer
 * @description 分页对象
 * @DATE 22:21 2021/4/28
 */
public class PageInfo<T> implements Serializable {

    private static final long serialVersionUID = 1L;


    private List<T> list;

    /**
     * 总计
     */
    private Long total;

    /**
     * 当前页
     */
    private Integer pageNo;

    /**
     * 每页数量
     */
    private Integer pageSize;

    /**
     * 总页数
     */
    private Integer pageCount;

    public PageInfo(long total, List<T> list, int pageNo, int pageSize) {
        this.total = total;
        this.list = list;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.pageCount = Math.toIntExact((total + pageSize - 1) / pageSize);
    }

    @Override
    public String toString() {
        return "PageInfo{" +
                "list=" + list +
                ", total=" + total +
                ", pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                ", pageCount=" + pageCount +
                '}';
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }
}
