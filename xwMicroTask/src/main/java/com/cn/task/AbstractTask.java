package com.cn.task;

import com.cn.TO.SmsDataTO;
import com.common.util.DateUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;

/**
 * Created by Administrator on 2018/4/3.
 */
public abstract class AbstractTask implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        initMethod();
        doExecute(context);
    }
    protected abstract void doExecute(JobExecutionContext context) throws JobExecutionException;

    abstract void initMethod();
}
