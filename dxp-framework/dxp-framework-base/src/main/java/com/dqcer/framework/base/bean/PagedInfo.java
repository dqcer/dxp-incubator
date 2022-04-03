package com.dqcer.framework.base.bean;

import com.dqcer.framework.base.VO;

import java.util.List;


/**
 * @author dqcer
 * @description 分页对象
 * @date 22:21 2021/4/28
 */
@SuppressWarnings("unused")
public class PagedInfo<T> extends VO implements IPaged {

    private static final long serialVersionUID = 1L;


    private List<T> list;

    /**
     * 总计
     */
    private Long total;

    /**
     * 当前页
     */
    private Integer pageNum;

    /**
     * 每页数量
     */
    private Integer limit;

    /**
     * 总页数
     */
    private Integer pageCount;

    public PagedInfo(long total, List<T> list, int pageNo, int pageSize) {
        this.total = total;
        this.list = list;
        this.pageNum = pageNo;
        this.limit = pageSize;
        this.pageCount = Math.toIntExact((total + pageSize - 1) / pageSize);
    }

    @Override
    public String toString() {
        return "PageInfo{" +
                "list=" + list +
                ", total=" + total +
                ", pageNo=" + pageNum +
                ", pageSize=" + limit +
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

    @Override
    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    @Override
    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

}
