package com.dqcer.dxptools.sync.strategy;

import com.dqcer.dxptools.sync.bean.DataSourceBean;
import com.dqcer.dxptools.sync.bean.ToolsSyncConfigBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * @author dongqin
 * @description 同步的工厂
 * @date 2021/07/26
 */
public final class SyncFactory {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    private volatile static SyncFactory syncFactory;

    private SyncFactory (){}

    public static SyncFactory getInstance() {
        if (syncFactory == null) {
            synchronized (SyncFactory.class) {
                if (syncFactory == null) {
                    syncFactory = new SyncFactory();
                }
            }
        }
        return syncFactory;
    }

    public void start(ToolsSyncConfigBean bean) {
        doSync(bean.getSourceBean(), bean.getTargetBean(), bean.getFilterTables(), bean.getBaseColumns());
    }


    private void doSync(DataSourceBean srcDb, DataSourceBean destDb,  List<String> filterTables, List<String> filterColumns) {
        String databaseName = findDataBaseNameByUrl(srcDb.getUrl());
        DbStrategy dbHelper = DbContext.create(destDb.getDbType());
        DataSource inConn = dbHelper.create(srcDb);
        DataSource outConn = dbHelper.create(destDb);

        long l = System.currentTimeMillis();

        List<String> e00001ZS = dbHelper.getTablesName(inConn, databaseName);
        for (String tableName : e00001ZS) {

            boolean filter = isFilter(tableName, filterTables);
            if (filter) {
                continue;
            }

            //  如果不存在，则创建表
            String createTableSql = dbHelper.createTableDDL(inConn, tableName);
            boolean isOk = dbHelper.oneExecuteSql(createTableSql, outConn);
            if (isOk) {
                log.info("table: [{}] 已经存在，无需创建", tableName);
            }

            List<Map<String, Object>> demo = dbHelper.findBatchInsertSql(inConn, tableName, filterColumns);
            for (Map<String, Object> map : demo) {
                String string = map.get("sql").toString();
                //log.info(">>>>>>>>>{}", string);
                boolean isInsertOk = dbHelper.oneExecuteSql(string, outConn);
                if (isInsertOk) {
                    continue;
                }
                String sql = dbHelper.findOneUpdateSql(map, tableName, "");
                boolean isUpdateOk = dbHelper.oneUpdateSql(sql, outConn);
                if (isUpdateOk) {
                    continue;
                }
                log.info("没有可以更新的数据: [{}]", sql);
            }
        }
        long timeConsuming = System.currentTimeMillis() - l;
        log.info("同步完成，共耗时毫秒:[{}] 分钟:[{}]", timeConsuming, timeConsuming/1000/60);
    }

    private String findDataBaseNameByUrl(String jdbcUrl) {
        String database = null;
        int pos, pos1;
        String connUri;

        jdbcUrl = jdbcUrl.toLowerCase();


        if (!jdbcUrl.startsWith("jdbc:")
                || (pos1 = jdbcUrl.indexOf(':', 5)) == -1) {
            throw new IllegalArgumentException("Invalid JDBC url.");
        }

        connUri = jdbcUrl.substring(pos1 + 1);

        if (connUri.startsWith("//")) {
            if ((pos = connUri.indexOf('/', 2)) != -1) {
                database = connUri.substring(pos + 1);
            }
        } else {
            database = connUri;
        }

        if (database.contains("?")) {
            database = database.substring(0, database.indexOf("?"));
        }

        if (database.contains(";")) {
            database = database.substring(0, database.indexOf(";"));
        }

        return database;
    }

    /**
     * 是否过滤
     *
     * @param tableName    表名
     * @param filterTables 过滤表
     * @return boolean
     */
    private boolean isFilter(String tableName, List<String> filterTables) {
        for (String filterTable : filterTables) {
            if (tableName.equalsIgnoreCase(filterTable)) {
                return true;
            }
        }
        return false;
    }

}
