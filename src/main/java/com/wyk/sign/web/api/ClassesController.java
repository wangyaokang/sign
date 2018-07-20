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
 * 班级相关接口
 *
 * @author wyk
 */
@Controller("apiClasses")
@RequestMapping("/api/classes")
public class ClassesController extends AbstractController {

    @Autowired
    UserService userService;

    @Autowired
    ClassesService classesService;

    /**
     * 修改班级信息
     * <p>传入参数：</p>
     * <pre>
     *      method:modify
     *      token: wxopenid, 微信wxid
     *      params: {id: 1, name: 英语110班, adminId: 01}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.ADMIN)
    public Output modify(Input input) {
        Output result = new Output();
        Classes classes = classesService.get(input.getLong("id"));
        classes.setName(input.getString("name"));
        Administrator admin = (Administrator) userService.getUserByToken(input.getToken());
        classes.setAdmin(admin);
        classesService.save(classes);
        result.setStatus(SUCCESS);
        result.setMsg("修改班级信息成功！");
        result.setData(classes);
        return result;
    }

    /**
     * 获取班级成员信息
     * <p>传入参数</p>
     * <pre>
     *     method:modify
     *     token: wxopenid, 微信wxid
     *     params: {classId: 1}
     * </pre>
     * @param input
     * @return
     */
    @Checked(Item.TYPE)
    public Output getClassUsers(Input input) {
        Output result = new Output();
        Map<String, Object> params = input.getParams();
        List<User> studentList = userService.query(params);
        result.setData(studentList);
        result.setStatus(SUCCESS);
        result.setMsg("修改班级信息成功！");
        return result;
    }

}
