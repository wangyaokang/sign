/**
 *
 */
package com.wyk.sign.web.api;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wyk.framework.utils.DateUtil;
import com.wyk.sign.annotation.Checked;
import com.wyk.sign.annotation.Item;
import com.wyk.sign.model.SignInfo;
import com.wyk.sign.service.AdministratorService;
import com.wyk.sign.util.Constants;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.wyk.sign.exception.SignException;
import com.wyk.sign.model.User;
import com.wyk.sign.service.StudentService;
import com.wyk.sign.web.api.param.Input;
import com.wyk.sign.web.api.param.Output;
import com.wyk.framework.utils.RandomUtil;
import com.wyk.framework.web.WebxController;
import com.alibaba.fastjson.JSONObject;

/**
 * 基础Controller
 *
 * @author wyk
 */
public abstract class AbstractController implements WebxController {

    protected transient Logger logger = LogManager.getLogger(this.getClass());

    @Value("#{properties['web.upload.path']}")
    protected String uploadPath;

    @Value("#{properties['web.context.path']}")
    protected String contextPath;

    @Autowired
    protected ServletContext context;

    @Autowired
    StudentService studentService;

    @Autowired
    AdministratorService administratorService;

    /**
     * Dispatch
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody Output dispatch(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setCharacterEncoding("UTF-8");
            // 设置Content-Type字段值
            response.setContentType("application/json;charset=UTF-8");
            // 读取请求参数
            Output result = doService(getInput(request));
            logger.info("执行了的接口, 返回数据: " + JSONObject.toJSONString(result));
            return result;
        } catch (SignException ae) {
            return new Output(ae.getStatus(), ae.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            return new Output(ERROR_UNKNOWN, ex.getMessage());
        }
    }

    /**
     * 处理业务逻辑，使用反射执行<code>method</code>定义的方法，可重载
     *
     * @param input
     * @return
     */
    protected Output doService(Input input) {
        String method = input.getMethod();
        try {
            Method md = this.getClass().getDeclaredMethod(method, Input.class);
            // 如果有@Checked注解的需要做信息检测判断
            Annotation annotation = md.getAnnotation(Checked.class);
            if (annotation != null) {
                Item itemValue = ((Checked) annotation).value();
                User currentUser = input.getCurrentUser() == null ? getCurrentUserByToken(input.getToken()) : input.getCurrentUser();
                if (Item.TYPE.equals(itemValue)) {
                    if (currentUser == null) {
                        return new Output(ERROR_UNKNOWN, "未选择【用户类型】，请先完善个人信息！");
                    }
                }

                if (Item.STU.equals(itemValue)) {
                    if (null == currentUser || null == currentUser.getUserType()) {
                        return new Output(ERROR_UNKNOWN, "未选择【用户类型】，请先完善个人信息！");
                    }

                    if (null == currentUser.getClass()) {
                        return new Output(ERROR_UNKNOWN, "未选择【班级】，请先完善个人信息！");
                    }

                    if (!Constants.User.STUDENT.equals(currentUser.getUserType())) {
                        return new Output(ERROR_UNKNOWN, "用户类型非学生！");
                    }
                }

                if (Item.ADMIN.equals(itemValue)) {
                    if (null == currentUser || null == currentUser.getUserType()) {
                        return new Output(ERROR_UNKNOWN, "未选择【用户类型】，请先完善个人信息！");
                    }

                    if (!(Constants.User.TEACHER.equals(currentUser.getUserType()) || Constants.User.COUNSELLOR.equals(currentUser.getUserType()))) {
                        return new Output(ERROR_UNKNOWN, "非管理员，无权限操作！");
                    }
                }

                logger.info("验证的对象userType为：{}", itemValue.toString());
                input.setCurrentUser(currentUser);
            }
            return (Output) md.invoke(this, input);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            e.printStackTrace();
        }
        return new Output(ERROR_NO_RECORD, "找不到对应的方法");
    }

    /**
     * 获得输入参数
     *
     * @param request
     * @return
     * @throws IOException
     */
    protected Input getInput(HttpServletRequest request) throws IOException {
        // 读取JSON字符串
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        String jsonStr = sb.toString();
        if (StringUtils.isEmpty(jsonStr)) {
            jsonStr = request.getParameter("data");
        }
        logger.info("接口请求数据: " + jsonStr);
        if (StringUtils.isEmpty(jsonStr)) {
            throw new SignException(ERROR_NO_RECORD, "请求参数为空");
        }
        return JSONObject.parseObject(jsonStr, Input.class);
    }

    /**
     * 获得当前用户信息
     *
     * @param token
     * @return
     */
    protected User getCurrentUserByToken(String token) {
        // 当缓存中存在对象时直接取缓存中的数据
        if(administratorService.hasCacheAdministrator(token)){
            return administratorService.getUserByToken(token);
        }

        if(studentService.hasCacheStudent(token)){
            return studentService.getUserByToken(token);
        }

        // 当缓存中无值时，查询数据库
        User user = administratorService.getUserByToken(token);
        if (null == user) {
            user = studentService.getUserByToken(token);
        }
        return user;
    }

    /**
     * 上传文件
     *
     * @param file   文件
     * @param fileId   文件标识
     * @return
     */
    protected String uploadFile(MultipartFile file, String fileId) {
        String savePath = context.getRealPath("/") + "/" + uploadPath;
        if (file != null && StringUtils.isNotEmpty(file.getOriginalFilename())) {
            try {
                String uploadFileName = file.getOriginalFilename();
                if (uploadFileName.lastIndexOf('.') < 0) {
                    return null;
                }
                String suffix = uploadFileName.substring(uploadFileName.lastIndexOf('.'));
                String random = RandomUtil.getString(6);
                String fileName = fileId + "_" + random + suffix;
                FileUtils.writeByteArrayToFile(new File(savePath + fileName), file.getBytes());
                return (uploadPath + fileName);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 文件下载
     *
     * @param filename 文件名
     * @return
     */
    public ResponseEntity<byte[]> downloadFile(String filename) {
        String path = context.getRealPath("/") + filename;
        File file = new File(path);
        HttpHeaders headers = new HttpHeaders();
        try {
            String downloadFielName = new String(filename.getBytes("UTF-8"), "iso-8859-1");
            headers.setContentDispositionFormData("attachment", downloadFielName);
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 泛型列表
     *
     * @param list
     * @return
     */
    protected <T> List<Map<String, Object>> toArray(Collection<T> list) {
        return toArray(list, null);
    }

    /**
     * 泛型列表
     *
     * @param list
     * @return
     */
    protected <T> List<Map<String, Object>> toArray(Collection<T> list, User user) {
        if (list == null || list.isEmpty()) {
            return null;
        }

        List<Map<String, Object>> result = new ArrayList<>();
        try {
            // 反射执行 toMap 方法，获得数据
            Class<T> typeClass = (Class<T>) list.iterator().next().getClass();
            Method method = null;
            if(user == null) {
                try {
                    method = this.getClass().getDeclaredMethod("toMap", typeClass);
                } catch (NoSuchMethodException ex) {
                    method = this.getClass().getSuperclass().getDeclaredMethod("toMap", typeClass);
                }
                if (method != null) {
                    method.setAccessible(true); // 不让Java对方法进行检查, 可执行私有方法
                    for (T entity : list) {
                        result.add((Map<String, Object>) method.invoke(this, entity));
                    }
                }
            }else{
                try {
                    method = this.getClass().getDeclaredMethod("toMap", typeClass, User.class);
                } catch (NoSuchMethodException ex) {
                    method = this.getClass().getSuperclass().getDeclaredMethod("toMap", typeClass, User.class);
                }
                if (method != null) {
                    method.setAccessible(true); // 不让Java对方法进行检查, 可执行私有方法
                    for (T entity : list) {
                        result.add((Map<String, Object>) method.invoke(this, entity, user));
                    }
                }
            }
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }

}
