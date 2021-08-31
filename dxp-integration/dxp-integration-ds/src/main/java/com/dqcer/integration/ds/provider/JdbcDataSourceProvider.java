package com.dqcer.integration.ds.provider;

import com.dqcer.integration.ds.DynamicRoutingDataSource;
import com.dqcer.integration.ds.creator.DefaultDataSourceCreator;
import com.dqcer.integration.ds.properties.DynamicDataSourceProperties;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class JdbcDataSourceProvider implements DataSourceProvider {

    private final DefaultDataSourceCreator defaultDataSourceCreator;



    private final DynamicRoutingDataSource hikariDataSource;

    public JdbcDataSourceProvider(DynamicRoutingDataSource hikariDataSource,DefaultDataSourceCreator defaultDataSourceCreator) {
        this.hikariDataSource = hikariDataSource;
        this.defaultDataSourceCreator = defaultDataSourceCreator;
    }


    /**
     * 加载数据来源
     *
     * @return {@link Map}
     */
    @Override
    public Map<String, DataSource> loadDataSources() {
        Map<String, DataSource> map = new HashMap<>(8);
        try {
            Map<String, DataSource> dataSources = hikariDataSource.getDataSources();
            DataSource master = dataSources.get("master");
            Connection connection = master.getConnection();
            Statement statement = connection.createStatement();
            ResultSet query = statement.executeQuery("select * from ct_cust_ds");
            while (query.next()) {
                String name = query.getString("db_name");
                String username = query.getString("db_username");
                String password = query.getString("db_password");
                String url = query.getString("db_url");
                String driver = "com.mysql.cj.jdbc.Driver";
                DynamicDataSourceProperties dynamicDataSourceProperties = new DynamicDataSourceProperties();
                dynamicDataSourceProperties.setDriverClassName(driver);
                dynamicDataSourceProperties.setDsName(name);
                dynamicDataSourceProperties.setUsername(username);
                dynamicDataSourceProperties.setPassword(password);
                dynamicDataSourceProperties.setUrl(url);
                map.put(name, defaultDataSourceCreator.createDataSource(dynamicDataSourceProperties));
            }
            JdbcUtils.closeConnection(connection);
            JdbcUtils.closeStatement(statement);
        } catch (Exception e) {
            e.printStackTrace();

        }

        return map;
    }
}
