/**
 *
 */
package com.wyk.sign.web.api;

import com.wyk.sign.annotation.Checked;
import com.wyk.sign.annotation.Item;
import com.wyk.sign.model.TbCourse;
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
     *     token: openid
     *     params: {name："基础英语"}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.ADMIN)
    public Output insert(Input input) {
        Output result = new Output();
        TbCourse tbCourse = new TbCourse();
        if(StringUtils.isEmpty(input.getString("name"))){
            return new Output(ERROR_NO_RECORD, "课程名不能为空！");
        }
        tbCourse.setName(input.getString("name"));
        courseService.insert(tbCourse);
        result.setData(tbCourse);
        result.setStatus(SUCCESS);
        result.setMsg("课程新增成功！");
        return result;
    }

    /**
     * 获取课程
     * <p>传入参数</p>
     * <pre>
     *     method: info
     *     token: openid
     *     params: {id：1}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.TYPE)
    public Output info(Input input) {
        Output result = new Output();
        TbCourse tbCourse = courseService.get(input.getLong("id"));
        if (null == tbCourse) {
            return new Output(ERROR_NO_RECORD, "没有对应的课程");
        }
        result.setStatus(SUCCESS);
        result.setMsg("查询课程成功！");
        result.setData(tbCourse);
        return result;
    }

    /**
     * 删除课程
     * <p>传入参数</p>
     *
     * <pre>
     *     token: openid
     *     method: delete
     *     params: {id : 2}
     * </pre>
     * @param input
     * @return
     */
    @Checked(Item.ADMIN)
    public Output delete(Input input){
        Output result = new Output();
        TbCourse tbCourse = courseService.get(input.getLong("id"));
        if(null == tbCourse){
            return new Output(ERROR_NO_RECORD, "没有此课程！");
        }
        // 删除对应课程的授课信息
        Map<String, Object> param = new HashMap<>();
        param.put("courseId", tbCourse.getId());
        electiveService.deleteByMap(param);
        courseService.delete(tbCourse);
        result.setMsg("删除课程成功");
        result.setStatus(SUCCESS);
        return result;
    }

    /**
     * 查询全部课程
     * <p>传入参数</p>
     *
     * <pre>
     *     token : openid;
     *     method : queryCourses
     * </pre>
     * @param input
     * @return
     */
    public Output queryCourses(Input input){
        Output result = new Output();
        List<TbCourse> tbCourseList = courseService.query();
        if(tbCourseList.size() == 0){
            return new Output(ERROR_NO_RECORD, "没有创建任何课程！");
        }
        result.setStatus(SUCCESS);
        result.setMsg("获取课程成功！");
        result.setData(tbCourseList);
        return result;
    }
}
