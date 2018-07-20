/**
 * 
 */
package com.wyk.sign.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wyk.sign.model.UserToken;
import com.wyk.framework.web.BaseController;

/**
 * @author wyk
 *
 */
@Controller
@RequestMapping("/userToken")
public class UserTokenController extends BaseController<UserToken> {

}
