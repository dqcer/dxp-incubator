package com.dqcer.dxptools.sync.groovy.dao;

import com.dqcer.dxptools.sync.groovy.Rules;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RulesDao {
    @Select("SELECT * FROM pomit_rule where id = #{id}")
    Rules getById(@Param("id") Integer id);

    @Select("SELECT * FROM pomit_rule where name = #{name}")
    Rules getByName(@Param("name") String name);

    @Insert("INSERT INTO pomit_rule(name,rule) VALUE(#{name},#{rule})")
    Integer setRule(@Param("name") String name,@Param("rule") String rule);

    @Select("SELECT * FROM pomit_rule order by create_time DESC")
    List<Rules> getRuleList();

    @Update("UPDATE pomit_rule SET visible=0 WHERE id = #{id}")
    Integer deleteRule(@Param("id") Integer id);

    @Update("UPDATE pomit_rule SET rule= #{rule} WHERE id = #{id}")
    Integer updateRule(@Param("id") Integer id,@Param("rule") String rule);

}