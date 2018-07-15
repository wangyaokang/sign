/**
 * 
 */
package com.wyk.sign.web.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wyk.sign.web.api.param.Input;
import com.wyk.sign.web.api.param.Output;

/**
 * App相关接口
 * 
 * @author Dareen-Leo
 *
 */
@Controller("apiApp")
@RequestMapping("/api/app")
public class AppController extends AbstractController {

	/**
	 * 设备初始化
	 * <p>传入参数：</p>
	 * <pre>
	 * method:init
	 * </pre>
	 * @param input
	 * @return
	 */
	public Output init(Input input) {
		Output result = new Output();
		
		result.setStatus(SUCCESS);
		result.setMsg("success");
		return result;
	}
	
}
