/**
 * 
 */
package com.wyk.sign.web;

import com.wyk.sign.model.Administrator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wyk.sign.model.User;
import com.wyk.framework.web.BaseController;

/**
 * @author wyk
 *
 */
@Controller
@RequestMapping("user/admin")
public class AdministratorController extends BaseController<Administrator> {

}
