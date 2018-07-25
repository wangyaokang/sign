/**
 * 
 */
package com.wyk.sign.web.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wyk.sign.annotation.Checked;
import com.wyk.sign.annotation.Item;
import com.wyk.sign.service.AdministratorService;
import com.wyk.sign.util.Constants;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.wyk.sign.exception.SignException;
import com.wyk.sign.model.User;
import com.wyk.sign.service.StudentService;
import com.wyk.sign.web.api.param.Input;
import com.wyk.sign.web.api.param.Output;
import com.wyk.framework.util.RandomUtils;
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
	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Output dispatch(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setCharacterEncoding("UTF-8");
	 		// response.setContentType("text/html;charset=UTF-8");
			// 设置Content-Type字段值
			response.setContentType("application/json;charset=UTF-8"); // 设置Content-Type字段值
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
				if(Item.TYPE.equals(itemValue)){
					if(currentUser == null){
						return new Output(ERROR_UNKNOWN, "未选择【用户类型】，请先完善个人信息！");
					}
					logger.info("验证的对象userType为：{}", itemValue.toString());
				}

				if(Item.STU.equals(itemValue)){
					if(null == currentUser || null == currentUser.getUserType()){
						return new Output(ERROR_UNKNOWN, "未选择【用户类型】，请先完善个人信息！");
					}

					if(!Constants.User.STUDENT.equals(currentUser.getUserType())){
						return new Output(ERROR_UNKNOWN, "用户类型非学生！");
					}
				}

				if(Item.ADMIN.equals(itemValue)){
					if(null == currentUser || null == currentUser.getUserType()){
						return new Output(ERROR_UNKNOWN, "未选择【用户类型】，请先完善个人信息！");
					}

					if(!(Constants.User.TEACHER.equals(currentUser.getUserType()) || Constants.User.COUNSELLOR.equals(currentUser.getUserType()))){
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
		// request.setCharacterEncoding("UTF-8"); // 设置处理请求参数的编码格式
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
		User user = administratorService.getUserByToken(token);
		if(null == user){
			user = studentService.getUserByToken(token);
		}
		return user;
	}

	/**
	 * 获得当前用户信息
	 * 
	 * @param request
	 * @return
	 */
	protected User getCurrentUser(HttpServletRequest request) {
		try {
			Input input = getInput(request);
			String token = input.getToken();
			if (StringUtils.isNotEmpty(token)) {
				return getCurrentUserByToken(token);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获得图片完整的URL地址
	 * 
	 * @param imagePath
	 * @return
	 */
	protected String getImageUrlPath(String imagePath) {
		if (StringUtils.isEmpty(imagePath)) {
			return "";
		}
		if (imagePath.startsWith("http://") || imagePath.startsWith("https://")) {
			return imagePath;
		} else {
			return contextPath.concat(imagePath);
		}
	}

	/**
	 * 上传文件
	 * 
	 * @param file
	 *            上传文件
	 */
	protected String uploadFile(MultipartFile file) {
		String savePath = context.getRealPath("/") + "/" + uploadPath;
		if (file != null && StringUtils.isNotEmpty(file.getOriginalFilename())) {
			try {
				String uploadFileName = file.getOriginalFilename();
				if (uploadFileName.lastIndexOf('.') < 0) {
					return null;
				}
				String suffix = uploadFileName.substring(uploadFileName.lastIndexOf('.'));
				String imgId = String.valueOf(System.currentTimeMillis());
				String random = RandomUtils.getString(6);
				String fileName = imgId + "_" + random + suffix;
				FileUtils.writeByteArrayToFile(new File(savePath + fileName), file.getBytes());
				return (uploadPath + fileName);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 上传文件
	 * 
	 * @param uploadFileName
	 *            上传文件名
	 * @param file
	 *            文件二进制字符流
	 */
	protected String uploadImage(String uploadFileName, byte[] file) {
		String savePath = context.getRealPath("/") + "/" + uploadPath;
		if (file != null) {
			try {
				String suffix = uploadFileName.substring(uploadFileName.lastIndexOf('.'));
				String imgId = String.valueOf(System.currentTimeMillis());
				String random = RandomUtils.getString(6);
				String fileName = imgId + "_" + random + suffix;
				FileUtils.writeByteArrayToFile(new File(savePath + fileName), file);
				return (uploadPath + fileName);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 上传文件
	 * 
	 * @param file
	 *            BASE64编码
	 * @param fileType
	 *            图片格式
	 * @return
	 */
	protected String uploadImage(String file, String fileType) {
		if (StringUtils.isEmpty(file)) {
			throw new SignException(ERROR_NO_RECORD, "没有上传文件");
		}

		if (StringUtils.isEmpty(fileType)) {
			throw new SignException(ERROR_NO_RECORD, "没有注明文件类型");
		}

		// 去掉前端Canvas的头
		if (file.startsWith("data:image/png;base64,")) { // PNG
			file = file.substring("data:image/png;base64,".length());
		} else if (file.startsWith("data:image/gif;base64,")) { // GIF
			file = file.substring("data:image/gif;base64,".length());
		} else if (file.startsWith("data:image/jpeg;base64,")) { // JPEG
			file = file.substring("data:image/jpeg;base64,".length());
		} else if (file.startsWith("data:image/jpg;base64,")) { // JPG
			file = file.substring("data:image/jpg;base64,".length());
		}
		// BASE64解析上传
		byte[] photoBytes = Base64.decodeBase64(file);
		return uploadImage(System.currentTimeMillis() + (fileType.startsWith(".") ? fileType : "." + fileType), photoBytes);
	}
}
