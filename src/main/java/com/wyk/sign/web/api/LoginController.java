/**
 *
 */
package com.wyk.sign.web.api;

import java.util.Date;

import com.wyk.sign.model.*;
import com.wyk.sign.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wyk.sign.web.api.param.Input;
import com.wyk.sign.web.api.param.Output;

/**
 * 登录注册相关接口
 *
 * @author wyk
 */
@Controller("apiLogin")
@RequestMapping("/api/login")
public class LoginController extends AbstractController {

    @Autowired
    UserService userService;

    /**
     * 登录
     * <p>传入参数：</p>
     * <pre>
     * method:login
     * params:{"wxId":"微信ID"}
     * </pre>
     *
     * @param input
     * @return
     */
    public Output login(Input input) {
        Output result = new Output();
        String token = input.getParams().get("wxId").toString();
        User user = userService.getUserByToken(token);
        // 当用户未完善个人信息时，已匿名方式登录。
        if (null == user) {
            user = User.Anonymous;
        }
        UserToken userToken = new UserToken();
        userToken.setUser(user);
        userToken.setToken(token);
        userToken.setLastTime(new Date());

        result.setStatus(SUCCESS);
        result.setMsg("登录成功");
        result.setData(userToken);
        return result;
    }


    /**
     * 注册个人信息
     * <p>传入参数</p>
     * <pre>
     *     method: register
     *     params: {wxId: 01,
     *     			wxName: darren,
     *     			wxAvatarUrl: http://qq.com/user/iul.pgn,
     *     			userType: 1,
     *     			realName: 张三,
     *     			sno: 20188888, // 可以为空
     *     			classId:01 // 可以为空
     *                    }
     * </pre>
     *
     * @param input
     * @return
     */
    public Output register(Input input) {
        Output result = new Output();
        UserToken userToken = new UserToken();
        Integer userType = input.getInteger("userType");
        if (userType == 1) {
            Administrator admin = new Administrator();
            admin.setWxId(input.getString("wxId"));
            admin.setWxName(input.getString("wxName"));
            admin.setWxAvatarUrl(input.getString("AvatarUrl"));
            admin.setRealName(input.getString("realName"));
            admin.setUserType(userType);
            userService.saveUser(admin);
            userToken.setUser(admin);
        } else if (userType == 2) {
            Student student = new Student();
            student.setWxId(input.getString("wxId"));
            student.setWxName(input.getString("wxName"));
            student.setWxAvatarUrl(input.getString("AvatarUrl"));
            student.setRealName(input.getString("realName"));
            student.setUserType(userType);
            student.setSno(input.getString("sno"));
            if (input.getLong("classId") != null) {
                Classes classes = new Classes();
                classes.setId(input.getLong("classId"));
                student.setClasses(classes);
            }
            userService.saveUser(student);
            userToken.setUser(student);
        } else {
            result.setMsg("未设置【用户类型】，请重新设置！");
            result.setStatus(SUCCESS);
            return result;
        }

        userToken.setToken(input.getString("wxId"));
        result.setMsg("保存信息成功！");
        result.setData(userToken);
        result.setStatus(SUCCESS);
        return result;
    }

}
