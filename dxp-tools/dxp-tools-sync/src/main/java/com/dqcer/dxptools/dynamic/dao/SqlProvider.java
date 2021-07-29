package com.dqcer.dxptools.dynamic.dao;

import org.apache.ibatis.jdbc.SQL;

public class SqlProvider {

    public String all(SQL sql){
        return sql.toString();
    }
}
