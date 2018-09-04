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
import com.wyk.sign.web.api.param.Input;
import com.wyk.sign.web.api.param.Output;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 授课相关接口
 *
 * @author wyk
 */
@Controller("apiElective")
@RequestMapping("/api/elective")
public class ElectiveController extends AbstractController {

    @Autowired
    ElectiveService electiveService;

    @Autowired
    ClassesService classesService;

    @Autowired
    CourseService courseService;

    /**
     * 新增授课信息
     * <p>传入参数</p>
     * <pre>
     *     method: insert
     *     token: 当前wxId
     *     params: {adminId: "1", classId："2"，courseId:"2"}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.ADMIN)
    public Output insert(Input input) {
        Output result = new Output();
        Administrator administrator = (Administrator) input.getCurrentUser();
        Elective elective = new Elective();
        Classes classes = new Classes();
        classes.setId(input.getLong("classId"));
        elective.setClasses(classes);
        elective.setAdmin(administrator);
        Course course = new Course();
        course.setId(input.getLong("courseId"));
        elective.setCourse(course);
        electiveService.save(elective);
        result.setData(classes);
        result.setMsg("授课信息添加成功！");
        return result;
    }

    /**
     * 获取授课信息
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
        Elective elective = electiveService.get(input.getLong("id"));
        if (null == elective) {
            return new Output(ERROR_NO_RECORD, "没有此条授课信息");
        }

        result.setStatus(SUCCESS);
        result.setMsg("查询授课信息成功！");
        result.setData(elective);
        return result;
    }

    /**
     * 修改授课信息
     * <p>传入参数：</p>
     * <pre>
     *      method:modify
     *      token: wxopenid, 微信wxid
     *      params: {teacherId: "1", classId："2"，courseId:"2"}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.ADMIN)
    public Output modify(Input input) {
        Output result = new Output();
        Elective elective = electiveService.get(input.getLong("id"));
        if (StringUtils.isNotEmpty(input.getString("classId"))) {
            Classes classes = classesService.get(input.getLong("classId"));
            elective.setClasses(classes);
        } else {
            return new Output(ERROR_NO_RECORD, "没有对应的班级！");
        }

        if (StringUtils.isNotEmpty(input.getString("courseId"))) {
            Course course = courseService.get(input.getLong("courseId"));
            elective.setCourse(course);
        } else {
            return new Output(ERROR_NO_RECORD, "没有对应的课程！");
        }

        if (StringUtils.isNotEmpty(input.getString("adminId"))) {
            Administrator teacher = administratorService.get(input.getLong("adminId"));
            elective.setAdmin(teacher);
        } else {
            return new Output(ERROR_NO_RECORD, "没有对应的教师！");
        }
        electiveService.update(elective);
        result.setStatus(SUCCESS);
        result.setMsg("修改授课信息成功！");
        result.setData(elective);
        return result;
    }

    /**
     * 删除授课信息
     * <p>传入参数</p>
     * <pre>
     *     method: delete
     *     token: wxopenid, 微信wxid
     *     params: {id: 1}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.ADMIN)
    public Output delete(Input input) {
        Output result = new Output();
        Elective elective = electiveService.get(input.getLong("id"));
        if (null == elective) {
            return new Output(ERROR_NO_RECORD, "没有此条授课信息！");
        }
        electiveService.delete(elective);
        result.setStatus(SUCCESS);
        result.setMsg("删除授课信息成功！");
        return result;
    }

    /**
     * 获取授课信息
     * <p>传入参数</p>
     *
     * <pre>
     *     token: wxId,
     *     method: getElectivesByAdmin
     * </pre>
     * @param input
     * @return
     */
    @Checked(Item.ADMIN)
    public Output getElectivesByWxId(Input input){
        Output result = new Output();
        String token = input.getToken();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("wxId", token);
        List<Elective> electiveList = electiveService.query(map);
        if (electiveList.size() == 0) {
            return new Output(ERROR_NO_RECORD, "没有授课信息！");
        }
        result.setData(electiveList);
        result.setStatus(SUCCESS);
        result.setMsg("获取授课信息成功！");
        return result;
    }



}
