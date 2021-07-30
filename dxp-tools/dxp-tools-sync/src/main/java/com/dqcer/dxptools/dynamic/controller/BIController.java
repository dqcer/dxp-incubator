package com.dqcer.dxptools.dynamic.controller;

import com.dqcer.dxptools.dynamic.dao.BaseDAO;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
public class BIController {

    @RequestMapping(value = "/demo")
    public Object view() throws Exception {
        String str = "package com.dqcer.dxptools.dynamic.dao\n" +
                "\n" +
                "import org.apache.ibatis.jdbc.SQL\n" +
                "\n" +
                "class DemoSqlProvider implements IBaseSql{\n" +
                "\n" +
                "    @Override\n" +
                "    SQL sqlProvider(Object object){\n" +
                "        return new SQL().\n" +
                "                SELECT(\"id\",\"status\")\n" +
                "                .FROM(\"pub_role\")\n" +
                "    }\n" +
                "}";
        GroovyClassLoader classLoader = new GroovyClassLoader();
        Class groovyClass = classLoader.parseClass(str);
        GroovyObject groovyObject = (GroovyObject) groovyClass.newInstance();
        SQL sql = (SQL) groovyObject.invokeMethod("sqlProvider",null);

        List<Map> all = baseDAO.view(sql);

        return all;
    }


    @Resource
    BaseDAO baseDAO;
}
