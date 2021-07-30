package com.dqcer.dxptools.sync.job;

import com.dqcer.dxptools.sync.bean.DataSourceBean;
import com.dqcer.dxptools.sync.bean.ToolsSyncConfigBean;
import com.dqcer.dxptools.sync.strategy.SyncFactory;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
public class ScheduledJob implements Job {


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        log.info("开始任务调度: " + new Date());

        JobDataMap data = context.getJobDetail().getJobDataMap();
        DataSourceBean srcDb = (DataSourceBean) data.get("srcDb");
        DataSourceBean destDb = (DataSourceBean) data.get("destDb");


        List<String> filterTables = new ArrayList<>();
        filterTables.add("ecc_version_control");
        filterTables.add("undo_log");

        List<String> filterColumns = new ArrayList<>();
        filterColumns.add("created_time");
        filterColumns.add("updated_time");

        ToolsSyncConfigBean configBean = ToolsSyncConfigBean.BeanBuilder.configBean()
                .sourceBean(srcDb)
                .targetBean(destDb)
                .filterTables(filterTables)
                .baseColumns(filterColumns)
                .build();

        SyncFactory instance = SyncFactory.getInstance();
       instance.start(configBean);
    }



}
