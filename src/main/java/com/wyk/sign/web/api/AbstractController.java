/**
 *
 */
package com.wyk.sign.web.api;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wyk.framework.utils.FileUtil;
import com.wyk.sign.model.TbUser;
import com.wyk.sign.service.*;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.wyk.sign.exception.SignException;
import com.wyk.sign.web.api.param.Input;
import com.wyk.sign.web.api.param.Output;
import com.wyk.framework.web.WebxController;
import com.alibaba.fastjson.JSONObject;

/**
 * 基础Controller
 *
 * @author wyk
 */
public abstract class AbstractController implements WebxController {

    protected transient Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    protected TbStudentService tbStudentService;
    @Autowired
    protected TbAdminService tbAdminService;
    @Autowired
    protected TbClassService tbClassService;
    @Autowired
    protected TbElectiveService tbElectiveService;
    @Autowired
    protected TbCourseService tbCourseService;
    @Autowired
    protected TbGradeService tbGradeService;
    @Autowired
    protected TbSignService tbSignService;
    @Autowired
    protected TbTaskService tbTaskService;
    @Autowired
    protected TbSignInfoService tbSignInfoService;
    @Autowired
    protected TbTaskInfoService tbTaskInfoService;

    @Value("#{properties['web.upload.path']}")
    protected String uploadPath;
    @Value("#{properties['web.context.path']}")
    protected String contextPath;
    @Autowired
    protected ServletContext context;

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
            return Output.getFail(ae.getStatus(), ae.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            return Output.getFail(ERROR_UNKNOWN, ex.getMessage());
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
            jsonStr = request.getParameter(DATA);
        }
        logger.info("接口请求数据: " + jsonStr);
        if (StringUtils.isEmpty(jsonStr)) {
            throw new SignException(ERROR_NO_RECORD, "请求参数为空");
        }
        return JSONObject.parseObject(jsonStr, Input.class);
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
                String fileName = FileUtil.createOrRenameFile(savePath, fileId, suffix);
                FileUtils.writeByteArrayToFile(new File(savePath + fileName), file.getBytes());
                return (uploadPath + fileName);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 作业删除
     * <p>传入参数</p>
     *
     * <pre>
     *     filename : attachment/file/taskInfo1/201106214_tREb2q.exe
     * </pre>
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "deleteUploadFile")
    public @ResponseBody Output deleteFileUrl(@RequestParam("filename") String filename){
        Output result = new Output();
        try {
            filename = new String(filename.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return new Output(ERROR_NO_RECORD, "中文转码失败！");
        }
        String path = context.getRealPath("/") + filename;
        logger.info("删除文件为：{}", path);
        FileUtil.deleteFile(path);

        result.setStatus(SUCCESS);
        result.setMsg("文件删除成功！");
        return result;
    }

    /**
     * 获得文件完整的URL地址
     *
     * @param filePath
     * @return
     */
    protected String getFileUrlPath(String filePath) {
        if (StringUtils.isEmpty(filePath)) {
            return "";
        }
        if (filePath.startsWith("http://") || filePath.startsWith("https://")) {
            return filePath;
        } else {
            return contextPath.concat(filePath);
        }
    }

    /**
     * 文件下载
     *
     * @param filename 文件名
     * @return
     */
    @RequestMapping(value = "downloadFile")
    public ResponseEntity<byte[]> downloadFile(@RequestParam("filename") String filename) {
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
    protected <T> List<Map<String, Object>> toArray(Collection<T> list, TbUser tbUser) {
        if (list == null || list.isEmpty()) {
            return null;
        }

        List<Map<String, Object>> result = new ArrayList<>();
        try {
            // 反射执行 toMap 方法，获得数据
            Class<T> typeClass = (Class<T>) list.iterator().next().getClass();
            Method method = null;
            if(tbUser == null) {
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
                    method = this.getClass().getDeclaredMethod("toMap", typeClass, TbUser.class);
                } catch (NoSuchMethodException ex) {
                    method = this.getClass().getSuperclass().getDeclaredMethod("toMap", typeClass, TbUser.class);
                }
                if (method != null) {
                    method.setAccessible(true); // 不让Java对方法进行检查, 可执行私有方法
                    for (T entity : list) {
                        result.add((Map<String, Object>) method.invoke(this, entity, tbUser));
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
