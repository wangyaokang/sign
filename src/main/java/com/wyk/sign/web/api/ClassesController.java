/**
 *
 */
package com.wyk.sign.web.api;

import com.wyk.sign.annotation.Checked;
import com.wyk.sign.annotation.Item;
import com.wyk.sign.model.Administrator;
import com.wyk.sign.model.Classes;
import com.wyk.sign.model.Student;
import com.wyk.sign.service.ClassesService;
import com.wyk.sign.service.ElectiveService;
import com.wyk.sign.web.api.param.Input;
import com.wyk.sign.web.api.param.Output;
import org.apache.commons.lang.StringUtils;
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
    ClassesService classesService;

    @Autowired
    ElectiveService electiveService;

    /**
     * 新增
     * <p>传入参数</p>
     * <pre>
     *     method: insert
     *     token: 当前wxId
     *     params: {name："英语11102班"，adminId:"2"}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.ADMIN)
    public Output insert(Input input){
        Output result = new Output();
        Classes classes = new Classes();
        classes.setName(input.getString("name"));
        if(StringUtils.isNotEmpty(input.getString("adminId"))){
            Administrator admin = new Administrator();
            admin.setId(input.getLong("adminId"));
            classes.setAdmin(admin);
        }
        classesService.save(classes);
        result.setData(classes);
        result.setMsg("班级信息添加成功！");
        return result;
    }

    /**
     * 获取班级信息
     * <p>传入参数</p>
     * <pre>
     *     method: info
     *     token: 当前wxId
     *     params: {id：1}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.TYPE)
    public Output info(Input input) {
        Output result = new Output();
        Classes classes = classesService.get(input.getLong("id"));
        if(null == classes){
            return new Output(ERROR_NO_RECORD, "没有对应的班级");
        }
        result.setStatus(SUCCESS);
        result.setMsg("查询班级信息成功！");
        result.setData(classes);
        return result;
    }

    /**
     * 修改班级信息
     * <p>传入参数：</p>
     * <pre>
     *      method:modify
     *      token: wxopenid, 微信wxid
     *      params: {id: 1, name: 英语110班, adminId: 2}
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
        Administrator admin = administratorService.getUserByToken(input.getToken());
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
     *     method: getClassStudentList
     *     token: wxopenid, 微信wxid
     *     params: {classId: 1}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.TYPE)
    public Output getClassStudentList(Input input) {
        Output result = new Output();
        Map<String, Object> params = input.getParams();
        List<Student> studentList = studentService.query(params);
        params.put("studentList", studentList);
        result.setData(studentList);
        result.setStatus(SUCCESS);
        result.setMsg("修改班级信息成功！");
        return result;
    }

    /**
     * 获取班级授课教师
     * <p>传入参数</p>
     * <pre>
     *     method: getClassTeacherList
     *     token: wxopenid, 微信wxid
     *     params: {classId: 1}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.TYPE)
    public Output getClassTeacherList(Input input) {
        Output result = new Output();
        Map<String, Object> params = input.getParams();
        List<Administrator> teacherList = electiveService.getTeacherList(params);
        params.put("teacherList", teacherList);
        result.setData(teacherList);
        result.setStatus(SUCCESS);
        result.setMsg("修改班级信息成功！");
        return result;
    }
}
