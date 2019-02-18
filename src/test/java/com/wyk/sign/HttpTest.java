package com.wyk.sign;

import com.wyk.sign.utils.HttpInvoker;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.TestName;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
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

    public static void main(String[] args) throws UnsupportedEncodingException {
        String url = "掌上校园使用说明";
        System.out.println(URLDecoder.decode(url, "utf-8"));
    }

    //登录
    @Test
    public void login(){
        long startTime = System.currentTimeMillis();
        Map<String, String> map = new HashMap<String, String>();
        map.put("wxId", "15000496839");
        paramMap.put("method" , "login");
        paramMap.put("params", map);
        String content = invoker.post("/api/login", paramMap);
        System.out.println(content);
        System.out.println("获取:" + (System.currentTimeMillis() - startTime));
        System.out.println(" total time: " + (System.currentTimeMillis() - startTime));
    }
//
//    //注册
//    @Test
//    public void register(){
//        long startTime = System.currentTimeMillis();
//       // {"wxId":"olXz64tz8EYrvJbDwjW-j1rluZqk","wxName":"甜啊🌸","wxAvatarUrl":"https://wx.qlogo.cn/mmopen/vi_32/AAwcDIcsNZDKXPo9cVXVpQ1705w18TIA7cQlXt8dBdKnNxagpBm9FTK46erbQmUuFJcPbGWQj7JhaxVXHrpOrg/132","realName":"向雨田","userType":3,"sno":"1710050045","classId":9}
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("wxId", "olXz64tz8EYrvJbDwjW-j1rluZqk");
//        map.put("wxName", "甜啊\uD83C\uDF38");
//        map.put("wxAvatarUrl", "wwww.youku.com");
//        map.put("userType", "3");
//        map.put("realName", "王五");
//        map.put("sno", "201106214");
//        map.put("classId", "1");
//        paramMap.put("method" , "register");
//        paramMap.put("params", map);
//        String content = invoker.post("/api/login", paramMap);
//        System.out.println(content);
//        System.out.println("获取:" + (System.currentTimeMillis() - startTime));
//        System.out.println(" total time: " + (System.currentTimeMillis() - startTime));
//    }
//
//    //新增班级
//    @Test
//    public void insertClasses(){
//        long startTime = System.currentTimeMillis();
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("name", "物理11102班");
//        map.put("adminId", "2");
//        paramMap.put("token" , "15000496839");
//        paramMap.put("method" , "insert");
//        paramMap.put("params", map);
//        String content = invoker.post("/api/classes", paramMap);
//        System.out.println(content);
//        System.out.println("获取:" + (System.currentTimeMillis() - startTime));
//        System.out.println(" total time: " + (System.currentTimeMillis() - startTime));
//    }
//
//    //获取班级信息
//    @Test
//    public void classesInfo(){
//        long startTime = System.currentTimeMillis();
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("id", "2");
//        paramMap.put("token" , "13381503907");
//        paramMap.put("method" , "info");
//        paramMap.put("params", map);
//        String content = invoker.post("/api/classes", paramMap);
//        System.out.println(content);
//        System.out.println("获取:" + (System.currentTimeMillis() - startTime));
//        System.out.println(" total time: " + (System.currentTimeMillis() - startTime));
//    }
//
//    //获取班级信息
//    @Test
//    public void deleteClasses(){
//        long startTime = System.currentTimeMillis();
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("id", "2");
//        paramMap.put("token" , "15000496839");
//        paramMap.put("method" , "delete");
//        paramMap.put("params", map);
//        String content = invoker.post("/api/classes", paramMap);
//        System.out.println(content);
//        System.out.println("获取:" + (System.currentTimeMillis() - startTime));
//        System.out.println(" total time: " + (System.currentTimeMillis() - startTime));
//    }
//
//    //新增课程
//    @Test
//    public void insertCourse(){
//        long startTime = System.currentTimeMillis();
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("name", "数学 物理");
//        paramMap.put("token" , "15000496839");
//        paramMap.put("method" , "insert");
//        paramMap.put("params", map);
//        String content = invoker.post("/api/course", paramMap);
//        System.out.println(content);
//        System.out.println("获取:" + (System.currentTimeMillis() - startTime));
//        System.out.println(" total time: " + (System.currentTimeMillis() - startTime));
//    }
//
//    //获取课程信息
//    @Test
//    public void courseInfo(){
//        long startTime = System.currentTimeMillis();
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("id", "1");
//        paramMap.put("token" , "13381503907");
//        paramMap.put("method" , "info");
//        paramMap.put("params", map);
//        String content = invoker.post("/api/course", paramMap);
//        System.out.println(content);
//        System.out.println("获取:" + (System.currentTimeMillis() - startTime));
//        System.out.println(" total time: " + (System.currentTimeMillis() - startTime));
//    }
//
//    //获取课程信息
//    @Test
//    public void deleteCourse(){
//        long startTime = System.currentTimeMillis();
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("id", "1");
//        paramMap.put("token" , "15000496839");
//        paramMap.put("method" , "delete");
//        paramMap.put("params", map);
//        String content = invoker.post("/api/course", paramMap);
//        System.out.println(content);
//        System.out.println("获取:" + (System.currentTimeMillis() - startTime));
//        System.out.println(" total time: " + (System.currentTimeMillis() - startTime));
//    }
//
//    //新增授课信息
//    @Test
//    public void insertElective(){
//        long startTime = System.currentTimeMillis();
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("classId", "1");
//        map.put("adminId", "1");
//        map.put("courseId", "1");
//        paramMap.put("token" , "15000496839");
//        paramMap.put("method" , "insert");
//        paramMap.put("params", map);
//        String content = invoker.post("/api/elective", paramMap);
//        System.out.println(content);
//        System.out.println("获取:" + (System.currentTimeMillis() - startTime));
//        System.out.println(" total time: " + (System.currentTimeMillis() - startTime));
//    }
//
//    //新增签到信息
//    @Test
//    public void insertSignInfo(){
//        long startTime = System.currentTimeMillis();
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("startDate", "2018-08-09 10:00:00");
//        map.put("stopDate", "2018-08-09 11:00:00");
//        map.put("address", "东方明珠");
//        map.put("classId", "1");
//        map.put("courseId", "1");
//        paramMap.put("token" , "15000496839");
//        paramMap.put("method" , "insert");
//        paramMap.put("params", map);
//        String content = invoker.post("/api/signInfo", paramMap);
//        System.out.println(content);
//        System.out.println("获取:" + (System.currentTimeMillis() - startTime));
//        System.out.println(" total time: " + (System.currentTimeMillis() - startTime));
//    }
//
//    //新增签到信息
//    @Test
//    public void signInfoModify(){
//        long startTime = System.currentTimeMillis();
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("id", "1");
//        map.put("startDate", "2018-08-01 09:00");
//        map.put("stopDate", "2018-08-01 10:00");
//        map.put("address", "东方明珠");
//        map.put("classId", "1");
//        map.put("courseId", "2");
//        paramMap.put("token" , "15000496839");
//        paramMap.put("method" , "modify");
//        paramMap.put("params", map);
//        String content = invoker.post("/api/signInfo", paramMap);
//        System.out.println(content);
//        System.out.println("获取:" + (System.currentTimeMillis() - startTime));
//        System.out.println(" total time: " + (System.currentTimeMillis() - startTime));
//    }
//
//    //新增签到信息
//    @Test
//    public void signInfo(){
//        long startTime = System.currentTimeMillis();
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("id", "1");
//        paramMap.put("token" , "15000496839");
//        paramMap.put("method" , "info");
//        paramMap.put("params", map);
//        String content = invoker.post("/api/signInfo", paramMap);
//        System.out.println(content);
//        System.out.println("获取:" + (System.currentTimeMillis() - startTime));
//        System.out.println(" total time: " + (System.currentTimeMillis() - startTime));
//    }
//
//    private static class TestInfoThread implements Runnable{
//        @Override
//        public void run() {
//            HttpInvoker invoker = HttpInvoker.getInstance();
//            invoker.setHost("http://localhost:8080/sign/");
//            Map<String, String> map = new HashMap<String, String>();
//            map.put("id", "1");
//            Map<String, Object> paramMap = new HashMap<>();
//            paramMap.put("token" , "15000496839");
//            paramMap.put("method" , "info");
//            paramMap.put("params", map);
//            String content = invoker.post("/api/classes", paramMap);
//            System.out.println("查询：" + content);
//        }
//    }
//
//    private static class TestModifyThread implements Runnable{
//        private String testName;
//
//        public TestModifyThread(String testName){
//            this.testName = testName;
//        }
//
//        @Override
//        public void run() {
//            HttpInvoker invoker = HttpInvoker.getInstance();
//            invoker.setHost("http://localhost:8080/sign/");
//            Map<String, String> map = new HashMap<String, String>();
//            map.put("info", "1");
//            map.put("name", testName);
//            Map<String, Object> paramMap = new HashMap<>();
//            paramMap.put("token" , "13764177152");
//            paramMap.put("method" , "modify");
//            paramMap.put("params", map);
//            String content = invoker.post("/api/classes", paramMap);
//            System.out.println("修改：" + content);
//        }
//    }
//
//    //新增签到信息
//    @Test
//    public void addSign(){
//        long startTime = System.currentTimeMillis();
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("infoId", "2");
//        map.put("signDate", "2018-07-26 10:30:00");
//        map.put("signAddress", "凌阳大厦");
//        paramMap.put("token" , "17717543071");
//        paramMap.put("method" , "insert");
//        paramMap.put("params", map);
//        String content = invoker.post("/api/sign", paramMap);
//        System.out.println(content);
//        System.out.println("获取:" + (System.currentTimeMillis() - startTime));
//        System.out.println(" total time: " + (System.currentTimeMillis() - startTime));
//    }
//
//    //新增签到信息
//    @Test
//    public void deleteSign(){
//        long startTime = System.currentTimeMillis();
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("id", "1");
//        paramMap.put("token" , "13381503907");
//        paramMap.put("method" , "delete");
//        paramMap.put("params", map);
//        String content = invoker.post("/api/sign", paramMap);
//        System.out.println(content);
//        System.out.println("获取:" + (System.currentTimeMillis() - startTime));
//        System.out.println(" total time: " + (System.currentTimeMillis() - startTime));
//    }
//
//    //申请补签
//    @Test
//    public void suppleSign(){
//        long startTime = System.currentTimeMillis();
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("infoId", "2");
//        paramMap.put("token" , "17717543071");
//        paramMap.put("method" , "suppleSign");
//        paramMap.put("params", map);
//        String content = invoker.post("/api/sign", paramMap);
//        System.out.println(content);
//        System.out.println("获取:" + (System.currentTimeMillis() - startTime));
//        System.out.println(" total time: " + (System.currentTimeMillis() - startTime));
//    }
//
//    //同意补签
//    @Test
//    public void acceptSign(){
//        long startTime = System.currentTimeMillis();
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("id", "1");
//        map.put("isAccept", "1");
//        paramMap.put("token" , "15000496839");
//        paramMap.put("method" , "acceptSign");
//        paramMap.put("params", map);
//        String content = invoker.post("/api/sign", paramMap);
//        System.out.println(content);
//        System.out.println("获取:" + (System.currentTimeMillis() - startTime));
//        System.out.println(" total time: " + (System.currentTimeMillis() - startTime));
//    }
//
//    //新增签到信息
//    @Test
//    public void insertTaskInfo(){
//        long startTime = System.currentTimeMillis();
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("courseId", "2");
//        map.put("classId", "1");
//        map.put("deadlineTime", "2018-08-01");
//        map.put("remark", "take you notebook");
//        paramMap.put("token" , "15000496839");
//        paramMap.put("method" , "insert");
//        paramMap.put("params", map);
//        String content = invoker.post("/api/taskInfo", paramMap);
//        System.out.println(content);
//        System.out.println("获取:" + (System.currentTimeMillis() - startTime));
//        System.out.println(" total time: " + (System.currentTimeMillis() - startTime));
//    }
//
//    @Test
//    public void taskInfo(){
//        long startTime = System.currentTimeMillis();
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("id", "1");
//        paramMap.put("token" , "15000496839");
//        paramMap.put("method" , "info");
//        paramMap.put("params", map);
//        String content = invoker.post("/api/taskInfo", paramMap);
//        System.out.println(content);
//        System.out.println("获取:" + (System.currentTimeMillis() - startTime));
//        System.out.println(" total time: " + (System.currentTimeMillis() - startTime));
//    }
//
//    @Test
//    public void modifyTaskInfo(){
//        long startTime = System.currentTimeMillis();
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("courseId", "1");
//        map.put("classId", "1");
//        map.put("deadlineTime", "2018-7-20");
//        map.put("remark", "take you notebook");
//        paramMap.put("token" , "15000496839");
//        paramMap.put("method" , "insert");
//        paramMap.put("params", map);
//        String content = invoker.post("/api/taskInfo", paramMap);
//        System.out.println(content);
//        System.out.println("获取:" + (System.currentTimeMillis() - startTime));
//        System.out.println(" total time: " + (System.currentTimeMillis() - startTime));
//    }
//
//    @Test
//    public void getMyTaskInfoList(){
//        long startTime = System.currentTimeMillis();
//        Map<String, String> map = new HashMap<String, String>();
//        paramMap.put("token" , "15000496839");
//        paramMap.put("method" , "getTaskInfoListByWxId");
//        paramMap.put("params", map);
//        String content = invoker.post("/api/taskInfo", paramMap);
//        System.out.println(content);
//        System.out.println("获取:" + (System.currentTimeMillis() - startTime));
//        System.out.println(" total time: " + (System.currentTimeMillis() - startTime));
//    }
//
//    @Test
//    public void deleteTaskInfo(){
//        long startTime = System.currentTimeMillis();
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("id", "2");
//        paramMap.put("token" , "15000496839");
//        paramMap.put("method" , "delete");
//        paramMap.put("params", map);
//        String content = invoker.post("/api/taskInfo", paramMap);
//        System.out.println(content);
//        System.out.println("获取:" + (System.currentTimeMillis() - startTime));
//        System.out.println(" total time: " + (System.currentTimeMillis() - startTime));
//    }
//
//    @Test
//    public void insertTask(){
//        long startTime = System.currentTimeMillis();
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("infoId", "1");
//        map.put("upDate", "2018-07-30");
//        map.put("upFileUrl", "/attachment/task_info1/201106259_109933.doc");
//        map.put("desc", "English TbTask");
//        paramMap.put("token" , "17717543071");
//        paramMap.put("method" , "insert");
//        paramMap.put("params", map);
//        String content = invoker.post("/api/task", paramMap);
//        System.out.println(content);
//        System.out.println("获取:" + (System.currentTimeMillis() - startTime));
//        System.out.println(" total time: " + (System.currentTimeMillis() - startTime));
//    }
//
//    @Test
//    public void modifyTask(){
//        long startTime = System.currentTimeMillis();
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("id", "1");
//        map.put("upDate", "2018-07-28");
//        map.put("upFileUrl", "");
//        map.put("desc", "English TbTask");
//        paramMap.put("token" , "17717543071");
//        paramMap.put("method" , "modify");
//        paramMap.put("params", map);
//        String content = invoker.post("/api/task", paramMap);
//        System.out.println(content);
//        System.out.println("获取:" + (System.currentTimeMillis() - startTime));
//        System.out.println(" total time: " + (System.currentTimeMillis() - startTime));
//    }
//
//    @Test
//    public void score(){
//        long startTime = System.currentTimeMillis();
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("id", "1");
//        map.put("score", "100");
//        paramMap.put("token" , "15000496839");
//        paramMap.put("method" , "score");
//        paramMap.put("params", map);
//        String content = invoker.post("/api/task", paramMap);
//        System.out.println(content);
//        System.out.println("获取:" + (System.currentTimeMillis() - startTime));
//        System.out.println(" total time: " + (System.currentTimeMillis() - startTime));
//    }
//
//    @Test
//    public void keyword(){
//        long startTime = System.currentTimeMillis();
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("keyWord", "汪");
//        paramMap.put("token" , "15000496839");
//        paramMap.put("method" , "searchKeyWord");
//        paramMap.put("params", map);
//        String content = invoker.post("/api/taskInfo", paramMap);
//        System.out.println(content);
//        System.out.println("获取:" + (System.currentTimeMillis() - startTime));
//        System.out.println(" total time: " + (System.currentTimeMillis() - startTime));
//    }
//
//    @Test
//    public void getSignInfoBySelectedDate(){
//        long startTime = System.currentTimeMillis();
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("selectDate", "2018-07-26");
//        paramMap.put("token" , "13381503907");
//        paramMap.put("method" , "getSignInfoBySelectedDate");
//        paramMap.put("params", map);
//        String content = invoker.post("/api/signInfo", paramMap);
//        System.out.println(content);
//        System.out.println("获取:" + (System.currentTimeMillis() - startTime));
//        System.out.println(" total time: " + (System.currentTimeMillis() - startTime));
//    }
//
//    @Test
//    public void getSignDatesByCurMonth(){
//        long startTime = System.currentTimeMillis();
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("curMonth", "2018-08");
//        paramMap.put("token" , "13381503907");
//        paramMap.put("method" , "getSignDatesByCurMonth");
//        paramMap.put("params", map);
//        String content = invoker.post("/api/signInfo", paramMap);
//        System.out.println(content);
//        System.out.println("获取:" + (System.currentTimeMillis() - startTime));
//        System.out.println(" total time: " + (System.currentTimeMillis() - startTime));
//    }
}
