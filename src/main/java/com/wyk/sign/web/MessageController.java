/**
 * 
 */
package com.wyk.sign.web;

import com.wyk.framework.web.BaseController;
import com.wyk.sign.model.TbMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author wyk
 *
 */
@Controller
@RequestMapping("web/message")
public class MessageController extends BaseController<TbMessage> {

}
