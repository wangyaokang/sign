/**
 *
 */
package com.wyk.sign.web.api;

import com.wyk.sign.annotation.Checked;
import com.wyk.sign.annotation.Item;
import com.wyk.sign.model.Administrator;
import com.wyk.sign.model.Classes;
import com.wyk.sign.model.Course;
import com.wyk.sign.model.Student;
import com.wyk.sign.service.CourseService;
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
 * 课程相关接口
 *
 * @author wyk
 */
@Controller("apiCourse")
@RequestMapping("/api/course")
public class CourseController extends AbstractController {

    @Autowired
    CourseService courseService;

    @Autowired
    ElectiveService electiveService;

    /**
     * 新增
     * <p>传入参数</p>
     * <pre>
     *     method: insert
     *     token: 当前wxId
     *     params: {name："基础英语"}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.ADMIN)
    public Output insert(Input input) {
        Output result = new Output();
        Course course = new Course();
        course.setName(input.getString("name"));
        courseService.save(course);
        result.setData(course);
        result.setMsg("课程信息添加成功！");
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
        Course course = courseService.get(input.getLong("id"));
        if (null == course) {
            return new Output(ERROR_NO_RECORD, "没有对应的课程");
        }
        result.setStatus(SUCCESS);
        result.setMsg("查询课程信息成功！");
        result.setData(course);
        return result;
    }

    /**
     * 获取对应的授课班级
     * <p>传入参数</p>
     * <pre>
     *     method: getClassListByCourseId
     *     token: wxopenid, 微信wxid
     *     params: {courseId: 1}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.TYPE)
    public Output getClassListByCourseId(Input input) {
        Output result = new Output();
        Map<String, Object> params = input.getParams();
        List<Classes> classesList = electiveService.getClassesList(params);
        params.put("classesList", classesList);
        result.setData(params);
        result.setStatus(SUCCESS);
        result.setMsg("修改班级信息成功！");
        return result;
    }

    /**
     * 获取课程授课教师
     * <p>传入参数</p>
     * <pre>
     *     method: getCourseTeacherList
     *     token: wxopenid, 微信wxid
     *     params: {courseId: 1}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.TYPE)
    public Output getTeacherListByCourseId(Input input) {
        Output result = new Output();
        Map<String, Object> params = input.getParams();
        List<Administrator> teacherList = electiveService.getTeacherList(params);
        params.put("teacherList", teacherList);
        result.setData(teacherList);
        result.setStatus(SUCCESS);
        result.setMsg("获取授课教师信息成功！");
        return result;
    }
}
