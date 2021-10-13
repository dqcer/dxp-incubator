package com.dqcer.tools.dxptoolsgenerator;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
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
        return new StrategyConfig.Builder().addInclude("t_simple"); // 设置需要生成的表名
    }

    /**
     * 全局配置
     */
    private GlobalConfig.Builder globalConfig() {
        return new GlobalConfig.Builder().fileOverride();
    }

    /**
     * 包配置
     */
    private PackageConfig.Builder packageConfig() {
        return new PackageConfig.Builder();
    }

    /**
     * 模板配置
     */
    private TemplateConfig.Builder templateConfig() {
        return new TemplateConfig.Builder();
    }

    /**
     * 注入配置
     */
    private InjectionConfig.Builder injectionConfig() {
        // 测试自定义输出文件之前注入操作，该操作再执行生成代码前 debug 查看
        return new InjectionConfig.Builder().beforeOutputFile((tableInfo, objectMap) -> {
            System.out.println("tableInfo: " + tableInfo.getEntityName() + " objectMap: " + objectMap.size());
        });
    }

    /**
     * 简单生成
     */
    @Test
    public void testSimple() {
        AutoGenerator generator = new AutoGenerator(DATA_SOURCE_CONFIG);
        generator.strategy(strategyConfig().build());
        generator.global(globalConfig().build());
        generator.execute();
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
     * 过滤字段后缀（前缀同理，支持多个）
     * result: deleted_flag -> deleted
     */
    @Test
    public void testFieldSuffix() {
        AutoGenerator generator = new AutoGenerator(DATA_SOURCE_CONFIG);
        generator.strategy(strategyConfig().addFieldSuffix("_flag").build());
        generator.global(globalConfig().build());
        generator.execute();
    }



    /**
     * 逻辑删除字段设置
     * result: 新增@TableLogic注解
     * 忽略字段设置
     * result: 不生成
     */
    @Test
    public void testLogicDeleteAndIgnoreColumn() {
        AutoGenerator generator = new AutoGenerator(DATA_SOURCE_CONFIG);
        generator.strategy(strategyConfig().entityBuilder()
                .logicDeleteColumnName("deleted") // 基于数据库字段
                .logicDeletePropertyName("deleteFlag")// 基于模型属性
                .addIgnoreColumns("age") // 基于数据库字段
                .build());
        generator.global(globalConfig().build());
        generator.execute();
    }

    /**
     * 自定义模板生成的文件名称
     * result: TSimple -> TSimpleEntity
     */
    @Test
    public void testCustomTemplateName() {
        AutoGenerator generator = new AutoGenerator(DATA_SOURCE_CONFIG);
        generator.strategy(strategyConfig()
                .entityBuilder().formatFileName("%sEntity")
                .mapperBuilder().formatMapperFileName("%sDao").formatXmlFileName("%sXml")
                .controllerBuilder().formatFileName("%sAction")
                .serviceBuilder().formatServiceFileName("%sService").formatServiceImplFileName("%sServiceImp")
                .build());
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
        pathInfo.put(OutputFile.mapperXml, "D://");
        pathInfo.put(OutputFile.entity, "D://entity//");
        AutoGenerator generator = new AutoGenerator(DATA_SOURCE_CONFIG);
        generator.strategy(strategyConfig().build());
        generator.packageInfo(packageConfig().pathInfo(pathInfo).build());
        generator.global(globalConfig().build());
        generator.execute();
    }

    /**
     * 自定义模板
     */
    @Test
    public void testCustomTemplate() {

        // 设置自定义输出文件
        Map<String, String> customFile = new HashMap<>();
        customFile.put("test.java", "/templates/biz.java.vm");

        AutoGenerator generator = new AutoGenerator(DATA_SOURCE_CONFIG);
        generator.strategy(strategyConfig().build());
        generator.template(templateConfig()
                .entity("/templates/entity.java")
                .controller("/templates/controller.java")
                .service("/templates/service.java")
                .serviceImpl("/templates/serviceImpl.java")
                .build());
        generator.injection(injectionConfig().customFile(customFile).build());
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
