/**
 * 
 */
package com.wyk.sign.web;

import com.wyk.framework.web.BaseController;
import com.wyk.sign.model.TbTaskInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author wyk
 *
 */
@Controller
@RequestMapping("web/taskInfo")
public class TaskInfoController extends BaseController<TbTaskInfo> {

}
