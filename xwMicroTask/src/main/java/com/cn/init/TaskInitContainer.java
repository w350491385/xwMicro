package com.cn.init;

import javax.annotation.Resource;

import com.cn.TO.TaskDataTO;
import com.cn.common.QuartzScheduleMgr;
import com.cn.task.TaskDataService;
import com.rpc.spring.ApplicationContextUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 任务初始化
 */
public class TaskInitContainer implements ApplicationListener {
    private static Logger logger = LoggerFactory.getLogger(TaskInitContainer.class);
    @Resource
    private TaskDataService taskDataService;
    private Object object = new Object();
    private volatile boolean initFlag = false;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (ContextRefreshedEvent.class.getName().equals(event.getClass().getName())) {
            synchronized (object) {
                if (!initFlag)
                    initTask();
                initFlag = true;
            }
        }
    }

    private void initTask() {
        logger.info("-------------start init task---------------");
        try {
            List<TaskDataTO> list = taskDataService.list();
            Scheduler scheduler = QuartzScheduleMgr.getInstanceScheduler();
            if (list == null || list.size() == 0){
                throw new RuntimeException("init task list is null");
            }
            for (TaskDataTO task : list) {
                Class<? extends Job> jobClass = (Class<? extends Job>) Class.forName(task.getFullclass());
                JobDetail job = JobBuilder.newJob(jobClass).withIdentity(task.getName(), task.getGroupname())
                        .usingJobData("biz_code", task.getBizCode()).build();
                Trigger trigger = TriggerBuilder.newTrigger().withIdentity(task.getTriggername(), task.getGroupname())
                        .startNow()
                        .withSchedule(CronScheduleBuilder.cronSchedule(task.getCorn()))
                        .build();
                scheduler.scheduleJob(job, trigger);//设置调度相关的Job
            }
            scheduler.start();
        } catch (Exception e) {
            logger.error("", e);
        }
        logger.info("-------------end init task---------------");
    }

    private Map<String,Object> build(String fullClassName,Class<? extends Job> jobClass) {
        Constructor<?>[] constructors = jobClass.getConstructors();
        if (constructors.length > 1)
            throw new RuntimeException("fullClassName is " + fullClassName + " has Multiple constructor");
        Map<String,Object> map = new HashMap<>();
        Constructor<?> constructor = constructors[0];
        Type[]  types = constructor.getGenericParameterTypes();
        for (Type type:types){
            String typeName = type.getTypeName();
            try {
                if (typeName!=null || !"".equals(typeName) || !"java".equals(typeName)) {
                    Object object = ApplicationContextUtils.applicationContext.getBean(Class.forName(typeName));
                    map.put(typeName,object);
                }
            } catch (ClassNotFoundException e) {
                logger.error("", e);
            }
        }
        return map;
    }
}
