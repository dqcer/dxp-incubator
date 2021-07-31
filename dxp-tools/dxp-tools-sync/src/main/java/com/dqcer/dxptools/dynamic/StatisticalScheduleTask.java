package com.dqcer.dxptools.dynamic;


import com.dqcer.dxptools.dynamic.service.IBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

@Slf4j
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling
public class StatisticalScheduleTask {

    @Scheduled(cron = "0/5 * * * * ?")
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
    private void configureTasks() throws Exception {
        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());

        String str = "package com.dqcer.dxptools.dynamic.service\n" +
                "\n" +
                "import com.dqcer.dxptools.dynamic.dao.BaseDAO\n" +
                "import org.apache.ibatis.jdbc.SQL \n" +
                "\n" +
                "class DemoService1 implements IBaseService {\n" +
                "\n" +
                "    private BaseDAO baseDAO\n" +
                "\n" +
                "    public DemoService1(BaseDAO baseDAO) {\n" +
                "        this.baseDAO = baseDAO;\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * 视图\n" +
                "     *\n" +
                "     * @return {@link }\n" +
                "     */\n" +
                "    @Override\n" +
                "    def Object view() {\n" +
                "        String str = \"package com.dqcer.dxptools.dynamic.dao\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"import org.apache.ibatis.jdbc.SQL\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"class DemoSqlProvider implements IBaseSql{\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"    @Override\\n\" +\n" +
                "                \"    SQL sqlProvider(Object object){\\n\" +\n" +
                "                \"        return new SQL().\\n\" +\n" +
                "                \"                SELECT(\\\"id\\\",\\\"status\\\")\\n\" +\n" +
                "                \"                .FROM(\\\"pub_role\\\")\\n\" +\n" +
                "                \"    }\\n\" +\n" +
                "                \"}\"\n" +
                "        GroovyClassLoader classLoader = new GroovyClassLoader()\n" +
                "        Class groovyClass = classLoader.parseClass(str)\n" +
                "        GroovyObject groovyObject = (GroovyObject) groovyClass.newInstance()\n" +
                "        SQL sql = (SQL) groovyObject.invokeMethod(\"sqlProvider\",null)\n" +
                "\n" +
                "\n" +
                "        List<Map> all = baseDAO.view(sql)\n" +
                "        return all;\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * 导出excel\n" +
                "     */\n" +
                "    @Override\n" +
                "    void exportExcel() {\n" +
                "    }\n" +
                "}\n";


        DynamicFactory instance = DynamicFactory.getInstance();
        IBaseService sqlProvider = instance.loadNewInstanceService(str);
        sqlProvider.statistical();
    }



}
