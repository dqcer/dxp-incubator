package com.dqcer.dxptools.sync.dbhelper;


import com.dqcer.dxptools.sync.bean.JobInfoBean;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface DbHelper {
	public String assembleSQL(String paramString, Connection paramConnection, JobInfoBean paramJobInfo)
		    throws SQLException;
	public void executeSQL(String sql, Connection conn)
		    throws SQLException;

	String createTable (Connection connection, JobInfoBean jobInfo);

	public List<Object> getTableInsertSql(Connection conn, String tableName , String where)throws Exception;

	String batchInsertSql(Connection connection, String tableName , String where);
	List<Map<String, Object>> demo(Connection connection, String tableName , String where);

	public boolean oneExecuteSql(String sql, Connection conn) throws SQLException;

	public boolean oneUpdateSql(String sql, Connection conn) throws SQLException;

	public String createTable(Connection connection, String tableName);


	List<String> getTablesName(Connection connection, String databaseName);

}
