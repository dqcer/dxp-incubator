package com.dqcer.dxptools.dynamic.service

import com.dqcer.dxptools.dynamic.dao.BaseDAO
import org.apache.ibatis.jdbc.SQL

class DemoService1 implements IBaseService {

    private BaseDAO baseDAO

    public DemoService1(BaseDAO baseDAO) {
        this.baseDAO = baseDAO;
    }

    /**
     * 视图
     *
     * @return {@link }
     */
    @Override
    def Object view() {
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
                "}"
        GroovyClassLoader classLoader = new GroovyClassLoader()
        Class groovyClass = classLoader.parseClass(str)
        GroovyObject groovyObject = (GroovyObject) groovyClass.newInstance()
        SQL sql = (SQL) groovyObject.invokeMethod("sqlProvider",null)


        List<Map> all = baseDAO.view(sql)

        //  todo 根据map生成建表语句
        return all;
    }

    /**
     * 导出excel
     */
    @Override
    void exportExcel() {
    }
}
