/**
 *
 */
package com.wyk.sign.web.api;

import com.wyk.sign.annotation.Checked;
import com.wyk.sign.annotation.Item;
import com.wyk.sign.model.*;
import com.wyk.sign.service.ClassesService;
import com.wyk.sign.service.CourseService;
import com.wyk.sign.service.ElectiveService;
import com.wyk.sign.util.Constants;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wyk.sign.web.api.param.Input;
import com.wyk.sign.web.api.param.Output;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 个人相关接口
 *
 * @author wyk
 */
@Controller("apiUser")
@RequestMapping("/api/user")
public class UserController extends AbstractController {

    @Autowired
    ClassesService classesService;

    @Autowired
    CourseService courseService;

    @Autowired
    ElectiveService electiveService;

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
        result.setMsg("获得个人信息成功！");
        result.setData(input.getCurrentTbUser());
        return result;
    }

    /**
     * 修改个人信息
     * <p>传入参数：</p>
     * <pre>
     * method:modify
     * token:openid
     * params: {需要修改的参数}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.TYPE)
    public Output modify(Input input) {
        Output result = new Output();
        TbUser tbUser = input.getCurrentTbUser();
        if (Constants.User.STUDENT.equals(tbUser.getUserType())) {
            TbStudent tbStudent = (TbStudent) input.getCurrentTbUser();
            if (StringUtils.isNotEmpty(input.getString("realName"))) {
                tbStudent.setRealName(input.getString("realName"));
            }
            if (StringUtils.isNotEmpty(input.getString("classId"))) {
                TbClass TbClass = classesService.get(input.getLong("classId"));
                tbStudent.setTbClass(TbClass);
            }
            if (StringUtils.isNotEmpty(input.getString("sno"))) {
                tbStudent.setSno(input.getString("sno"));
            }
            studentService.save(tbStudent);
            result.setData(LoginController.toMap(tbStudent));
        } else if (Constants.User.TEACHER.equals(tbUser.getUserType()) || Constants.User.COUNSELLOR.equals(tbUser.getUserType())) {
            TbAdmin admin = (TbAdmin) input.getCurrentTbUser();
            if (StringUtils.isNotEmpty(input.getString("realName"))) {
                admin.setRealName(input.getString("realName"));
            }
            administratorService.save(admin);
            result.setData(LoginController.toMap(admin));
        }
        result.setStatus(SUCCESS);
        result.setMsg("个人信息修改成功");
        return result;
    }

    /**
     * 获得管理者管理的班级
     * <p>传入参数：</p>
     * <pre>
     * method:getMyAdminClasses
     * token:当前TOKEN信息
     * </pre>
     * @param input
     * @return
     */
    @Checked(Item.ADMIN)
    public Output getMyAdminClasses(Input input) {
        Output result = new Output();
        TbUser tbUser = input.getCurrentTbUser();
        Map<String, Object> param = new HashMap<>();
        param.put("adminId", tbUser.getId());
        TbClass TbClass = classesService.get(param);
        if(TbClass == null){
            return new Output(ERROR_NO_RECORD, "没有对应管理的班级！");
        }
        result.setStatus(SUCCESS);
        result.setMsg("获得班级成功！");
        result.setData(TbClass);
        return result;
    }

    /**
     * 获得授课的班级
     * <p>传入参数：</p>
     * <pre>
     * method:getMyTeachClasses
     * token:当前TOKEN信息
     * </pre>
     * @param input
     * @return
     */
    @Checked(Item.ADMIN)
    public Output getMyTeachClasses(Input input) {
        Output result = new Output();
        TbUser tbUser = input.getCurrentTbUser();
        Map<String, Object> param = new HashMap<>();
        param.put("adminId", tbUser.getId());
        List<TbClass> TbClassList = electiveService.getClassesList(param);
        if(TbClassList.size() == 0){
            return new Output(ERROR_NO_RECORD, "没有对应的授课班级！");
        }
        result.setStatus(SUCCESS);
        result.setMsg("获得授课班级成功！");
        result.setData(TbClassList);
        return result;
    }

    /**
     * 获取管理者
     * <p>传入参数</p>
     * <pre>
     *     token: wxId
     *     method: getAdminList
     * </pre>
     * @param input
     * @return
     */
    @Checked(Item.ADMIN)
    public Output getAdminList(Input input){
        Output result = new Output();
        List<TbAdmin> TbAdminList = administratorService.query();
        if(TbAdminList.size() == 0){
            return new Output(ERROR_NO_RECORD, "无对应的管理者");
        }
        result.setStatus(SUCCESS);
        result.setData(TbAdminList);
        result.setMsg("获取管理者成功！");
        return result;
    }

    /**
     * 缓存刷新
     * <p>传入参数</p>
     * <pre>
     *     token: openid
     *     method: flushCache
     * </pre>
     * @param input
     * @return
     */
    @Checked(Item.TYPE)
    public Output flushCache(Input input){
        Output result = new Output();
        administratorService.flushCache();
        result.setStatus(SUCCESS);
        result.setMsg("缓存刷新成功！");
        return result;
    }
}
