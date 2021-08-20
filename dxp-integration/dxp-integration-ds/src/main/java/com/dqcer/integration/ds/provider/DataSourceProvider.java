package com.dqcer.integration.ds.provider;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @author dongqin
 * @description 数据源提供者
 * @date 2021/08/21 01:08:00
 */
@FunctionalInterface
public interface DataSourceProvider {


    /**
     * 加载数据来源
     *
     * @return {@link Map}
     */
    Map<String, DataSource> loadDataSources();
}
