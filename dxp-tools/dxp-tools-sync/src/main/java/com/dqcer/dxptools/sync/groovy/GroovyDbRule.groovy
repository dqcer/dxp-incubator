package com.dqcer.dxptools.sync.groovy

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class GroovyDbRule implements IRule {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public int getType() {
        return GROOVY_DB_TYPE;
    }

    @Override
    public void printInfo() {
        log.info("这是一段来自数据库的Groovy脚本");
        printInfoHigh();
    }

    public void printInfoHigh() {
        log.info("这是一段来自数据库的Groovy脚本的代码");
    }
}