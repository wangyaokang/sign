/**
 * 
 */
package com.wyk.sign.web;

import com.wyk.framework.web.BaseController;
import com.wyk.sign.model.Student;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author wyk
 *
 */
@Controller
@RequestMapping("user/stu")
public class StudentController extends BaseController<Student> {

}
