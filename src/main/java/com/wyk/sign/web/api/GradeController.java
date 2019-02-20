/**
 *
 */
package com.wyk.sign.web.api;

import com.wyk.sign.annotation.Checked;
import com.wyk.sign.annotation.Item;
import com.wyk.sign.model.*;
import com.wyk.sign.service.TbElectiveService;
import com.wyk.sign.service.TbGradeService;
import com.wyk.sign.web.api.param.Input;
import com.wyk.sign.web.api.param.Output;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 成绩相关接口
 *
 * @author wyk
 */
@Controller("apiGrade")
@RequestMapping("/api/grade")
public class GradeController extends AbstractController {



    @Autowired
    TbElectiveService tbElectiveService;

    /**
     * 保存
     *
     * @param input
     * @return
     */
    public Output save(Input input) {
        Output result = new Output();
        TbGrade tbGrade = tbGradeService.get(input.getLong("id"));
        // 没有成绩时直接新建
        if (null == tbGrade) {
            tbGrade = new TbGrade();
        }

        if (StringUtils.isNotEmpty(input.getString("electiveId"))) {
            TbElective tbElective = new TbElective();
            tbElective.setId(input.getLong("electiveId"));
            tbGrade.setTbElective(tbElective);
        }
        if (StringUtils.isNotEmpty(input.getString("stuId"))) {
            TbStudent tbStudent = new TbStudent();
            tbStudent.setId(input.getLong("stuId"));
            tbGrade.setTbStudent(tbStudent);
        }
        if (StringUtils.isNotEmpty(input.getString("courseScore"))) {
            tbGrade.setCourseScore(input.getInteger("courseScore"));
        }
        if (StringUtils.isNotEmpty(input.getString("testScore"))) {
            tbGrade.setTestScore(input.getInteger("testScore"));
        }

        tbGradeService.save(tbGrade);
        result.setMsg("成绩保存成功！");
        result.setStatus(SUCCESS);
        return result;
    }

    /**
     * 根据用户id，获取对应的成绩
     * <p>传入参数</p>
     *
     * <pre>
     *     method: getUserGradeList
     *     token: wxId
     *     params: {"stuId": 1}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.TYPE)
    public Output getUserGradeList(Input input) {
        Output result = new Output();
        TbStudent tbStudent = tbStudentService.get(input.getLong("stuId"));
        if (null == tbStudent) {
            return new Output(ERROR_NO_RECORD, "没有此学生！");
        }
        Map<String, Object> param = new HashMap<>();
        param.put("classId", tbStudent.getTbClass().getId());
        List<TbElective> tbElectiveList = tbElectiveService.query(param);
        List<Map<String, Object>> gradeList = new ArrayList<>();
        for (TbElective tbElective : tbElectiveList) {
            Map<String, Object> map = new HashMap<>();
            map.put("electiveId", tbElective.getId());
            map.put("stuId", tbStudent.getId());
            TbGrade tbGrade = tbGradeService.get(map);
            gradeList.add(toMap(tbGrade, tbElective, tbStudent));
        }

        result.setStatus(SUCCESS);
        result.setMsg("获取成绩成功！");
        result.setData(gradeList);
        return result;
    }

    /**
     * 根据授课信息获取对应班级学生的成绩列表
     * <p>传入参数</p>
     *
     * <pre>
     *     token: openid
     *     method: getGradeListByElective
     *     params: {electiveId : '1'}
     * </pre>
     * @param input
     * @return
     */
    @Checked(Item.ADMIN)
    public Output getGradeListByElective(Input input) {
        Output result = new Output();
        TbElective tbElective = tbElectiveService.get(input.getLong("electiveId"));
        if (null == tbElective) {
            return new Output(ERROR_NO_RECORD, "没有此授课记录！");
        }
        Map<String, Object> param = new HashMap<>();
        param.put("classId", tbElective.getTbClass().getId());
        List<TbStudent> tbStudentList = tbStudentService.query(param);
        if (tbStudentList.size() == 0) {
            return new Output(ERROR_NO_RECORD, "此班级没有学生！");
        }
        List<Map<String, Object>> gradeList = new ArrayList<>();
        for (TbStudent tbStudent : tbStudentList) {
            Map<String, Object> gradeParam = new HashMap<>();
            gradeParam.put("electiveId", tbElective.getId());
            gradeParam.put("stuId", tbStudent.getId());
            TbGrade tbGrade = tbGradeService.get(gradeParam);
            gradeList.add(toMap(tbGrade, tbElective, tbStudent));
        }
        result.setStatus(SUCCESS);
        result.setMsg("获取成绩列表成功！");
        result.setData(gradeList);
        return result;
    }

    /**
     * 用于展示
     *
     * @param tbGrade 成绩类
     * @param tbElective 授课信息
     * @param tbStudent 学生
     * @return
     */
    public Map<String, Object> toMap(TbGrade tbGrade, TbElective tbElective, TbStudent tbStudent) {
        Map<String, Object> result = new HashMap<>();
        result.put("student", tbStudent);
        result.put("elective", tbElective);
        double courseScore = tbGradeService.getCourseScore(tbElective, tbStudent);
        result.put("courseScore", courseScore);
        double testScore = 0;
        if (tbGrade == null) {
            result.put("testScore", testScore);
        } else {
            result.put("id", tbGrade.getId());
            testScore = tbGrade.getTestScore();
            result.put("testScore", testScore);
        }
        double total = courseScore * tbElective.getCourseScoreRatio() / 100 + testScore * tbElective.getTestScoreRatio() / 100;
        result.put("total", total);
        return result;
    }
}
