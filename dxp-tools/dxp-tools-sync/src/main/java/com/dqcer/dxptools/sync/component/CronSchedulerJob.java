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
        dataSourceBean.setUrl("jdbc:mysql://172.16.11.11:3308/dhms001");
        dataSourceBean.setDbType("mysql");
        dataSourceBean.setUsername("root");
        dataSourceBean.setPassword("123456");
        dataSourceBean.setDriver("com.mysql.jdbc.Driver");
        jobDataMap.put("destDb", dataSourceBean);
        jobDataMap.put("srcDb", dataSourceBean);

        DataSourceBean bean = new DataSourceBean();
        bean.setUrl("jdbc:mysql://172.16.11.11:3308/A04");
        bean.setDbType("mysql");
        bean.setUsername("root");
        bean.setPassword("123456");
        bean.setDriver("com.mysql.jdbc.Driver");
        jobDataMap.put("destDb", bean);


        JobInfoBean jobInfoBean = new JobInfoBean();
        jobInfoBean.setCron("0/10 * * * * ?");
        jobInfoBean.setSrcSql("select * from pub_user_role_study_env_site");
        jobInfoBean.setDestTable("pub_user_role_study_env_site");
        jobInfoBean.setDestTableFields("account,password");
        jobInfoBean.setDestTableKey("username");
        jobInfoBean.setDestTableUpdate("password");
        jobDataMap.put("jobInfo", jobInfoBean);

        jobDataMap.put("logTitle", "logTitle");

        // 6的倍数秒执行 也就是 6 12 18 24 30 36 42 ....
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/6 * * * * ?");
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1")
                .usingJobData("name","王智1").withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail,cronTrigger);
    }

//    private void scheduleJob2(Scheduler scheduler) throws SchedulerException{
//        JobDetail jobDetail = JobBuilder.newJob(ScheduledJob2.class) .withIdentity("job2", "group2").build();
//        // 12秒的倍数执行  12  24 36  48  60
//        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/12 * * * * ?");
//        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger2", "group2")
//                .usingJobData("name","王智2").withSchedule(scheduleBuilder).build();
//        scheduler.scheduleJob(jobDetail,cronTrigger);
//    }

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
