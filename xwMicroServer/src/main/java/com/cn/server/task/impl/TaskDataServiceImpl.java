package com.cn.server.task.impl;

import com.cn.TO.TaskDataTO;
import com.cn.dao.TaskDataMapper;
import com.cn.model.TaskData;
import com.cn.model.TaskDataExample;
import com.cn.task.TaskDataService;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 */
@Service
public class TaskDataServiceImpl implements TaskDataService {

    private static Logger logger = LoggerFactory.getLogger(TaskDataServiceImpl.class);

    @Resource
    private TaskDataMapper taskDataMapper;

    @Override
    public List<TaskDataTO> list() {
        List<TaskData> taskDatas = taskDataMapper.selectByExample(new TaskDataExample());
        List<TaskDataTO> taskDataTOs = new ArrayList<>();
        for (TaskData temp:taskDatas){
            TaskDataTO taskDataTO = new TaskDataTO();
            try {
                BeanUtils.copyProperties(taskDataTO,temp);
            } catch (InvocationTargetException | IllegalAccessException e) {
                logger.error("",e);
            }
            taskDataTOs.add(taskDataTO);
        }
        return taskDataTOs;
    }
}
