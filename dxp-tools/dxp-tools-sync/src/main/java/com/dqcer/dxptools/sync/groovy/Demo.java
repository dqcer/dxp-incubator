package com.dqcer.dxptools.sync.groovy;

import com.dqcer.dxptools.sync.groovy.demo.DynamicExecutor;
import com.dqcer.dxptools.sync.groovy.demo.DynamicFactory;
import com.dqcer.dxptools.sync.groovy.demo.IBaseService;
import com.dqcer.dxptools.sync.groovy.demo.SpringContextUtils;

public class Demo {

    public static void main(String[] args) throws Exception {
        DynamicFactory dynamicFactory = DynamicFactory.getInstance();
        DynamicExecutor executor = new DynamicExecutor();
        String dao = "package com.dqcer.dxptools.sync.groovy.dao\n" +
                "\n" +
                "import org.apache.ibatis.annotations.Mapper\n" +
                "import org.apache.ibatis.annotations.Select\n" +
                "\n" +
                "@Mapper\n" +
                "interface RoleDAO {\n" +
                "\n" +
                "    @Select(\"\"\"<script>select * from pub_role</script>\"\"\")\n" +
                "    List<Map> get();\n" +
                "}";
        String str = "package com.dqcer.dxptools.sync.groovy.demo\n" +
                "\n" +
                "import com.dqcer.dxptools.sync.groovy.dao.RoleDAO\n" +
                "import org.springframework.beans.factory.annotation.Autowired\n" +
                "\n" +
                "class DemoService1 implements IBaseService {\n" +
                "\n" +
                "    @Autowired\n" +
                "    private RoleDAO roleDAO\n" +
                "\n" +
                "    /**\n" +
                "     * 视图\n" +
                "     *\n" +
                "     * @return {@link }\n" +
                "     */\n" +
                "    @Override\n" +
                "    def Object view() {\n" +
                "        return roleDAO.get()\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * 导出excel\n" +
                "     */\n" +
                "    @Override\n" +
                "    void exportExcel() {\n" +
                "    }\n" +
                "}";
        IBaseService daoService = dynamicFactory.loadNewInstance(dao);
        SpringContextUtils.autowireBean(daoService);

        IBaseService iBaseService = dynamicFactory.loadNewInstance(str);
        executor.register("1", iBaseService);

        SpringContextUtils.autowireBean(iBaseService);
        executor.run("1", null);
    }

//    @RequestMapping("/runScript")
//    public Object runScript(String script) throws Exception {
//        if (script != null) {
//            // 这里其实就是groovy的api动态的加载生成一个Class，然后反射生成对象，然后执行run方法，最后返回结果
//            // 最精华的地方就是SpringContextUtils.autowireBean，可以实现自动注入bean，
//            Class clazz = new GroovyClassLoader().parseClass(script);
//            GroovyClassLoader groovyClassLoader = new GroovyClassLoader();
//            Method run = clazz.getMethod("run");
//            Object o = clazz.newInstance();
//            SpringContextUtils.autowireBean(o);
//            Object ret = run.invoke(o);
//            return ret;
//        } else {
//            return "no script";
//        }
//    }

}
