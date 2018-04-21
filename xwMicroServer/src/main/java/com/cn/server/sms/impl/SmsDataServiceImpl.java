package com.cn.server.sms.impl;

import com.cn.TO.SmsDataTO;
import com.cn.dao.SmsPushMapper;
import com.cn.model.SmsPush;
import com.cn.model.SmsPushExample;
import com.cn.sms.SmsDataService;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/3.
 */
@Service
public class SmsDataServiceImpl implements SmsDataService {
    private static Logger logger = LoggerFactory.getLogger(SmsDataServiceImpl.class);
    @Resource
    private SmsPushMapper smsPushMapper;

    @Override
    public List<SmsDataTO> list() {
        List<SmsPush> list = smsPushMapper.selectByExample(new SmsPushExample());
        List<SmsDataTO> smsDataTOs = new ArrayList<>();
        for (SmsPush smsPush : list){
            SmsDataTO smsDataTO = new SmsDataTO();
            try {
                BeanUtils.copyProperties(smsDataTO,smsPush);
            } catch (IllegalAccessException |InvocationTargetException e) {
                logger.error("",e);
            }
            smsDataTOs.add(smsDataTO);
        }
        return smsDataTOs;
    }
}
