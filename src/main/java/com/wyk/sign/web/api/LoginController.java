/**
 *
 */
package com.wyk.sign.web.api;

import java.util.HashMap;
import java.util.Map;

import com.wyk.sign.model.*;
import com.wyk.sign.service.ClassesService;
import com.wyk.sign.util.Constants;
import org.apache.commons.lang.StringUtils;
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
    ClassesService classesService;

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
        String token = input.getString("wxId");
        User user = getCurrentUserByToken(token);
        if (null == user) {
            return new Output(ERROR_UNKNOWN, "没有此用户！");
        }
        result.setStatus(SUCCESS);
        result.setMsg("登录成功");
        result.setData(toMap(user));
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
        Integer userType = input.getInteger("userType");

        if (userType == 1 || userType == 2) {
            Administrator admin = new Administrator();
            admin.setWxId(input.getString("wxId"));
            admin.setWxName(input.getString("wxName"));
            admin.setWxAvatarUrl(input.getString("wxAvatarUrl"));
            admin.setRealName(input.getString("realName"));
            admin.setUserType(userType);
            administratorService.save(admin);
            result.setData(toMap(admin));
        } else if (userType == 3) {
            Student student = new Student();
            student.setWxId(input.getString("wxId"));
            student.setWxName(input.getString("wxName"));
            student.setWxAvatarUrl(input.getString("wxAvatarUrl"));
            student.setRealName(input.getString("realName"));
            student.setUserType(userType);
            student.setSno(input.getString("sno"));
            if (!StringUtils.isEmpty(input.getString("classId"))) {
                Classes classes = new Classes();
                classes.setId(input.getLong("classId"));
                student.setClasses(classes);
            }
            studentService.save(student);
            result.setData(toMap(student));
        } else {
            result.setMsg("未设置【用户类型】，请重新设置！");
            result.setStatus(SUCCESS);
            return result;
        }

        result.setMsg("保存信息成功！");
        result.setStatus(SUCCESS);
        return result;
    }

    /**
     * 用于展示
     * @param user
     * @return
     */
    public Map<String, Object> toMap(User user){
        Map<String, Object> result = new HashMap<>();
        result.put("id", user.getId());
        result.put("wxId", user.getWxId());
        result.put("wxName", user.getWxName());
        result.put("wxAvatarUrl", user.getWxAvatarUrl());
        result.put("realName", user.getRealName());
        Integer userType = user.getUserType();
        result.put("userType", userType);
        if(Constants.User.STUDENT.equals(userType)){
            result.put("userTypeName", "学生");
            Student student = (Student) user;
            result.put("sno", student.getSno());
            if(StringUtils.isEmpty(student.getClasses().getName())) {
                Classes classes = classesService.get(student.getClasses().getId());
                if (classes != null) {
                    result.put("classes", classes);
                } else {
                    result.put("classes", null);
                }
            }else{
                result.put("classes", student.getClasses());
            }
        }else if(Constants.User.TEACHER.equals(userType)){
            result.put("userTypeName", "教师");
        }else if(Constants.User.COUNSELLOR.equals(userType)){
            result.put("userTypeName", "辅导员");
        }
        return result;
    }

}
