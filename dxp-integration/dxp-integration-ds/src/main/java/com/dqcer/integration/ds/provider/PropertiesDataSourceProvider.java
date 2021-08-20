package com.dqcer.integration.ds.provider;

import com.dqcer.integration.ds.creator.DefaultDataSourceCreator;
import com.dqcer.integration.ds.properties.DynamicDataSourceProperties;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dongqin
 * @description 文件 数据来源提供者
 * @date 2021/08/21 01:08:51
 */
public class PropertiesDataSourceProvider implements DataSourceProvider{

    @Resource
    private DefaultDataSourceCreator defaultDataSourceCreator;

    private final Map<String, DynamicDataSourceProperties> dataSourcePropertiesMap;

    public PropertiesDataSourceProvider(Map<String, DynamicDataSourceProperties> dataSourcePropertiesMap) {
        this.dataSourcePropertiesMap = dataSourcePropertiesMap;

    }

    /**
     * 加载所有数据源
     *
     * @return 所有数据源，key为数据源名称
     */
    @Override
    public Map<String, DataSource> loadDataSources() {
        return createDataSourceMap(dataSourcePropertiesMap);
    }

    /**
     * 创建数据源映射
     *
     * @param dataSourcePropertiesMap 数据源属性映射
     * @return {@link Map}
     */
    protected Map<String, DataSource> createDataSourceMap(Map<String, DynamicDataSourceProperties> dataSourcePropertiesMap) {
        Map<String, DataSource> dataSourceMap = new HashMap<>(dataSourcePropertiesMap.size() * 2);
        for (Map.Entry<String, DynamicDataSourceProperties> item : dataSourcePropertiesMap.entrySet()) {
            String dsName = item.getKey();
            DynamicDataSourceProperties dataSourceProperty = item.getValue();
            dataSourceMap.put(dsName, defaultDataSourceCreator.createDataSource(dataSourceProperty));
        }
        return dataSourceMap;
    }
}
