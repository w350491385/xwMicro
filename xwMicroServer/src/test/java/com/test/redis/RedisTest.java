package com.test.redis;

import com.test.base.TestBase;
import org.junit.Test;
import org.omg.CORBA.Object;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * redis 测试
 */
public class RedisTest extends TestBase {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test1() {
        System.out.println("---------------" + redisTemplate + "------------");
    }

    @Test
    public void testRedisSet() {
        String key = "name";
        String value = "jaco";
        redisTemplate.opsForValue().set(key, value);
    }

    @Test
    public void testReidsGet() {
        String key = "name";
        String value = (String) redisTemplate.opsForValue().get(key);
        System.out.println("----------------value is "+ value);
    }

    @Test
    public void testSetInnerTime(){
        redisTemplate.opsForValue().set("username","tom",10, TimeUnit.SECONDS);//
        try {
            Thread.sleep(10*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String value = (String) redisTemplate.opsForValue().get("username");
        System.out.println("----------------value is "+ value);//由于设置的是10秒失效，十秒之内查询有结果，十秒之后返回为null
    }

    @Test
    public void  tsetAppand(){
        redisTemplate.opsForValue().set("keyAppand","hello world");
        redisTemplate.opsForValue().set("keyAppand","redis", 6);
        String value = (String) redisTemplate.opsForValue().get("keyAppand");//api有问题
        System.out.println("***************" + value);
    }

    @Test
    public void setIfAbsent(){
        System.out.println("------" + redisTemplate.opsForValue().setIfAbsent("multi1","multi1"));//false multi1之前已经存在
        System.out.println("------" + redisTemplate.opsForValue().setIfAbsent("multi111","multi111"));//true multi111之前不存在
    }

    @Test
    public void testMultiSet(){
        Map<String,String> maps = new HashMap<>();
        maps.put("multi1","multi1");
        maps.put("multi2","multi2");
        maps.put("multi3","multi3");
        redisTemplate.opsForValue().multiSet(maps);
        List<String> keys = new ArrayList<>();
        keys.add("multi1");
        keys.add("multi2");
        keys.add("multi3");
        System.out.println(redisTemplate.opsForValue().multiGet(keys));
    }

    @Test
    public void testMultiSetIfAbsent(){
        Map<String,String> maps = new HashMap<>();
        maps.put("multi11","multi11");
        maps.put("multi22","multi22");
        maps.put("multi33","multi33");
        Map<String,String> maps2 = new HashMap<>();
        maps2.put("multi1","multi1");
        maps2.put("multi2","multi2");
        maps2.put("multi3","multi3");
        System.out.println("result : " + redisTemplate.opsForValue().multiSetIfAbsent(maps));
        System.out.println("result : " + redisTemplate.opsForValue().multiSetIfAbsent(maps2));
    }

    @Test
    public void testGetAndSet(){
        redisTemplate.opsForValue().set("getSetTest","test");
        System.out.println(" result : " + redisTemplate.opsForValue().getAndSet("getSetTest","test2"));
        System.out.println(" result : " + redisTemplate.opsForValue().get("getSetTest"));
    }

    @Test
    public void testIncrement(){
        redisTemplate.opsForValue().increment("increInt",1);
        System.out.println("***************" + redisTemplate.opsForValue().get("increInt"));
    }

    @Test
    public void testIncrement2(){
        redisTemplate.opsForValue().increment("increDouble",1.2);
        System.out.println("***************" + redisTemplate.opsForValue().get("increInt"));
    }

    @Test
    public void testAppend1(){
        redisTemplate.opsForValue().append("appendTest","Hello");
        System.out.println(redisTemplate.opsForValue().get("appendTest"));
        redisTemplate.opsForValue().append("appendTest","world");
        System.out.println(redisTemplate.opsForValue().get("appendTest"));
    }

    @Test
    public void testGet(){
        System.out.println("*********"+redisTemplate.opsForValue().get("appendTest",0,5));
        System.out.println("*********"+redisTemplate.opsForValue().get("appendTest",0,-1));
        System.out.println("*********"+redisTemplate.opsForValue().get("appendTest",-3,-1));
    }

    @Test
    public void testKeySize(){
        redisTemplate.opsForValue().set("key","hello world");
        System.out.println("***************" + redisTemplate.opsForValue().size("key"));
    }

    @Test
    public void testListRange(){
        List<String> list = new ArrayList<>();
        list.add("java");
        list.add("C#");
        list.add(".net");
        list.add("python");
        list.add("go");
        list.add("c");
        list.add("c++");
        redisTemplate.opsForList().leftPushAll("list",list);
        System.out.println("*************** " + redisTemplate.opsForList().range("list",0,-1));
    }

    @Test
    public void testListSize(){
        System.out.println("*************** " + redisTemplate.opsForList().size("list"));
    }
}
