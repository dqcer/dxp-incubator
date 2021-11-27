package com.dqcer.tools.dxptoolsgenerator;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author dongqin
 * @description h2代码生成器测试
 * @date 2021/10/13
 */
public class H2CodeGeneratorTest {

    /**
     * 执行初始化数据库脚本
     */
    @BeforeAll
    public static void before() throws SQLException {
        Connection conn = DATA_SOURCE_CONFIG.getConn();
        InputStream inputStream = H2CodeGeneratorTest.class.getResourceAsStream("/sql/init.sql");
        ScriptRunner scriptRunner = new ScriptRunner(conn);
        scriptRunner.setAutoCommit(true);
        scriptRunner.runScript(new InputStreamReader(inputStream));
        conn.close();
    }
    private static Pattern linePattern = Pattern.compile("_(\\w)");

    public static String lineToHump(String str) {
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }


    /**
     * 将字符串的首字母转大写
     *
     * @param str 需要转换的字符串
     * @return
     */
    private static String captureName(String str) {
        // 进行字母的ascii编码前移，效率要高于截取字符串进行转换的操作
        char[] cs= str.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }


    /**
     * 数据源配置
     */
    private static final DataSourceConfig DATA_SOURCE_CONFIG = new DataSourceConfig
            .Builder("jdbc:mysql://172.16.2.171:3308/E00001ZS?characterEncoding=UTF-8&useUnicode=true&autoReconnect=true", "root", "123456")
            .dbQuery(new MySqlQuery())
            .schema("mybatis-plus")
            .typeConvert(new MySqlTypeConvert())
            .keyWordsHandler(new MySqlKeyWordsHandler())
            .build();;

    /**
     * 策略配置
     */
    private StrategyConfig.Builder strategyConfig() {
        return new StrategyConfig.Builder().addInclude(TABLE_NAME); // 设置需要生成的表名
    }

    /**
     * 全局配置
     */
    private GlobalConfig.Builder globalConfig() {
        return new GlobalConfig.Builder().fileOverride().dateType(DateType.ONLY_DATE);
    }

    /**
     * 包配置
     */
    private PackageConfig.Builder packageConfig() {
        return new PackageConfig.Builder().parent(PACKAGE_NAME).moduleName(MODEL_NAME);
    }

    /**
     * 模板配置
     */
    private TemplateConfig.Builder templateConfig() {
        return new TemplateConfig.Builder();
    }

    public static String TABLE_NAME = "t_simple";


    /**
     * 注入配置
     */
    private InjectionConfig.Builder injectionConfig() {
        return new InjectionConfig.Builder().beforeOutputFile((tableInfo, objectMap) -> {
            System.out.println("tableInfo: " + tableInfo.getEntityName() + " objectMap: " + objectMap.size());
        });
    }

    /**
     * 过滤表前缀（后缀同理，支持多个）
     * result: t_simple -> simple
     */
    @Test
    public void testTablePrefix() {
        AutoGenerator generator = new AutoGenerator(DATA_SOURCE_CONFIG);
        generator.strategy(strategyConfig().addTablePrefix("t_", "c_").build());
        generator.global(globalConfig().build());
        generator.execute();
    }



    /**
     * 自定义模板生成的文件路径
     *
     * @see OutputFile
     */
    @Test
    public void testCustomTemplatePath() {
        // 设置自定义路径
        Map<OutputFile, String> pathInfo = new HashMap<>();
        pathInfo.put(OutputFile.controller, USER_DIR);
        pathInfo.put(OutputFile.service, USER_DIR);
        pathInfo.put(OutputFile.serviceImpl, USER_DIR);
        pathInfo.put(OutputFile.mapper, USER_DIR);
        pathInfo.put(OutputFile.other, USER_DIR);
        pathInfo.put(OutputFile.mapperXml, USER_DIR);
        AutoGenerator generator = new AutoGenerator(DATA_SOURCE_CONFIG);
        generator.strategy(strategyConfig().build());
        generator.packageInfo(packageConfig().pathInfo(pathInfo).build());
        generator.global(globalConfig().build());
        generator.execute();
    }
    public static final String USER_DIR = System.getProperty("user.dir") + "/src/main/java";

    public static final String PACKAGE_NAME = "com.dqcer.tool";
    public static final String PACKAGE_DIR = "/com/dqcer/tool";
    public static final String MODEL_NAME = "test";

    /**
     * 自定义模板
     */
    @Test
    public void testCustomTemplate() {



        AutoGenerator generator = new AutoGenerator(DATA_SOURCE_CONFIG);

        InjectionConfig.Builder builder = injectionConfig();

        String baseTableName = captureName(lineToHump(TABLE_NAME));

        // 设置自定义输出文件
        Map<String, String> customFile = new HashMap<>();
        customFile.put(baseTableName + "Biz.java", "/templates/biz.java.vm");
        customFile.put(baseTableName + "BizImpl.java", "/templates/bizImpl.java.vm");

        // 设置自定义路径
        Map<OutputFile, String> pathInfo = new HashMap<>();
        pathInfo.put(OutputFile.controller, USER_DIR  + PACKAGE_DIR + "/" + MODEL_NAME + "/controller");
        pathInfo.put(OutputFile.service, USER_DIR + PACKAGE_DIR  + "/" + MODEL_NAME + "/service");
        pathInfo.put(OutputFile.serviceImpl, USER_DIR + PACKAGE_DIR  + "/" + MODEL_NAME+ "/service/impl");
//        pathInfo.put(OutputFile.mapper, USER_DIR + PACKAGE_DIR  + "/" + MODEL_NAME+ "/biz");
        pathInfo.put(OutputFile.other, USER_DIR + PACKAGE_DIR  + "/" + MODEL_NAME);
        pathInfo.put(OutputFile.mapperXml, USER_DIR + PACKAGE_DIR  + "/" + MODEL_NAME+ "/entity");
        pathInfo.put(OutputFile.entity, USER_DIR + PACKAGE_DIR  + "/" + MODEL_NAME+ "/entity");
        generator.packageInfo(packageConfig().pathInfo(pathInfo).build());

        generator.strategy(strategyConfig()
                .entityBuilder().formatFileName("%sEntity")
                .mapperBuilder().formatMapperFileName("%sBiz").formatXmlFileName("%s.hbm")
                .controllerBuilder().formatFileName("%sController")
                .serviceBuilder().formatServiceFileName("%sService").formatServiceImplFileName("%sServiceImp")
                .build());

        generator.template(templateConfig()
                .entity("/templates/entity.java")
                .controller("/templates/controller.java")
                .service("/templates/service.java")
                .serviceImpl("/templates/serviceImpl.java")
                .mapper("/templates/biz.java")
                .mapperXml("/templates/hbm.xml")
                .build());

        Map<String, Object> map = new HashMap<>();
        map.put("basePackage", PACKAGE_NAME + "."+ MODEL_NAME);
        map.put("baseTableName", baseTableName);

        generator.injection(builder.customFile(customFile).customMap(map).build());
        generator.global(globalConfig().build());
        generator.execute();
    }

    /**
     * 自定义注入属性
     */
    @Test
    public void testCustomMap() {
        // 设置自定义属性
        Map<String, Object> map = new HashMap<>();
        map.put("abc", 123);
        // 设置自定义输出文件
        Map<String, String> customFile = new HashMap<>();
        customFile.put("test.java", "/templates/test.java.vm");

        AutoGenerator generator = new AutoGenerator(DATA_SOURCE_CONFIG);
        generator.strategy(strategyConfig().build());
        generator.template(templateConfig()
                .entity("/templates/entity1.java")
                .build());
        generator.injection(injectionConfig().customMap(map).customFile(customFile).build());
        generator.global(globalConfig().build());
        generator.execute();
    }

    /**
     * 自定义文件
     * key为文件名称，value为文件路径
     * 输出目录为 other
     */
    @Test
    public void testCustomFile() {
        // 设置自定义输出文件
        Map<String, String> customFile = new HashMap<>();
        customFile.put("test.txt", "/templates/test.vm");
        AutoGenerator generator = new AutoGenerator(DATA_SOURCE_CONFIG);
        generator.strategy(strategyConfig().build());
        generator.injection(injectionConfig().customFile(customFile).build());
        generator.global(globalConfig().build());
        generator.execute();
    }

}
