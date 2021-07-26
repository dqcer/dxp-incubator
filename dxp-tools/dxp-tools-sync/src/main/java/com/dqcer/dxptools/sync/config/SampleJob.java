package com.dqcer.dxptools.sync.config;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class SampleJob extends QuartzJobBean {

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 执行内部
     *
     * @param jobExecutionContext 工作执行上下文
     * @throws JobExecutionException 作业执行异常
     */
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("Quartz ---->  Hello, " + this.name);
    }
}
