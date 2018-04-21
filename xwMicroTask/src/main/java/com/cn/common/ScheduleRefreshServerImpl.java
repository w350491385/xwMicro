package com.cn.common;

import com.rpc.etcd.callback.EtcdRefreshServer;
import com.rpc.spring.config.register.RegisterServer;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 */
public class ScheduleRefreshServerImpl  implements EtcdRefreshServer {

    private static Logger logger = LoggerFactory.getLogger(ScheduleRefreshServerImpl.class);

    @Override
    public void refresh(String beanName,String dir, String propetyName, String value) {
        logger.debug("beanName is {},propertyName is {},value is {}",beanName,propetyName,value);
        if (propetyName.indexOf("_") == -1){
            throw new RuntimeException(propetyName + "propertyName's  format is wrong");
        }
        String[] strs = propetyName.split("_");
        Scheduler scheduler = QuartzScheduleMgr.getInstanceScheduler();
        if (strs.length != 2){
            throw new RuntimeException(propetyName + "propertyName's  format is wrong");
        }
        TriggerKey triggerKey = new TriggerKey(strs[0],strs[1]);
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey.getName(), triggerKey.getGroup())
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule(value))
                .build();
        try {
            scheduler.rescheduleJob(triggerKey,trigger);
        } catch (SchedulerException e) {
            logger.error("",e);
        }
    }
}
