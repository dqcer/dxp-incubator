package com.dqcer.dxptools.sync.groovy.dao

import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface RoleDAO {

    @Select("""<script>select * from pub_role</script>""")
    List<Map> get();
}