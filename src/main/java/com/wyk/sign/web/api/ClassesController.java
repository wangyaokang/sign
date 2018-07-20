/**
 *
 */
package com.wyk.sign.web.api;

import com.wyk.sign.annotation.Checked;
import com.wyk.sign.annotation.Item;
import com.wyk.sign.model.Administrator;
import com.wyk.sign.model.Student;
import com.wyk.sign.service.ClassesService;
import com.wyk.sign.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wyk.sign.service.UserService;
import com.wyk.sign.web.api.param.Input;
import com.wyk.sign.web.api.param.Output;

/**
 * 个人相关接口
 *
 * @author wyk
 */
@Controller("apiUser")
@RequestMapping("/api/user")
public class ClassesController extends AbstractController {

    @Autowired
    UserService userService;

    @Autowired
    ClassesService classesService;

    /**
     * 获得当前用户的个人信息
     * <p>传入参数：</p>
     * <pre>
     * method:info
     * token:当前TOKEN信息
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.TYPE)
    public Output info(Input input) {
        Output result = new Output();
        result.setStatus(SUCCESS);
        result.setMsg("获得个人信息");
        result.setData(input.getCurrentUser());
        return result;
    }

    /**
     * 修改个人信息
     * <p>传入参数：</p>
     * <pre>
     * method:modify
     * token:当前TOKEN信息
     * params:{当前所有信息JSON格式}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.TYPE)
    public Output modify(Input input) {
        Output result = new Output();
        String userType = input.getString("userType");
        if (userType.equals(Constants.STU)) {
            Student student = (Student) input.getCurrentUser();
            userService.saveUser(student);
            result.setData(student);
        } else if (userType.equals(Constants.ADMIN)) {
            Administrator admin = (Administrator) input.getCurrentUser();
            userService.saveUser(admin);
            result.setData(admin);
        }
        result.setStatus(SUCCESS);
        result.setMsg("个人信息修改成功");
        return result;
    }

}
