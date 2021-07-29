package com.dqcer.dxptools.sync.groovy;

import com.dqcer.dxptools.sync.groovy.dao.RulesDao;

import java.util.ArrayList;
import java.util.List;

public class RuleFactory {
    private static volatile RuleFactory singleton = null;

    private RuleFactory() {
    }
    public static RuleFactory getInstance() {
        // 第一次校验singleton是否为空
        if (singleton == null) {
            synchronized (RuleFactory.class) {
                // 第二次校验singleton是否为空
                if (singleton == null) {
                    singleton = new RuleFactory();
                }
            }
        }
        return singleton;
    }

    private RulesDao rulesDao;
    private RuleFactory(RulesDao rulesDao) {
        this.rulesDao = rulesDao;
    }
    public static RuleFactory getInstance(RulesDao rulesDao) {
        // 第一次校验singleton是否为空
        if (singleton == null) {
            synchronized (RuleFactory.class) {
                // 第二次校验singleton是否为空
                if (singleton == null) {
                    singleton = new RuleFactory(rulesDao);
                }
            }
        }
        return singleton;
    }

    public List<IRule> getRuleList() throws Exception {
        //调用普通的JAVA实现做对比
        List<IRule> rules = new ArrayList<>();
        NormalJavaRule normalJavaRule = new NormalJavaRule();
        rules.add(normalJavaRule);

        //直接读取Groovy文件生成IRule实现
        IRule groovyFile = GroovyFactory.getInstance()
                .getIRuleFromPackage("H:\\workspace\\opencode\\dxp-incubator\\dxp-tools\\dxp-tools-sync\\src\\main\\java\\com\\dqcer\\dxptools\\sync\\groovy/GroovyFileRule.groovy");
        rules.add(groovyFile);

        //从Db的Groovy脚本中生成IRule实现
        Rules ruleGroovy = rulesDao.getByName("GroovyDbRule");
        if (ruleGroovy != null) {
            IRule groovyDb = GroovyFactory.getInstance().getIRuleFromCode(ruleGroovy.getRule());
            rules.add(groovyDb);
        }

        return rules;
    }
}
