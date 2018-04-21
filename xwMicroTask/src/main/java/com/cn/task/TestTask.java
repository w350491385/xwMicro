package com.cn.task;

import com.cn.TO.SmsDataTO;
import com.cn.sms.SmsDataService;
import com.common.util.DateUtils;
import com.rpc.spring.ApplicationContextUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.annotation.Resource;
import java.util.List;


/**
 * 测试任务
 * @author Administrator
 */
public class TestTask extends AbstractTask {

	private SmsDataService smsDataService;

	@Override
	public void doExecute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("biz_code:"+context.getJobDetail().getJobDataMap().get("biz_code")+"---TestTask---"+ DateUtils.currentDate2String());
		List<SmsDataTO> list = smsDataService.list();
		System.out.println(list);
	}

	@Override
	void initMethod() {
		this.smsDataService = (SmsDataService)ApplicationContextUtils.applicationContext.getBean("smsDataService");
	}
}
