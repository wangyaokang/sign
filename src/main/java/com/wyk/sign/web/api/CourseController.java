/**
 *
 */
package com.wyk.sign.web.api;

import com.wyk.sign.annotation.Checked;
import com.wyk.sign.annotation.Item;
import com.wyk.sign.model.Administrator;
import com.wyk.sign.model.Classes;
import com.wyk.sign.model.User;
import com.wyk.sign.service.ClassesService;
import com.wyk.sign.service.UserService;
import com.wyk.sign.web.api.param.Input;
import com.wyk.sign.web.api.param.Output;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * 课程相关接口
 *
 * @author wyk
 */
@Controller("apiCourse")
@RequestMapping("/api/course")
public class CourseController extends AbstractController {

}
