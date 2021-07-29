package com.dqcer.dxptools.sync.groovy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NormalJavaRule implements IRule {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Override
    public int getType() {
        return NORMAL_TYPE;
    }

    @Override
    public void printInfo() {
        log.info("这是正常的JAVA代码");
        printInfoHigh();
    }

    //加这段是为了测试改动后输出是否有变化，在ide里，只靠上面的打印看不出来效果
    public void printInfoHigh() {
        log.info("这是正常的JAVA代码的代码");
    }
}
