package com.test.activemq;

import com.test.base.TestBase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

/**
 * activemq 测试
 */
public class ActivemqTest extends TestBase {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Test
    public void test1(){
        System.out.println("----------------"+jmsTemplate+"--------------");
    }
}
