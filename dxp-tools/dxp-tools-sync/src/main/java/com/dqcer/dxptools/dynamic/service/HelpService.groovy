package com.dqcer.dxptools.dynamic.service

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import groovy.sql.GroovyRowResult
import groovy.sql.Sql

abstract class HelpService extends IBaseService{

    /**
     *   批量插入
     *
     * @param insertSql 插入sql
     * @param groovyRowResults 待插入的数据
     * @return {  @link Object  }
     */
    def batchInsert(insertSql, groovyRowResults) {
        batchInsert(10, insertSql, groovyRowResults)
    }

    /**
     *    批量插入
     *
     @param batchSize 批量大小 调用batchSize次addBatch方法之后，就会执行一次插入操作
     @param insertSql 插入sql
     @param groovyRowResults 待插入的数据
     @return { @link Object }
     */
    def batchInsert(int batchSize, String insertSql, groovyRowResults) {
        def connection = getConnection()
        connection.withBatch(batchSize, insertSql) { ps ->
            for (row in groovyRowResults) {
                Collection values = row.values()
                Object[] objects = values.toArray(new Object[values.size()]);
                ps.addBatch(objects)
            }
        }
        connection.close()
    }

    /**
     *   创建表
     *
     * @param sqlCreateTable sql创建表
     * @return {  @link Object  操作结果}
     */
    def execute(String sqlCreateTable) {
        def connection = getConnection()
        def isOk = connection.execute sqlCreateTable
        connection.close()
        return isOk
    }


    /**
     *   列表 无参
     *
     * @param sqlSelect sql select
     * @return {  @link List < GroovyRowResult >  }
     */
    List<GroovyRowResult> list(String sqlSelect) {
        def connection = this.getConnection()
        def rows = connection.rows sqlSelect
        connection.close()
        return rows
    }


    /**
     *   列表 有参
     *
     * @param sqlSelect sql select
     * @param objects 对象
     * @return {  @link List < GroovyRowResult >  }
     */
    List<GroovyRowResult> list(String sqlSelect, Object... objects) {
        def connection = getConnection()
        def rows = connection.rows sqlSelect, objects
        connection.close()
        return rows
    }

    /**
     *   获得连接
     *
     * @return {  @link Object  }
     */
    def getConnection() {
        HikariConfig configuration = new HikariConfig();
        configuration.setJdbcUrl 'jdbc:mysql://172.16.2.171:3308/a03?serverTimezone=UTC&characterEncoding=UTF-8&useUnicode=true'
        configuration.setUsername 'root'
        configuration.setPassword '123456'
        configuration.setDriverClassName 'com.mysql.cj.jdbc.Driver'
        HikariDataSource dataSource = new HikariDataSource(configuration);
        return new Sql(dataSource)
    }
}
