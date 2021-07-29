package com.dqcer.dxptools.sync.groovy

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class GroovyFileRule implements IRule {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public int getType() {
        return GROOVY_FILE_TYPE;
    }

    @Override
    public void printInfo() {
        log.info("这是一段来自Groovy文件的代码");
        printInfoHigh();
    }

    public void printInfoHigh() {
        log.info("这是一段来自Groovy文件的代码的代码");
    }
}
