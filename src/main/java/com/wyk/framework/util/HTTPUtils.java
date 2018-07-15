package com.wyk.framework.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author bocar
 */
public class HTTPUtils {

    public static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 用户直接输出文本数据
     *
     * @param response
     * @param data     需要直接输出的结果数据
     * @throws IOException
     */
    public static void writeData(HttpServletResponse response, String data) throws IOException {
        response.setCharacterEncoding("UTF-8");// 一定要放到下面这句的上面
        response.setContentType("text/plain");
        // NOTE:AJAX提交的数据的格式为UTF-8,通过AJAX提交的数据不要经过过滤器
        // data = new String(data.getBytes("ISO-8859-1"), "UTF-8");
        // 修改过滤器类，然后定义统一的uri样式
        response.setHeader("Pragma", "No-Cache");
        response.setHeader("Cache-Control", "No-Cache");
        response.setDateHeader("Expires", 0);
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
            pw.print(data);
        } catch (IOException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    /**
     * 用户直接输出类的JSON格式
     *
     * @param response
     * @param object
     * @throws IOException
     */
    public static void writeJSON(HttpServletResponse response, Object object) throws IOException {
        response.setCharacterEncoding("UTF-8");// 一定要放到下面这句的上面
        response.setContentType("text/plain");
        // NOTE:AJAX提交的数据的格式为UTF-8,通过AJAX提交的数据不要经过过滤器
        // data = new String(data.getBytes("ISO-8859-1"), "UTF-8");
        // 修改过滤器类，然后定义统一的uri样式
        response.setHeader("Pragma", "No-Cache");
        response.setHeader("Cache-Control", "No-Cache");
        response.setDateHeader("Expires", 0);

        JsonGenerator jsonGenerator = objectMapper.getFactory().createGenerator(response.getOutputStream(), JsonEncoding.UTF8);
        objectMapper.writeValue(jsonGenerator, object);
    }


    /**
     * 获得Cookie值
     *
     * @param request
     * @param name
     * @return
     */
    public static String getCookieValue(HttpServletRequest request, String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }

        Cookie cookies[] = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                if (name.equals(cookies[i].getName())) {
                    return cookies[i].getValue();
                }
            }
        }

        return null;
    }

    /**
     * 设置cookie值
     *
     * @param response
     * @param name
     * @param value
     * @param maxAge
     */
    public static void setCookieValue(HttpServletResponse response, String name, String value, int maxAge) {
        if (StringUtils.isEmpty(name)) {
            return;
        }
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    /**
     * 设置cookie值
     *
     * @param response
     * @param name
     * @param value
     * @param maxAge
     */
    public static void setCookieValue(HttpServletResponse response, String name, String value, int maxAge, String domain, String path) {
        if (StringUtils.isEmpty(name)) {
            return;
        }
        if (StringUtils.isBlank(path)) {
            path = "/";
        }
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        if (StringUtils.isNotEmpty(domain)) {
            cookie.setDomain(domain);
        }
        cookie.setPath(path);
        response.addCookie(cookie);
    }

    /**
     * 清除Cookie值
     *
     * @param response
     * @param name
     */
    public static void removeCookieValue(HttpServletResponse response, String name) {
        if (StringUtils.isEmpty(name)) {
            return;
        }
        Cookie cookie = new Cookie(name, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    /**
     * 清除Cookie值
     *
     * @param response
     * @param name
     * @param domain
     * @param path
     */
    public static void removeCookieValue(HttpServletResponse response, String name, String domain, String path) {
        if (StringUtils.isEmpty(name)) {
            return;
        }
        if (StringUtils.isBlank(path)) {
            path = "/";
        }
        Cookie cookie = new Cookie(name, null);
        cookie.setMaxAge(0);
        if (StringUtils.isNotEmpty(domain)) {
            cookie.setDomain(domain);
        }
        cookie.setPath(path);
        response.addCookie(cookie);
    }

    /**
     * 获得请求字符串
     *
     * @param request
     * @return
     */
    public static String getParameterPairs(HttpServletRequest request) {
        Enumeration<String> paramNames = request.getParameterNames();
        StringBuffer paramString = new StringBuffer();
        if (paramNames != null) {
            while (paramNames.hasMoreElements()) {
                String param = paramNames.nextElement();
                paramString.append(paramString.length() > 0 ? "&" : "?").append(param).append("=").append(request.getParameter(param));
            }
        }
        return paramString.toString();
    }

    /**
     * 通过cookie获得url路径并删除cookie
     */
    public static String getCookieFileURL(HttpServletResponse response, HttpServletRequest request) {
        String tempUrl = "";
        Cookie cookies[] = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals("tempUrl")) {
                    tempUrl = cookies[i].getValue();
                    Cookie cookie = new Cookie(cookies[i].getName(), null);
                    cookie.setMaxAge(0);
                    cookie.setPath(request.getContextPath());
                    response.addCookie(cookie);
                    continue;
                }
            }
        }
        return tempUrl;
    }

    private HTTPUtils() {
    }
}
