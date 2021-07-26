 package com.dqcer.dxptools.sync.strategy;

import com.dqcer.dxptools.sync.bean.DataSourceBean;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

@Slf4j
public class MySqlStrategy implements DbStrategy {



    @Override
    public boolean oneExecuteSql(String sql, final DataSource dataSource) {
        PreparedStatement pst = null;
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            pst = connection.prepareStatement(sql);
            boolean execute = pst.execute(sql);
            pst.close();
            connection.close();
            return execute;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean oneUpdateSql(String sql, DataSource conn) {
        Connection connection = null;
        try {
            connection = conn.getConnection();
            final PreparedStatement pst = connection.prepareStatement(sql);
            int i = pst.executeUpdate();
            pst.close();
            connection.close();
            return i == 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }


    @Override
    public String createTableDDL(DataSource dataSource, String tableName) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            final PreparedStatement statement = connection.prepareStatement("show create table " + tableName);
            ResultSet rs = statement.executeQuery();
            String substring = "";
            while (rs.next()) {
                substring = new String(rs.getObject(2).toString().getBytes());
                int index = substring.indexOf(tableName);
                substring = substring.substring(index - 1);
            }

            statement.close();
            connection.close();
            return "create table if not exists " + substring;
        } catch (SQLException   e) {
            e.printStackTrace();
        }
        return null;
    }



    @Override
    public List<String> getTablesName(DataSource dataSource, String databaseName) {
        List<String> list = new ArrayList<>();
        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            final PreparedStatement pst = connection.prepareStatement("select table_name from information_schema.tables where table_schema= " + "'" + databaseName + "'");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String string = rs.getString(1);
                list.add(string);
            }
            pst.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    @Override
    public List<Map<String, Object>> findBatchInsertSql(DataSource connection, String tableName , List<String> where) {
        List<Map<String, Object>> sql = null;
        try {
            sql = demoSql(connection.getConnection(), tableName, where, 10, 1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sql;
    }


    private List<Map<String, Object>> demoSql(Connection conn, String tableName , List<String> wheres, int startDay, int endDay) throws SQLException {

        final Statement statement = conn.createStatement();

        //  拼装where条件
        boolean isOk = true;
        for (String where : wheres) {
            ResultSet set = statement.executeQuery("show columns from " + tableName + " like '" + where + "'");
            int row = set.getRow();
            if (row == 0) {
                isOk = false;
                break;
            }
        }
        StringBuffer stringBuffer = new StringBuffer();
        if (isOk){
            for (int i = 0; i < wheres.size(); i++) {
                if (i == 0) {
                    stringBuffer.append(" where");
                }
                stringBuffer.append(String.format("date(%s) between date_sub(curdate(),interval %s day) and date_sub(curdate(),interval %s day)", wheres.get(i), startDay, endDay));
                if (i + 1 != wheres.size()) {
                    stringBuffer.append(" or ");
                }
            }
        }


        //  查询结果集
        ResultSet resultSet = statement.executeQuery("select * from " + tableName + stringBuffer.toString());

        List<Map<String, Object>> stringList = new ArrayList<>();

        String prefix = "insert into " + tableName + " ( ";
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        StringBuffer columnNames = new StringBuffer();
        for (int i = 0; i < columnCount; i++) {
            columnNames.append(metaData.getColumnName(i + 1));
            if (i + 1 != columnCount) {
                columnNames.append(",");
            }
        }
        prefix = prefix + columnNames.toString() + " ) select ";

        while (resultSet.next()) {
            columnCount = resultSet.getMetaData().getColumnCount();
            StringBuffer middleSql = new StringBuffer();
            Map<String, Object> map = new HashMap<>(16);
            List<Object> valueList = new ArrayList<>();
            for (int i = 0; i < columnCount; i++) {
                String key = metaData.getColumnName(i + 1);
                Object object = resultSet.getObject(i + 1);

                if (object instanceof String) {
                    object = null == object ? null : "'" + object.toString().replace("'", "''") + "'";
                }

                String columnName = metaData.getColumnName(i + 1);
                //  base_plan,last_plan,actual_compelete
                if (columnName.toLowerCase().endsWith("time") || columnName.toLowerCase().endsWith("date")
                        || columnName.toLowerCase().endsWith("old_plan")
                        || columnName.toLowerCase().endsWith("new_plan")
                        || columnName.toLowerCase().endsWith("actual_compelete")
                        || columnName.toLowerCase().endsWith("last_plan")
                        || columnName.toLowerCase().endsWith("base_plan")
                ) {
                    object = null == object ? null : "'" + object + "'";
                }

                map.put(key, object);

                valueList.add(object);

                middleSql.append(object);
                if (i + 1 != columnCount) {
                    middleSql.append(",");
                }
            }

            String suffix = " from dual where not exists (select "+ columnNames.toString()+" from  " + tableName + " where  id = "+ map.get("id") + " )";
            String oneSql = prefix + middleSql + suffix;

            Map<String, Object> hashMap = new HashMap<>(8);
            hashMap.put("sql", oneSql);
            hashMap.put("values", valueList);

            stringList.add(hashMap);
        }
        resultSet.close();
        conn.close();

        return stringList;
    }

    /**
     * 一个更新sql
     *
     * @param map       地图
     * @param tableName 表名
     * @param where     在哪里
     * @return {@link String}
     */
    @Override
    public String findOneUpdateSql(Map<String, Object> map, String tableName, String where) {
        String str = map.get("sql").toString();
        int index = str.indexOf("(");
        int index1 = str.indexOf(")");
        String substring = str.substring(index+1, index1 );
        String[] keys = substring.split(",");

        List<Object> objects = (List<Object>) map.get("values");

        String updateSql = "update "+ tableName +" set";

        StringBuffer buffer = new StringBuffer();
        int length = keys.length;
        for (int i = 0; i< length ; i ++) {
            buffer.append(keys[i] + "=" + objects.get(i));
            if (i + 1 != length) {
                buffer.append(",");
            }
        }
        return updateSql + buffer.toString() + " where id =" + objects.get(0);
    }




    @Override
    public DataSource create(DataSourceBean sourceInfo) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setUsername(sourceInfo.getUsername());
        hikariConfig.setPassword(sourceInfo.getPassword());
        hikariConfig.setJdbcUrl(sourceInfo.getUrl());
        hikariConfig.setDriverClassName("com.mysql.jdbc.Driver");
        return new HikariDataSource(hikariConfig);
    }

}
