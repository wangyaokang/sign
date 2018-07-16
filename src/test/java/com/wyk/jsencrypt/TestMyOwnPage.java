package com.wyk.jsencrypt;

import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class TestMyOwnPage {
    private void action() {  
        WebClient webClient = new WebClient();  
        try {  
            webClient.getOptions().setCssEnabled(false);  
            webClient.getOptions().setJavaScriptEnabled(true);   
            HtmlPage htmlPage = webClient.getPage("https://puser.zjzwfw.gov.cn/sso/usp.do?action=ssoLogin&servicecode=jhsgjjcx");  
            Thread.sleep(3000);
            String s = "wewewe";  
            ScriptResult t = htmlPage.executeJavaScriptIfPossible("RSA(\"" + s  + "\")", "injected script", 5000);
            System.out.println(t.getJavaScriptResult().toString());
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            webClient.close();  
        }  
  
    }  
  
    public static void main(String[] args) {  
        TestMyOwnPage tmop = new TestMyOwnPage();  
        tmop.action();  
    }  
}
