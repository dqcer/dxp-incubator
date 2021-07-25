package com.dqcer.dxptools.sync.job;

import com.dqcer.dxptools.sync.bean.DataSourceBean;
import com.dqcer.dxptools.sync.bean.JobInfoBean;
import com.dqcer.dxptools.sync.dbhelper.DbHelper;
import com.dqcer.dxptools.sync.dbhelper.Factory;
import jdk.nashorn.internal.runtime.regexp.JoniRegExp;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
public class ScheduledJob implements Job {

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

        System.out.println("CRON ----> schedule job1 is running ... + " + name + "  ---->  " + dateFormat.format(new Date()));

        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();

        

        log.info("开始任务调度: " + new Date());
        Connection inConn = null;
        Connection outConn = null;
        JobDataMap data = context.getJobDetail().getJobDataMap();
        DataSourceBean srcDb = (DataSourceBean) data.get("srcDb");
        DataSourceBean destDb = (DataSourceBean) data.get("destDb");
        JobInfoBean jobInfo = (JobInfoBean) data.get("jobInfo");
        String logTitle = (String) data.get("logTitle");
        try {
            inConn = createConnection(srcDb);
            outConn = createConnection(destDb);
            if (inConn == null) {
                log.info("请检查源数据连接!");
                return;
            } else if (outConn == null) {
                log.info("请检查目标数据连接!");
                return;
            }

            DbHelper dbHelper = Factory.create(destDb.getDbType());

            List<String> e00001ZS = dbHelper.getTablesName(inConn, "dhms001");
            for (String tableName : e00001ZS) {

                if (tableName.equalsIgnoreCase("ecc_version_control") || tableName.equalsIgnoreCase("undo_log")) {
                    continue;
                }
                //  查看

                String createTableSql = dbHelper.createTable(inConn, tableName);
                log.info("createTableSql=======>" + createTableSql);
                dbHelper.executeSQL(createTableSql, outConn);

                List<Map<String, Object>> demo = dbHelper.demo(inConn, tableName, "");
                for (Map<String, Object> map : demo) {
                    String string = map.get("sql").toString();
                    //log.info("sql=========>" + string);
                    boolean b = dbHelper.oneExecuteSql(string, outConn);
                    if (!b) {
                        String s = updateSql(map, tableName, "");
                        boolean b1 = dbHelper.oneUpdateSql(s, outConn);
                        if (!b1) {
                            System.out.println("没有可以更新的数据" + s);
                        }
                    }
                }
            }




//            List<Object> tableInsertSql = dbHelper.getTableInsertSql(inConn, jobInfo.getDestTable(), "");
//            for (Object obj : tableInsertSql) {
//                dbHelper.executeSQL(obj.toString(), outConn);
//            }

//
//            long start = new Date().getTime();
//            String sql = dbHelper.assembleSQL(jobInfo.getSrcSql(), inConn, jobInfo);
//            log.info("组装SQL耗时: " + (new Date().getTime() - start) + "ms");
//            if (sql != null) {
//                log.debug(sql);
//                long eStart = new Date().getTime();
//                dbHelper.executeSQL(sql, outConn);
//                log.info("执行SQL语句耗时: " + (new Date().getTime() - eStart) + "ms");
//            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            log.info("关闭源数据库连接");
            destoryConnection(inConn);
            log.info("关闭目标数据库连接");
            destoryConnection(outConn);
        }

    }

    private Connection createConnection(DataSourceBean db) {
        try {
            Class.forName(db.getDriver());
            Connection conn = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword());
            conn.setAutoCommit(false);
            return conn;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    private void destoryConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
                log.info("数据库连接关闭");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }

    public String updateSql(Map<String, Object> map, String tableName, String where) {
        String str = map.get("sql").toString();
        int index = str.indexOf("(");
        int index1 = str.indexOf(")");
        String substring = str.substring(index+1, index1 );
        System.out.println(substring);
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
        String sql = updateSql + buffer.toString() + " where id =" + objects.get(0);
        System.out.println(sql);
        return sql;
    }
}
