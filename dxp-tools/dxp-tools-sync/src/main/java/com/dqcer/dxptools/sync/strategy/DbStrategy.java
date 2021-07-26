package com.dqcer.dxptools.sync.strategy;


import com.dqcer.dxptools.sync.bean.DataSourceBean;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author dongqin
 * @description syn数据库接口
 * @date 2021/07/26
 */
public interface DbStrategy  {


	/**
	 * 批量插入sql
	 *
	 * @param connection 连接
	 * @param tableName  表名
	 * @param where      在哪里
	 * @return {@link List<Map<String, Object>>}
	 */
	List<Map<String, Object>> findBatchInsertSql(DataSource connection, String tableName , List<String> where);

	/**
	 * 一个更新sql
	 *
	 * @param map       地图
	 * @param tableName 表名
	 * @param where     在哪里
	 * @return {@link String}
	 */
	String findOneUpdateSql(Map<String, Object> map, String tableName, String where);

	boolean oneExecuteSql(String sql, DataSource conn) ;

	boolean oneUpdateSql(String sql, DataSource conn);

	/**
	 * 创建表的ddl
	 *
	 * @param connection 连接
	 * @param tableName  表名
	 * @return {@link String}
	 */
	String createTableDDL(DataSource connection, String tableName);


	List<String> getTablesName(DataSource connection, String databaseName);




	DataSource create(DataSourceBean sourceInfo);
}
