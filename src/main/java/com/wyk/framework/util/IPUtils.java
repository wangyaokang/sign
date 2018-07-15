/**
 * 
 */
package com.wyk.framework.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * IP相关工具类
 * 
 * @author bocar
 *
 */
public class IPUtils {

	/**
	 * 获取客户端IP
	 * 
	 * @return
	 */
	public static String getClientIP(HttpServletRequest request) {
		// x-forwarded-for:10.5.15.104
		String ipAddress = request.getHeader("x-forwarded-for");
		
		if (ipAddress != null) {
			StringBuffer buf = new StringBuffer();
			for (int i = 0; i < ipAddress.length(); i++) {
				char ch = ipAddress.charAt(i);
				if (ch != ' ')
					buf.append(ch);
			}
			ipAddress = buf.toString();
		}

		if (ipAddress != null) {
			if (ipAddress.length() > 0 && !ipAddress.startsWith("10.25.")) {
				int tmpIndex = ipAddress.indexOf(",");
				if (tmpIndex > 0) {
					ipAddress = ipAddress.substring(0, tmpIndex);
				}
				return ipAddress;
			}
		}

		ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");

		if (ipAddress != null) {
			int index = ipAddress.indexOf(',');
			if (index > 0) {
				ipAddress = ipAddress.substring(0, index);
			}
			return ipAddress;
		}

		ipAddress = request.getHeader("CLIENT_IP");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}

		int index = ipAddress.indexOf(',');
		if (index > 0) {
			ipAddress = ipAddress.substring(0, index);
		}

		if (ipAddress == null) {
			ipAddress = "";
		}

		return ipAddress;
	}
	
	/**
	 * IP校验
	 * @param ip
	 * @return
	 */
	public static boolean isIpAddress(String ip) {
		String regex = "(((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(ip);
		return m.matches();
	}
	
	/**
	 * 将IP地址编码转换成整数
	 * <p>
	 * 形如 AAA.BBB.CCC.DDD的IP地址格式，利用255次幂的加权成整数编码，即：
	 * AAA * (255 * 255 * 255) + BBB * (255 * 255) + CCC * 255 + DDD
	 * </p>
	 * 
	 * @param ip
	 * @return
	 */
	public static int encode(String ip) {
		if (!isIpAddress(ip)) {
			throw new IllegalArgumentException("不合法的IP地址");
		}
		String[] ipArr = StringUtils.split(ip, ".");
		int result = 0;
		if (ipArr != null && ipArr.length == 4) {
			result += Integer.parseInt(ipArr[0]) * (255 * 255 * 255);
			result += Integer.parseInt(ipArr[1]) * (255 * 255);
			result += Integer.parseInt(ipArr[2]) * 255;
			result += Integer.parseInt(ipArr[3]);
		}
		return result;
	}
	
	/**
	 * 将IP地址的整数编码转换成IP字符模式
	 * <p>
	 * 利用除以255次幂的方式解析
	 * (ipCode / (255 * 255 * 255)).((ipCode % (255 * 255 * 255)) / (255 * 255))..以此类推
	 * </p>
	 * 
	 * @param ipCode
	 * @return
	 */
	public static String decode(int ipCode) {
		StringBuffer result = new StringBuffer("");
		result.append(ipCode / (255 * 255 * 255));
		ipCode = ipCode % (255 * 255 * 255);
		
		result.append('.').append(ipCode / (255 * 255));
		ipCode = ipCode % (255 * 255);
		
		result.append('.').append(ipCode / 255);
		result.append('.').append(ipCode % 255);
		
		return result.toString();
	}
}
