package com.dqcer.framework.dto;

import com.dqcer.framework.base.DTO;
import com.dqcer.framework.base.bean.IPaged;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


/**
 * 分页dto
 *
 * @author dongqin
 * @date 2022/07/26
 */
@SuppressWarnings("unused")
public abstract class PagedDTO extends DTO implements IPaged {

    /**
     * 关键字 max 128
     */
    @Length(groups = ValidGroup.Paged.class, max = 128)
    private String keyword;

    /**
     * 当前页 min 1
     */
    @NotNull(groups = ValidGroup.Paged.class)
    @Min(groups = ValidGroup.Paged.class, value = 1)
    private Integer pageNum;

    /**
     * 每页数量 max 1000
     */
    @NotNull(groups = ValidGroup.Paged.class)
    @Max(groups = ValidGroup.Paged.class, value = 1000)
    private Integer limit;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
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
}
