package com.dqcer.dxptools.sync.component;

import com.dqcer.dxptools.sync.bean.DataSourceBean;
import com.dqcer.dxptools.sync.bean.JobInfoBean;
import com.dqcer.dxptools.sync.job.ScheduledJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

@Component
public class CronSchedulerJob {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    private void scheduleJob1(Scheduler scheduler) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(ScheduledJob.class) .withIdentity("job1", "group1").build();

        JobDataMap jobDataMap = jobDetail.getJobDataMap();

        DataSourceBean dataSourceBean = new DataSourceBean();
        dataSourceBean.setUrl("jdbc:mysql://172.16.2.171:3308/a02?characterEncoding=utf-8");
        dataSourceBean.setDbType("mysql");
        dataSourceBean.setUsername("root");
        dataSourceBean.setPassword("123456");
        dataSourceBean.setDriver("com.mysql.jdbc.Driver");
        jobDataMap.put("srcDb", dataSourceBean);

        DataSourceBean bean = new DataSourceBean();
        bean.setUrl("jdbc:mysql://172.16.2.171:3308/a03?characterEncoding=utf-8");
        bean.setDbType("mysql");
        bean.setUsername("root");
        bean.setPassword("123456");
        bean.setDriver("com.mysql.jdbc.Driver");
        jobDataMap.put("destDb", bean);


        JobInfoBean jobInfoBean = new JobInfoBean();
        jobInfoBean.setCron("0/6 * * * * ?");
        jobInfoBean.setSrcSql("select * from pub_user_role_study_env_site");
        jobInfoBean.setDestTable("pub_user_role_study_env_site");
        jobInfoBean.setDestTableFields("account,password");
        jobInfoBean.setDestTableKey("username");
        jobInfoBean.setDestTableUpdate("password");
        jobDataMap.put("jobInfo", jobInfoBean);

        jobDataMap.put("logTitle", "logTitle");

        //  每天的0点、13点、18点、21点执行
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("9 * * * * ? *");
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1")
                .usingJobData("name","王智1").withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail,cronTrigger);
    }


    /**
     * @Author Smith
     * @Description 同时启动两个定时任务
     * @Date 16:31 2019/1/24
     * @Param
     * @return void
     **/
    public void scheduleJobs() throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        scheduleJob1(scheduler);
        //scheduleJob2(scheduler);
    }
}
