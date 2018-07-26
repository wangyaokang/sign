/**
 *
 */
package com.wyk.sign.web.api;

import com.wyk.sign.annotation.Checked;
import com.wyk.sign.annotation.Item;
import com.wyk.sign.model.Course;
import com.wyk.sign.service.CourseService;
import com.wyk.sign.service.ElectiveService;
import com.wyk.sign.web.api.param.Input;
import com.wyk.sign.web.api.param.Output;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
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
     * 删除课程
     * <p>传入参数</p>
     *
     * <pre>
     *     token: wxId
     *     method: delete
     *     params: {id : 2}
     * </pre>
     * @param input
     * @return
     */
    @Checked(Item.ADMIN)
    public Output delete(Input input){
        Output result = new Output();
        Course course = courseService.get(input.getLong("id"));
        Map<String, Object> param = new HashMap<>();
        param.put("courseId", course.getId());
        int rows = electiveService.deleteElectiveByMap(param);
        logger.debug("删除{}条授课信息数据！", rows);
        courseService.delete(course);
        result.setMsg("删除课程成功");
        result.setStatus(SUCCESS);
        return result;
    }
}
