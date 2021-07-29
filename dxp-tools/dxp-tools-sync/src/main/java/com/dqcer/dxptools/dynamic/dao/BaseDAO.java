package com.dqcer.dxptools.dynamic.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.Map;

@Mapper
public interface BaseDAO {

    @SelectProvider(type = SqlProvider.class, method = "all")
    List<Map> all(SQL sql);
}
