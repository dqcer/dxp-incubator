package com.dqcer.dxptools.sync.dbhelper.impl;

import com.dqcer.dxptools.sync.bean.JobInfoBean;
import com.dqcer.dxptools.sync.dbhelper.DbHelper;
import com.dqcer.dxptools.sync.dbhelper.Tool;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.Date;

@Slf4j
public class MySql implements DbHelper {

    public String assembleSQL(String srcSql, Connection conn, JobInfoBean jobInfo) throws SQLException {
        String uniqueName = Tool.generateString(6) + "_" + jobInfo.getName();
        String[] fields = jobInfo.getDestTableFields().split(",");
        String[] updateFields = jobInfo.getDestTableUpdate().split(",");
        String destTable = jobInfo.getDestTable();
        String destTableKey = jobInfo.getDestTableKey();
        PreparedStatement pst = conn.prepareStatement(srcSql);
        ResultSet rs = pst.executeQuery();
        StringBuffer sql = new StringBuffer();
        sql.append("insert into ").append(jobInfo.getDestTable()).append(" (").append(jobInfo.getDestTableFields()).append(") values ");
        long count = 0;
        while (rs.next()) {
            sql.append("(");
            for (int index = 0; index < fields.length; index++) {
                sql.append("'").append(rs.getString(fields[index])).append(index == (fields.length - 1) ? "'" : "',");
            }
            sql.append("),");
            count++;
        }
        if (rs != null) {
            rs.close();
        }
        if (pst != null) {
            pst.close();
        }
        if (count > 0) {
            sql = sql.deleteCharAt(sql.length() - 1);
            if ((!jobInfo.getDestTableUpdate().equals("")) && (!jobInfo.getDestTableKey().equals(""))) {
                sql.append(" on duplicate key update ");
                for (int index = 0; index < updateFields.length; index++) {
                    sql.append(updateFields[index]).append("= values(").append(updateFields[index]).append(index == (updateFields.length - 1) ? ")" : "),");
                }
                return new StringBuffer("alter table ").append(destTable).append(" add constraint ").append(uniqueName).append(" unique (").append(destTableKey).append(");").append(sql.toString())
                                .append(";alter table ").append(destTable).append(" drop index ").append(uniqueName).toString();
            }
            return sql.toString();
        }
        return null;
    }

    public void executeSQL(String sql, Connection conn) throws SQLException {
        PreparedStatement pst = conn.prepareStatement(sql);
        String[] sqlList = sql.split(";");
        for (int index = 0; index < sqlList.length; index++) {
            pst.addBatch(sqlList[index]);
        }
        pst.executeBatch();
        conn.commit();
        pst.close();
    }

    public boolean oneExecuteSql(String sql, Connection conn) throws SQLException {
        PreparedStatement pst = conn.prepareStatement(sql);
        boolean execute = pst.execute(sql);
        conn.commit();
        pst.close();
        return execute;
    }

    public boolean oneUpdateSql(String sql, Connection conn) throws SQLException {
        PreparedStatement pst = conn.prepareStatement(sql);
        int i = pst.executeUpdate();
        conn.commit();
        pst.close();
        return i == 1 ? true : false;
    }

    @Override
    public String createTable(Connection connection, JobInfoBean jobInfo) {
        String destTable = jobInfo.getDestTable();
        PreparedStatement pst = null;
        try {
            pst = connection.prepareStatement("show create table " + destTable);
            ResultSet rs = pst.executeQuery();
            String substring = "";
            while (rs.next()) {
                String string = rs.getObject(2).toString();
                int index = string.indexOf(destTable);
                substring = string.substring(index - 1);
            }

            pst.close();
            return "create table if not exists " + substring;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String createTable(Connection connection, String tableName) {
        PreparedStatement pst = null;
        try {
            pst = connection.prepareStatement("show create table " + tableName);
            ResultSet rs = pst.executeQuery();
            String substring = "";
            while (rs.next()) {
                String string = rs.getObject(2).toString();
                int index = string.indexOf(tableName);
                substring = string.substring(index - 1);
            }
            pst.close();
            return "create table if not exists " + substring;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<String> getTablesName(Connection connection, String databaseName) {
        List<String> list = new ArrayList<>();

        PreparedStatement pst = null;
        try {
            pst = connection.prepareStatement("select table_name from information_schema.tables where table_schema= " + "'" + databaseName + "'");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String string = rs.getString(1);
                list.add(string);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public String batchInsertSql(Connection connection, String tableName , String where) {
        String sql = null;
        try {
            sql = sql(connection, tableName, where);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sql;
    }

    @Override
    public List<Map<String, Object>> demo(Connection connection, String tableName , String where) {
        List<Map<String, Object>> sql = null;
        try {
            sql = demoSql(connection, tableName, where);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sql;
    }

    public  List<Object> getTableInsertSql(Connection conn, String tableName , String where)
            throws Exception {
        List<Object> list=null;
        return list;
    }


    private String sql(Connection conn, String tableName , String where) throws SQLException {
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from " + tableName + where);


        /**
         * INSERT INTO clients
         * (client_id, client_name, client_type)
         * SELECT 10345, 'IBM', 'advertising'
         * FROM dual
         * WHERE not exists (select * from clients
         * where clients.client_id = 10345);
         */
        String sql = "insert into " + tableName + " ( ";

        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        for (int i = 0; i < columnCount; i++) {
            sql += metaData.getColumnName(i + 1);
            if (i + 1 != columnCount) {
                sql += ",";
            }
        }

        sql += " ) value (";
        while (resultSet.next()) {
            columnCount = resultSet.getMetaData().getColumnCount();
            for (int i = 0; i < columnCount; i++) {
                Object object = resultSet.getObject(i + 1);

                if (object instanceof String) {
                    object = null == object ? null : "'" + object + "'";
                }

                String columnName = metaData.getColumnName(i + 1);
                if (columnName.toLowerCase().endsWith("time") || columnName.toLowerCase().endsWith("date")) {
                    object = null == object ? null : "'" + object + "'";
                }

                sql += object;
                if (i + 1 != columnCount) {
                    sql += ",";
                } else {
                    sql += "),(";
                }
            }
        }
        resultSet.close();
        conn.close();
        int index = sql.lastIndexOf(",(");
        String substring = sql.substring(0, index);
        return substring;
    }


    private List<Map<String, Object>> demoSql(Connection conn, String tableName , String where) throws SQLException {
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from " + tableName + where);

        List<Map<String, Object>> stringList = new ArrayList<>();



        /**
         * INSERT INTO clients
         * (client_id, client_name, client_type)
         * SELECT 10345, 'IBM', 'advertising'
         * FROM dual
         * WHERE not exists (select * from clients
         * where clients.client_id = 10345);
         */
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
            String middleSql = "";
            Map<String, Object> map = new HashMap<>(16);
            List<Object> valueList = new ArrayList<>();
            for (int i = 0; i < columnCount; i++) {
                String key = metaData.getColumnName(i + 1);
                Object object = resultSet.getObject(i + 1);

                if (object instanceof String) {
                    object = null == object ? null : "'" + object + "'";
                }

                String columnName = metaData.getColumnName(i + 1);
                if (columnName.toLowerCase().endsWith("time") || columnName.toLowerCase().endsWith("date")) {
                    object = null == object ? null : "'" + object + "'";
                }

                map.put(key, object);

                valueList.add(object);

                middleSql += object;
                if (i + 1 != columnCount) {
                    middleSql += ",";
                }
            }

            String suffix = " from dual where not exists (select "+ columnNames.toString()+" from  " + tableName + " where  id = "+ map.get("id") + " )";
            String oneSql = prefix + middleSql + suffix;

            Map<String, Object> hashMap = new HashMap<>();
            hashMap.put("sql", oneSql);
            hashMap.put("values", valueList);

            stringList.add(hashMap);
        }
        resultSet.close();
        conn.close();

        return stringList;
    }
    public static void main(String[] args) {
        String str = "2020-10-14T10:49:33";
        LocalDateTime parse = LocalDateTime.parse(str);
        System.out.println(parse);
    }

}
