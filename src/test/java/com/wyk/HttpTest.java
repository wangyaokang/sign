package com.wyk;

import org.junit.Before;
import org.junit.Test;

import com.wyk.utils.HttpInvoker;

import java.util.HashMap;
import java.util.Map;

public class HttpTest {
	
    Map<String, Object> paramMap = new HashMap<String, Object>();
    
    HttpInvoker invoker ;
    
    @Before
    public void init(){
        invoker = HttpInvoker.getInstance();
        invoker.setHost("http://localhost:8080/library");
    }

    //登录
    @Test
    public void login(){
        long startTime = System.currentTimeMillis(); 
        Map<String, String> map = new HashMap<String, String>();
        map.put("mobile", "13800138000");
        map.put("password", "123456");
        paramMap.put("method" , "login");
        paramMap.put("params", map);
        String content = invoker.post("/api/login", paramMap);
        System.out.println(content);
        System.out.println("获取:" + (System.currentTimeMillis() - startTime));
        System.out.println(" total time: " + (System.currentTimeMillis() - startTime));
    }
    
    //注册用户
    @Test
    public void register(){
    	long startTime = System.currentTimeMillis(); 
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("mobile", "13800138000");
    	map.put("password", "123456");
    	paramMap.put("method" , "register");
    	paramMap.put("params", map);
    	String content = invoker.post("/api/login", paramMap);
    	System.out.println(content);
    	System.out.println("获取:" + (System.currentTimeMillis() - startTime));
    	System.out.println(" total time: ");
    	System.out.println(System.currentTimeMillis() - startTime);
    }
    
    @Test
    public void logout(){
    	long startTime = System.currentTimeMillis();
    	paramMap.put("token", "iKVXAnYcxT0uHVbrpG0C9yXhkleKmtwB");
    	paramMap.put("method", "logout");
    	String content = invoker.post("/api/login", paramMap);
    	System.out.println(content);
    	System.out.println(" total time: " + (System.currentTimeMillis() - startTime));
    }
    
    @Test
    public void info(){
    	long startTime = System.currentTimeMillis();
    	paramMap.put("token", "lW9wJ0lcWhcxfUBjf97YO08zAYEjj3Qu");
    	paramMap.put("method", "info");
    	String content = invoker.post("/api/user", paramMap);
    	System.out.println(content);
    	System.out.println(" total time: " + (System.currentTimeMillis() - startTime));
    }
    
    @Test
    public void uploadPhoto(){
    	long startTime = System.currentTimeMillis();
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("filePath", "C:\\Users\\Win7\\Desktop\\wode.jpg");
    	paramMap.put("token", "GzgJ2YOEd8aqUOJkND7YLuOIi5FdfzQY");
    	paramMap.put("method", "uploadPhoto");
    	paramMap.put("params", map);
    	String content = invoker.post("/api/user", paramMap);
    	System.out.println(content);
    	System.out.println(" total time: " + (System.currentTimeMillis() - startTime));
    }
    
    @Test
    public void addBook(){
    	long startTime = System.currentTimeMillis();
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("bookNo", "2018012201");
    	map.put("bookName", "刘心武揭秘红楼梦");
    	map.put("author", "刘心武");
    	map.put("publisher", "人民日报出版社");
    	map.put("synopsis", "一部旷世奇书，。。。。");
    	map.put("location", "TP-南-52.90");
    	paramMap.put("method", "addBook");
    	paramMap.put("params", map);
    	String content = invoker.post("/api/book", paramMap);
    	System.out.println(content);
    	System.out.println(" total time: " + (System.currentTimeMillis() - startTime));
    }
    
    @Test
    public void bookInfo(){
    	long startTime = System.currentTimeMillis();
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("bookNo", "2018012201");
    	paramMap.put("method", "info");
    	paramMap.put("params", map);
    	String content = invoker.post("/api/book", paramMap);
    	System.out.println(content);
    	System.out.println(" total time: " + (System.currentTimeMillis() - startTime));
    }
    
    @Test
    public void queryBooks(){
    	long startTime = System.currentTimeMillis();
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("type", "2");
    	map.put("key", "人民");
    	paramMap.put("method", "queryBooks");
    	paramMap.put("params", map);
    	String content = invoker.post("/api/book", paramMap);
    	System.out.println(content);
    	System.out.println(" total time: " + (System.currentTimeMillis() - startTime));
    }
    
    @Test
    public void addBook2Bag(){
    	long startTime = System.currentTimeMillis();
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("bookId", "1");
    	paramMap.put("token", "iKVXAnYcxT0uHVbrpG0C9yXhkleKmtwB");
    	paramMap.put("method", "addBook2Bag");
    	paramMap.put("params", map);
    	String content = invoker.post("/api/bag", paramMap);
    	System.out.println(content);
    	System.out.println(" total time: " + (System.currentTimeMillis() - startTime));
    }
    
    @Test
    public void queryBags(){
    	long startTime = System.currentTimeMillis();
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("bookId", "1");
    	paramMap.put("token", "iKVXAnYcxT0uHVbrpG0C9yXhkleKmtwB");
    	paramMap.put("method", "queryBags");
    	String content = invoker.post("/api/bag", paramMap);
    	System.out.println(content);
    	System.out.println(" total time: " + (System.currentTimeMillis() - startTime));
    }
    
    @Test
    public void webBookTest(){
        long startTime = System.currentTimeMillis(); 
        Map<String, String> map = new HashMap<String, String>();
    	map.put("bookNo", "2018012201");
        String content = invoker.get("/book/queryBook.json", map);
        System.out.println(content);
        System.out.println("获取:" + (System.currentTimeMillis() - startTime));
        System.out.println(" total time: " + (System.currentTimeMillis() - startTime));
    }
}
