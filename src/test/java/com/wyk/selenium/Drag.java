package com.wyk.selenium;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;

public class Drag {
	@Test
	public void captureScreenInCurrentWindow() throws MalformedURLException, InterruptedException {
		System.setProperty("webdriver.chrome.driver", "brower_driver/chromedriver.exe");
		WebDriver driver = new RemoteWebDriver(new URL("http://192.168.5.23:4444/wd/hub/"), DesiredCapabilities.chrome());
        driver.manage().window().maximize();
        driver.get("https://www.cdzfgjj.gov.cn:9802/cdnt/login.jsp#per");
        Thread.sleep(2000);
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("alert('我现在在服务器')");
        Thread.sleep(2000);
        driver.quit();
	}
	
	public static void main(String[] args) throws InterruptedException, FileNotFoundException, IOException {
		Context ctx = Context.enter();
		Scriptable scope = ctx.initStandardObjects();
		String jsPath = "d:/project/library/src/test/java/com/wyk/selenium/test.js";
		ctx.evaluateReader(scope, new java.io.FileReader(jsPath), "test", 1, null);
		Object fObj = scope.get("test", scope);
		Object functionArgs[] = { "wyk" };
		Function f = (Function) fObj;
		Object result = f.call(ctx, scope, scope, functionArgs);
		System.out.println("result=" + result);
	}
}
