/**
 * 
 */
package com.wyk.sign.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wyk.sign.model.User;
import com.wyk.framework.web.BaseController;

/**
 * @author Dareen-Leo
 *
 */
@Controller
@RequestMapping("user")
public class UserController extends BaseController<User> {

}
