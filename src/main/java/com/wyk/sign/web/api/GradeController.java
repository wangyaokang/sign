/**
 *
 */
package com.wyk.sign.web.api;

import com.wyk.sign.annotation.Checked;
import com.wyk.sign.annotation.Item;
import com.wyk.sign.model.Administrator;
import com.wyk.sign.model.Classes;
import com.wyk.sign.model.Student;
import com.wyk.sign.model.User;
import com.wyk.sign.service.ClassesService;
import com.wyk.sign.service.CourseService;
import com.wyk.sign.service.ElectiveService;
import com.wyk.sign.util.Constants;
import com.wyk.sign.web.api.param.Input;
import com.wyk.sign.web.api.param.Output;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 个人相关接口
 *
 * @author wyk
 */
@Controller("apiGrade")
@RequestMapping("/api/grade")
public class GradeController extends AbstractController {

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
        if (userType.equals(Constants.User.STUDENT)) {
            Student student = (Student) input.getCurrentUser();
            studentService.save(student);
            result.setData(student);
        } else if (userType.equals(Constants.User.TEACHER) || userType.equals(Constants.User.COUNSELLOR)) {
            Administrator admin = (Administrator) input.getCurrentUser();
            administratorService.save(admin);
            result.setData(admin);
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
        User user = input.getCurrentUser();
        Map<String, Object> param = new HashMap<>();
        param.put("adminId", user.getId());
        Classes classes = classesService.get(param);
        if(classes == null){
            return new Output(ERROR_NO_RECORD, "没有对应管理的班级！");
        }
        result.setStatus(SUCCESS);
        result.setMsg("获得班级成功！");
        result.setData(classes);
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
        User user = input.getCurrentUser();
        Map<String, Object> param = new HashMap<>();
        param.put("adminId", user.getId());
        List<Classes> classesList = electiveService.getClassesList(param);
        if(classesList.size() == 0){
            return new Output(ERROR_NO_RECORD, "没有对应的授课班级！");
        }
        result.setStatus(SUCCESS);
        result.setMsg("获得授课班级成功！");
        result.setData(classesList);
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
        List<Administrator> administratorList = administratorService.query();
        if(administratorList.size() == 0){
            return new Output(ERROR_NO_RECORD, "无对应的管理者");
        }
        result.setStatus(SUCCESS);
        result.setData(administratorList);
        result.setMsg("获取管理者成功！");
        return result;
    }
}
