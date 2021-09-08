package com.dqcer.dxpframework.dto;

import com.dqcer.dxpframework.dto.annontation.StrValid;

/**
 * @author dongqin
 * @description 关键字dto
 * @date 2021/09/08
 */
public class KeywordDTO extends BaseDTO {

    @StrValid(max = 256)
    private String keyword;

    @Override
    public String toString() {
        return "KeywordDTO{" +
                "keyword='" + keyword + '\'' +
                "} " + super.toString();
    }

    /**
     * 获取关键字：处理关键字%转义问题， 解决以下问题
     *        name  like concat('%','%','%') -- 查询的是全部数据，期望是查询带有‘%’关键字的数据
     *
     * @return {@link String}
     */
    public String getKeyword() {
        if (null != keyword && keyword.trim().length() > 0) {
            keyword = keyword.replaceAll("%", "\\\\%");
        }
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
