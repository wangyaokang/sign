/**
 *
 */
package com.wyk.sign.web.api;

import com.wyk.sign.annotation.Checked;
import com.wyk.sign.annotation.Item;
import com.wyk.sign.model.TbAdmin;
import com.wyk.sign.model.TbClass;
import com.wyk.sign.model.TbElective;
import com.wyk.sign.model.TbStudent;
import com.wyk.sign.web.api.param.Input;
import com.wyk.sign.web.api.param.Output;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

/**
 * 班级相关接口
 *
 * @author wyk
 */
@Controller("apiClasses")
@RequestMapping("/api/classes")
public class ClassesController extends AbstractController {



    /**
     * 新增
     * <p>传入参数</p>
     * <pre>
     *     method: insert
     *     token: openid
     *     params: {name："英语11102班"，adminId:"2"}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.ADMIN)
    public Output insert(Input input) {
        Output result = new Output();
        TbClass TbClass = new TbClass();
        TbClass.setName(input.getString("name"));
        if (StringUtils.isNotEmpty(input.getString("adminId"))) {
            TbAdmin admin = new TbAdmin();
            admin.setId(input.getLong("adminId"));
            TbClass.setAdmin(admin);
        }
        classesService.save(TbClass);
        result.setData(TbClass);
        result.setMsg("班级新增成功！");
        return result;
    }

    /**
     * 修改班级信息
     * <p>传入参数：</p>
     * <pre>
     *      method:modify
     *      token: openid
     *      params: {id: 1, name: 英语110班, adminId: 2}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.ADMIN)
    public Output modify(Input input) {
        Output result = new Output();
        TbClass TbClass = classesService.get(input.getLong("id"));
        if (null == TbClass) {
            return new Output(ERROR_NO_RECORD, "没有对应的班级！");
        }
        TbClass.setName(input.getString("name"));
        if (StringUtils.isNotEmpty(input.getString("adminId"))) {
            TbAdmin admin = new TbAdmin();
            admin.setId(input.getLong("adminId"));
            TbClass.setAdmin(admin);
        }
        TbClass.setModifyTime(new Date());
        classesService.update(TbClass);
        result.setStatus(SUCCESS);
        result.setMsg("修改班级信息成功！");
        result.setData(TbClass);
        return result;
    }

    /**
     * 查询全部班级
     * <p>传入参数</p>
     * <pre>
     *     null
     * </pre>
     *
     * @param input
     * @return
     */
    public Output queryClasses(Input input) {
        Output result = new Output();
        List<TbClass> TbClassList = classesService.query();
        if (TbClassList.size() == 0) {
            return new Output(ERROR_NO_RECORD, "无班级记录");
        }
        result.setData(toArray(TbClassList));
        result.setStatus(SUCCESS);
        result.setMsg("修改班级信息成功！");
        return result;
    }

    /**
     * 删除班级（同时对应的班级学生所属班级置空）
     * <p>传入参数</p>
     *
     * <pre>
     *     token: openid
     *     method: delete
     *     params: {id : 2}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.ADMIN)
    public Output delete(Input input) {
        Output result = new Output();
        try {
            TbClass TbClass = classesService.get(input.getLong("id"));
            Map<String, Object> param = new HashMap<>();
            param.put("classId", TbClass.getId());
            List<TbStudent> tbStudentList = studentService.query(param);
            if (tbStudentList.size() != 0) {
                List<TbStudent> paramList = new ArrayList<>();
                int len = tbStudentList.size();
                for (int i = 0; i < len; i++) {
                    TbStudent tbStudent = tbStudentList.get(i);
                    tbStudent.setTbClass(null);
                    paramList.add(tbStudent);
                    if(i % 10 == 0 && i >= 10) {
                        studentService.updateBatch(paramList);
                        paramList.clear();
                    } else if (i == len - 1) {
                        studentService.updateBatch(paramList);
                    }
                }
                logger.debug("{}条数据更新成功！", tbStudentList.size());
            }
            classesService.delete(TbClass);
        } catch (Exception e) {
            logger.error(e, e);
        }
        result.setMsg("删除班级成功");
        result.setStatus(SUCCESS);
        return result;
    }

    /**
     * 获取班级成员信息
     * <p>传入参数</p>
     * <pre>
     *     method: getClassesListByAdmin
     *     token: openid
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.ADMIN)
    public Output getClassesListByAdmin(Input input) {
        Output result = new Output();
        Map<String, Object> params = new HashMap<>();
        params.put("adminId", ((TbAdmin) input.getCurrentTbUser()).getId());
        List<TbClass> TbClassList = classesService.query(params);
        if (TbClassList.size() == 0) {
            return new Output(ERROR_NO_RECORD, "没有管理的班级！");
        }
        result.setData(toArray(TbClassList));
        result.setStatus(SUCCESS);
        result.setMsg("修改班级信息成功！");
        return result;
    }

    /**
     * 获取授课班级
     * <p>传入参数</p>
     *
     * <pre>
     *     token: openid,
     *     method: getElectivesByAdmin
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.ADMIN)
    public Output getClassesListFormElective(Input input) {
        Output result = new Output();
        String token = input.getToken();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("wxId", token);
        List<TbElective> tbElectiveList = electiveService.query(map);
        if (tbElectiveList.size() == 0) {
            return new Output(ERROR_NO_RECORD, "没有授课班级！");
        }
        List<TbClass> TbClassList = new ArrayList<>();
        // 此处班级信息不全，需要重新查询
        for (TbElective tbElective : tbElectiveList) {
            TbClass TbClass = classesService.get(tbElective.getTbClass().getId());
            TbClassList.add(TbClass);
        }
        result.setData(toArray(TbClassList));
        result.setStatus(SUCCESS);
        result.setMsg("获取授课班级信息成功！");
        return result;
    }

    /**
     * 用于展示
     *
     * @param TbClass
     * @return
     */
    public Map<String, Object> toMap(TbClass TbClass) {
        Map<String, Object> result = new HashMap<>();
        result.put("id", TbClass.getId());
        result.put("name", TbClass.getName());
        result.put("admin", TbClass.getAdmin());
        Map<String, Object> param = new HashMap<>();
        param.put("classId", TbClass.getId());
        // 班级成员（学生）
        Map<String, Object> studentMap = new HashMap<>();
        List<TbStudent> tbStudentList = studentService.query(param);
        studentMap.put(LIST, tbStudentList);
        studentMap.put(TOTAL, tbStudentList.size());
        result.put("studentList", studentMap);
        // 班级授课教师
        Map<String, Object> teacherMap = new HashMap<>();
        List<TbAdmin> teacherList = administratorService.query(param);
        teacherMap.put(LIST, teacherList);
        teacherMap.put(TOTAL, teacherList.size());
        param.put("teacherList", teacherList);
        return result;
    }

}
