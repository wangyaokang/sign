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
        invoker.setHost("http://localhost:8080/library/");
    }

    //登录
    @Test
    public void login(){
        long startTime = System.currentTimeMillis();
        Map<String, String> map = new HashMap<String, String>();
        map.put("wxId", "13381503907");
        paramMap.put("method" , "login");
        paramMap.put("params", map);
        String content = invoker.post("/api/login", paramMap);
        System.out.println(content);
        System.out.println("获取:" + (System.currentTimeMillis() - startTime));
        System.out.println(" total time: " + (System.currentTimeMillis() - startTime));
    }

    //登录
    @Test
    public void loginLibrary(){
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

}
