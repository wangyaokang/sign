package com.wyk.sign;

import com.wyk.sign.utils.HttpInvoker;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class HttpTest {
	
    Map<String, Object> paramMap = new HashMap<String, Object>();
    
    HttpInvoker invoker ;
    
    @Before
    public void init(){
        invoker = HttpInvoker.getInstance();
        invoker.setHost("http://localhost:8080/sign/");
    }

    //登录
    @Test
    public void login(){
        long startTime = System.currentTimeMillis();
        Map<String, String> map = new HashMap<String, String>();
        map.put("wxId", "13381503908");
        paramMap.put("method" , "login");
        paramMap.put("params", map);
        String content = invoker.post("/api/login", paramMap);
        System.out.println(content);
        System.out.println("获取:" + (System.currentTimeMillis() - startTime));
        System.out.println(" total time: " + (System.currentTimeMillis() - startTime));
    }

    //注册
    @Test
    public void register(){
        long startTime = System.currentTimeMillis();
        Map<String, String> map = new HashMap<String, String>();
        map.put("wxId", "13381503907");
        map.put("wxName", "AngryAnt");
        map.put("wxAvatarUrl", "wwww.youku.com");
        map.put("userType", "3");
        map.put("realName", "张三");
        map.put("sno", "201106259");
        map.put("classId", "1");
        paramMap.put("method" , "register");
        paramMap.put("params", map);
        String content = invoker.post("/api/login", paramMap);
        System.out.println(content);
        System.out.println("获取:" + (System.currentTimeMillis() - startTime));
        System.out.println(" total time: " + (System.currentTimeMillis() - startTime));
    }

    //新增班级
    @Test
    public void insertClasses(){
        long startTime = System.currentTimeMillis();
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "物理11102班");
        map.put("adminId", "2");
        paramMap.put("token" , "15000496839");
        paramMap.put("method" , "insert");
        paramMap.put("params", map);
        String content = invoker.post("/api/classes", paramMap);
        System.out.println(content);
        System.out.println("获取:" + (System.currentTimeMillis() - startTime));
        System.out.println(" total time: " + (System.currentTimeMillis() - startTime));
    }

    //获取班级信息
    @Test
    public void classesInfo(){
        long startTime = System.currentTimeMillis();
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", "2");
        paramMap.put("token" , "0");
        paramMap.put("method" , "info");
        paramMap.put("params", map);
        String content = invoker.post("/api/classes", paramMap);
        System.out.println(content);
        System.out.println("获取:" + (System.currentTimeMillis() - startTime));
        System.out.println(" total time: " + (System.currentTimeMillis() - startTime));
    }

    //新增课程
    @Test
    public void insertCourse(){
        long startTime = System.currentTimeMillis();
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "基础英语");
        paramMap.put("token" , "15000496839");
        paramMap.put("method" , "insert");
        paramMap.put("params", map);
        String content = invoker.post("/api/course", paramMap);
        System.out.println(content);
        System.out.println("获取:" + (System.currentTimeMillis() - startTime));
        System.out.println(" total time: " + (System.currentTimeMillis() - startTime));
    }

    //获取班级信息
    @Test
    public void courseInfo(){
        long startTime = System.currentTimeMillis();
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", "1");
        paramMap.put("token" , "13381503907");
        paramMap.put("method" , "info");
        paramMap.put("params", map);
        String content = invoker.post("/api/course", paramMap);
        System.out.println(content);
        System.out.println("获取:" + (System.currentTimeMillis() - startTime));
        System.out.println(" total time: " + (System.currentTimeMillis() - startTime));
    }
}
