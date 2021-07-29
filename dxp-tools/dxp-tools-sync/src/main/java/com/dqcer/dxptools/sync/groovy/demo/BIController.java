package com.dqcer.dxptools.sync.groovy.demo;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BIController {

    @RequestMapping(value = "")
    public Object view() throws Exception {
        String str = "package com.dqcer.dxptools.sync.groovy.demo\n" +
                "\n" +
                "class DemoService implements IBaseService {\n" +
                "    /**\n" +
                "     * 视图\n" +
                "     *\n" +
                "     * @return {@link }\n" +
                "     */\n" +
                "    @Override\n" +
                "    def Object view() {\n" +
                "        return \"good\"\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * 导出excel\n" +
                "     */\n" +
                "    @Override\n" +
                "    void exportExcel() {\n" +
                "    }\n" +
                "}\n";
        GroovyClassLoader classLoader = new GroovyClassLoader();
        Class groovyClass = classLoader.parseClass(str);
        GroovyObject groovyObject = (GroovyObject) groovyClass.newInstance();
        Object result = groovyObject.invokeMethod("view",null);
        return result;
    }
}
