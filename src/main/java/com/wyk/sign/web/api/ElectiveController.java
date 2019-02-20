/**
 *
 */
package com.wyk.sign.web.api;

import com.wyk.framework.utils.DateUtil;
import com.wyk.sign.annotation.Checked;
import com.wyk.sign.annotation.Item;
import com.wyk.sign.model.*;
import com.wyk.sign.service.TbClassService;
import com.wyk.sign.service.TbCourseService;
import com.wyk.sign.service.TbElectiveService;
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
    /**
     * 新增授课信息
     * <p>传入参数</p>
     * <pre>
     *     method: insert
     *     token: openid
     *     params: {classId："2"，courseId:"2", courseScoreRatio: "50", testScoreRatio: "50", termStartDate: "yyyy-MM-dd", termStopDate: "yyyy-MM-dd"}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.ADMIN)
    public Output insert(Input input) {
        Output result = new Output();
        TbAdmin TbAdmin = (TbAdmin) input.getCurrentTbUser(); // 授课教师，为当前用户
        TbElective tbElective = new TbElective();
        TbClass TbClass = new TbClass();
        TbClass.setId(input.getLong("classId"));
        tbElective.setTbClass(TbClass);
        tbElective.setAdmin(TbAdmin);
        TbCourse tbCourse = new TbCourse();
        tbCourse.setId(input.getLong("courseId"));
        tbElective.setTbCourse(tbCourse);
        tbElective.setCourseScoreRatio(input.getInteger("courseScoreRatio"));
        tbElective.setTestScoreRatio(input.getInteger("testScoreRatio"));

        if(StringUtils.isNotEmpty(input.getString("courseScoreRatio")) && StringUtils.isNotEmpty(input.getString("testScoreRatio"))){
            if(tbElective.getCourseScoreRatio() + tbElective.getTestScoreRatio() != 100){
                return new Output(ERROR_UNKNOWN, "平时分占比和考试分占比之和不等于100%！");
            }
        }

        if (StringUtils.isNotEmpty(input.getString("termStartDate"))) {
            tbElective.setTermStartDate(input.getDate("termStartDate"));
        }
        if (StringUtils.isNotEmpty(input.getString("termStartDate"))) {
            tbElective.setTermStopDate(input.getDate("termStopDate"));
        }

        if (StringUtils.isNotEmpty(input.getString("termStartDate")) && StringUtils.isNotEmpty(input.getString("termStartDate"))) {
            if(tbElective.getTermStopDate().before(tbElective.getTermStartDate())){
                return new Output(ERROR_UNKNOWN, "学期结束日期早于开始日期，请重填！");
            }
        }

        try {
            tbElectiveService.insert(tbElective);
        } catch (Exception e) {
            return new Output(ERROR_UNKNOWN, "此授课的课程和班级已存在！");
        }
        result.setData(TbClass);
        result.setMsg("授课信息新增成功！");
        return result;
    }

    /**
     * 获取授课信息
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
        TbElective tbElective = tbElectiveService.get(input.getLong("id"));
        if (null == tbElective) {
            return new Output(ERROR_NO_RECORD, "没有此条授课信息");
        }

        result.setStatus(SUCCESS);
        result.setMsg("查询授课信息成功！");
        result.setData(tbElective);
        return result;
    }

    /**
     * 修改授课信息
     * <p>传入参数：</p>
     * <pre>
     *      method: modify
     *      token: openid
     *      params: {id: 1, 需要修改的参数}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.ADMIN)
    public Output modify(Input input) {
        Output result = new Output();
        TbElective tbElective = tbElectiveService.get(input.getLong("id"));
        if (null == tbElective) {
            return new Output(ERROR_NO_RECORD, "没有此授课信息");
        }
        if (StringUtils.isNotEmpty(input.getString("classId"))) {
            TbClass TbClass = new TbClass();
            TbClass.setId(input.getLong("classId"));
            tbElective.setTbClass(TbClass);
        }
        if (StringUtils.isNotEmpty(input.getString("courseId"))) {
            TbCourse tbCourse = new TbCourse();
            tbCourse.setId(input.getLong("courseId"));
            tbElective.setTbCourse(tbCourse);
        }
        if (StringUtils.isNotEmpty(input.getString("courseScoreRatio"))) {
            tbElective.setCourseScoreRatio(input.getInteger("courseScoreRatio"));
        }
        if (StringUtils.isNotEmpty(input.getString("testScoreRatio"))) {
            tbElective.setTestScoreRatio(input.getInteger("testScoreRatio"));
        }
        if (StringUtils.isNotEmpty(input.getString("termStartDate"))) {
            tbElective.setTermStartDate(input.getDate("termStartDate"));
        }
        if (StringUtils.isNotEmpty(input.getString("termStopDate"))) {
            tbElective.setTermStopDate(input.getDate("termStopDate"));
        }

        tbElectiveService.update(tbElective);
        result.setStatus(SUCCESS);
        result.setMsg("授课信息修改成功！");
        result.setData(tbElective);
        return result;
    }

    /**
     * 删除授课信息
     * <p>传入参数</p>
     * <pre>
     *     method: delete
     *     token: openid
     *     params: {id: 1}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.ADMIN)
    public Output delete(Input input) {
        Output result = new Output();
        TbElective tbElective = tbElectiveService.get(input.getLong("id"));
        if (null == tbElective) {
            return new Output(ERROR_NO_RECORD, "没有此条授课信息！");
        }
        tbElectiveService.delete(tbElective);
        result.setStatus(SUCCESS);
        result.setMsg("删除授课信息成功！");
        return result;
    }

    /**
     * 获取授课信息
     * <p>传入参数</p>
     *
     * <pre>
     *     token: openid,
     *     method: getElectivesByWxId
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.TYPE)
    public Output getElectivesByWxId(Input input) {
        Output result = new Output();
        String token = input.getToken();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("wxId", token);
        List<TbElective> tbElectiveList = tbElectiveService.query(map);
        result.setData(toArray(tbElectiveList));
        result.setStatus(SUCCESS);
        result.setMsg("获取授课信息成功！");
        return result;
    }

    /**
     * 用于展示
     *
     * @param tbElective
     * @return
     */
    public Map<String, Object> toMap(TbElective tbElective) {
        Map<String, Object> result = new HashMap<>();
        result.put("id", tbElective.getId());
        result.put("admin", tbElective.getAdmin());
        result.put("classes", tbElective.getTbClass());
        result.put("course", tbElective.getTbCourse());
        result.put("courseScoreRatio", tbElective.getCourseScoreRatio());
        result.put("testScoreRatio", tbElective.getTestScoreRatio());
        if (null != tbElective.getTermStartDate()) {
            result.put("termStartDate", DateUtil.format(tbElective.getTermStartDate(), DateUtil.DATE_FORMAT));
        }
        if (null != tbElective.getTermStopDate()) {
            result.put("termStopDate", DateUtil.format(tbElective.getTermStopDate(), DateUtil.DATE_FORMAT));
        }
        return result;
    }

}
