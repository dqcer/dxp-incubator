package com.dqcer.dxptools.dynamic.dao

import org.apache.ibatis.jdbc.SQL

class DemoSqlProvider implements IBaseSql{

    @Override
    SQL sqlProvider(Object object){
        return new SQL().
                SELECT("id","status")
                .FROM("pub_role")
    }
}
