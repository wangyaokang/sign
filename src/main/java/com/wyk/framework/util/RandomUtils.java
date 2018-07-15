/**
 * 
 */
package com.wyk.framework.util;

import java.util.Random;

public class RandomUtils {
	
	private static String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"; 

	/**
	 * 获得指定长度的字符串
	 * 
	 * @param length
	 * @return
	 */
	public static String getString(int length) {
		Random random = new Random();   
        StringBuffer sb = new StringBuffer();   
        for (int i = 0; i < length; i++) {   
            int number = random.nextInt(base.length());   
            sb.append(base.charAt(number));   
        }   
        return sb.toString();
	}
}
