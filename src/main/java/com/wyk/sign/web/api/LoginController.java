/**
 * 
 */
package com.wyk.sign.web.api;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wyk.sign.model.User;
import com.wyk.sign.model.UserToken;
import com.wyk.sign.web.api.param.Input;
import com.wyk.sign.web.api.param.Output;
import com.wyk.framework.util.MD5Util;
import com.wyk.framework.util.RandomUtils;

/**
 * 登录注册相关接口
 * 
 * @author Dareen-Leo
 *
 */
@Controller("apiLogin")
@RequestMapping("/api/login")
public class LoginController extends AbstractController {
	
	/**
	 * 登录
	 * <p>传入参数：</p>
	 * <pre>
	 * method:login
	 * params:{"mobile":"手机号","password":"密码"}
	 * </pre>
	 * @param input
	 * @return
	 */
	public Output login(Input input) {
		Output result = new Output();
		User user = userService.get(input.getParams());
		if(null == user || MD5Util.stringToMD5(input.getString("password")).equals(input.getString("password"))){
			result.setStatus(ERROR_NO_RECORD);
			result.setMsg("用户未注册或密码输入错误！");
			return result;
		}
		UserToken userToken = new UserToken();
		userToken.setUser(user);
		userToken.setToken(RandomUtils.getString(32));
		userToken.setLastTime(new Date());
		
		result.setStatus(SUCCESS);
		result.setMsg("登录成功");
		result.setData(userToken);
		return result;
	}
	
	/**
	 * 登出
	 * <p>传入参数：</p>
	 * <pre>
	 * method:login
	 * token:当前用户TOKEN
	 * </pre>
	 * @param input
	 * @return
	 */
	public Output logout(Input input) {
		Output result = new Output();
		
		result.setStatus(SUCCESS);
		result.setMsg("登出成功");
		return result;
	}
	
	/**
	 * 发送验证码
	 * <p>传入参数：</p>
	 * <pre>
	 * method:sendCode
	 * params:{"mobile":"13800138000"}
	 * </pre>
	 * @param input
	 * @return
	 */
	public Output sendCode(Input input) {
		Output result = new Output();
		
		result.setStatus(SUCCESS);
		result.setMsg("发送验证码成功");
		return result;
	}
	
	/**
	 * 校验验证码，完成注册
	 * <p>传入参数：</p>
	 * <pre>
	 * method:register
	 * params:{"mobile":"13800138000","password":"密码"}
	 * </pre>
	 * @param input
	 * @return
	 */
	public Output register(Input input) {
		Output result = new Output();
		
		result.setStatus(SUCCESS);
		result.setMsg("注册成功");
		return result;
	}
	
	/**
	 * 设置密码
	 * <p>传入参数：</p>
	 * <pre>
	 * method:setPassword
	 * params:{"mobile":"13800138000","password":"密码"}
	 * </pre>
	 * @param input
	 * @return
	 */
	public Output setPassword(Input input) {
		Output result = new Output();
		
		result.setStatus(SUCCESS);
		result.setMsg("密码设置成功");
		return result;
	}
	
	/**
	 * 重置密码
	 * <p>传入参数：</p>
	 * <pre>
	 * method:resetPassword
	 * params:{"mobile":"13800138000","oldPassword":"原有密码","password":"新密码"}
	 * </pre>
	 * @param input
	 * @return
	 */
	public Output resetPassword(Input input) {
		Output result = new Output();
		
		result.setStatus(SUCCESS);
		result.setMsg("密码重置成功");
		return result;
	}
}
