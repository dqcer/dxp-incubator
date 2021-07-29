package com.dqcer.dxptools.dynamic.dao;

import org.apache.ibatis.jdbc.SQL;

public interface IBaseSql {

    SQL sqlProvider(Object object);
}
