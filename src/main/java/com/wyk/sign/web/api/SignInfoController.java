/**
 *
 */
package com.wyk.sign.web.api;

import com.wyk.framework.utils.DateUtil;
import com.wyk.sign.annotation.Checked;
import com.wyk.sign.annotation.Item;
import com.wyk.sign.model.*;
import com.wyk.sign.service.ElectiveService;
import com.wyk.sign.service.SignInfoService;
import com.wyk.sign.service.SignService;
import com.wyk.sign.util.Constants;
import com.wyk.sign.web.api.param.Input;
import com.wyk.sign.web.api.param.Output;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 签到信息相关接口
 *
 * <pre>由管理者创建</pre>
 *
 * @author wyk
 */
@Controller("apiSignInfo")
@RequestMapping("/api/signInfo")
public class SignInfoController extends AbstractController {

    @Autowired
    SignInfoService signInfoService;

    @Autowired
    SignService signService;

    @Autowired
    ElectiveService electiveService;

    /**
     * 获取签到信息
     * <p>
     * 传入参数
     * </p>
     * <pre>
     *  method : info
     *  param：{id}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.TYPE)
    public Output info(Input input) {
        Output result = new Output();
        SignInfo signInfo = signInfoService.get(input.getLong("id"));
        if(null == signInfo){
            return new Output(ERROR_NO_RECORD, "没有获取对应的签到信息！");
        }
        result.setData(signInfo);
        result.setMsg("获取签到成功！");
        result.setStatus(SUCCESS);
        return result;
    }

    /**
     * 创建签到
     * <p>
     * 传入参数
     * </p>
     * <pre>
     * token : wxid
     * method : insert
     * params : {"startDate" : "2018-06-09 10:30:00", "stopDate" : "2018-06-09 12:30:00", "address" : "xxxx大楼", "classId" : "02", "courseId" : "2"}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.ADMIN)
    public Output insert(Input input) {
        Output result = new Output();
        Administrator admin = (Administrator) input.getCurrentUser();
        SignInfo signInfo = new SignInfo();
        signInfo.setAdmin(admin);
        signInfo.setAddress(input.getString("address"));
        if (StringUtils.isNotEmpty(input.getString("startDate"))) {
            signInfo.setStartDate(input.getDate("startDate", DateUtil.DATETIME_FORMAT));
        }
        if (StringUtils.isNotEmpty(input.getString("stopDate"))) {
            signInfo.setStopDate(input.getDate("stopDate", DateUtil.DATETIME_FORMAT));
        }
        Classes classes = new Classes();
        classes.setId(input.getLong("classId"));
        signInfo.setClasses(classes);
        Course course = new Course();
        course.setId(input.getLong("courseId"));
        signInfo.setCourse(course);
        if(admin.getUserType().equals(Constants.User.TEACHER)){
            input.getParams().put("adminId", admin.getId());
            if(StringUtils.isEmpty(input.getString("courseId")) || StringUtils.isEmpty(input.getString("classId"))){
                return new Output(ERROR_UNKNOWN, "请选择对应的课程和班级！");
            }
            Elective elective = electiveService.get(input.getParams());
            if (null == elective) {
                return new Output(ERROR_NO_RECORD, "没有此授课信息，请检查对应的授课课程和班级！");
            }
        }
        signInfoService.insert(signInfo);
        result.setMsg("创建签到成功！");
        result.setStatus(SUCCESS);
        return result;
    }

    /**
     * 修改签到信息
     * <p>传入参数：</p>
     * <pre>
     *      method:modify
     *      token: wxopenid, 微信wxid
     *      params: 全部信息，例如：{id: "25", "startDate" : "2018-06-09 10:30", "stopDate" : "2018-06-09 12:30", "address" : "xxxx大楼", "classId" : "2", "courseId" : "2"}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.ADMIN)
    public Output modify(Input input) {
        Output result = new Output();
        SignInfo signInfo = signInfoService.get(input.getLong("id"));
        if(null == signInfo){
            return new Output(ERROR_NO_RECORD, "未获取到对应的签到信息！");
        }
        signInfo.setAddress(input.getString("address"));
        if (StringUtils.isNotEmpty(input.getString("startDate"))) {
            signInfo.setStartDate(input.getDate("startDate", DateUtil.DATETIME_FORMAT));
        }
        if (StringUtils.isNotEmpty(input.getString("stopDate"))) {
            signInfo.setStopDate(input.getDate("stopDate", DateUtil.DATETIME_FORMAT));
        }
        Classes classes = new Classes();
        classes.setId(input.getLong("classId"));
        signInfo.setClasses(classes);
        Course course = new Course();
        course.setId(input.getLong("courseId"));
        signInfo.setCourse(course);
        signInfo.setModifyTime(new Date());
        signInfoService.update(signInfo);
        result.setStatus(SUCCESS);
        result.setMsg("修改签到信息成功！");
        result.setData(classes);
        return result;
    }

    /**
     * 获取我的签到
     *
     * <p>传入参数</p>
     * <pre>
     *     method: getMySignInfoList
     *     token: wxId
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.ADMIN)
    public Output getMySignInfoList(Input input){
        Output result = new Output();
        User admin = input.getCurrentUser();
        Map<String, Object> param = new HashMap<>();
        param.put("adminId", admin.getId());
        List<SignInfo> signingInfoList = signInfoService.query(param);
        result.setStatus(SUCCESS);
        result.setMsg("获取签到信息成功！");
        result.setData(signingInfoList);
        return result;
    }

    /**
     * 删除签到信息
     *
     * <p>传入参数</p>
     * <pre>
     *     method: delete
     *     token: wxId
     *     params: {id: 1}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.ADMIN)
    public Output delete(Input input){
        Output result = new Output();
        SignInfo signingInfo = signInfoService.get(input.getLong("id"));
        if(null == signingInfo){
            return new Output(ERROR_NO_RECORD, "没有对应的签到信息！");
        }
        // 删除签到信息下，所有的签到
        Map<String, Object> param = new HashMap<>();
        param.put("infoId", input.getLong("id"));
        signService.deleteByMap(param);
        signInfoService.delete(signingInfo);
        result.setStatus(SUCCESS);
        result.setMsg("删除签到信息成功！");
        return result;
    }

    /**
     * 获取选中日期对应的签到信息
     *
     * <p>传入参数</p>
     * <pre>
     *     method: getSignInfoBySelectedDate
     *     token: wxId
     *     params: {selectDate : "2018-07-31"}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.TYPE)
    public Output getSignInfoBySelectedDate(Input input){
        Output result = new Output();
        User user = input.getCurrentUser();
        Map<String, Object> param = input.getParams();
        if(user.getUserType().equals(Constants.User.STUDENT)){
            param.put("classId", ((Student) user).getClasses().getId());
        }else{
            param.put("adminId", user.getId());
        }
        param.put("selectDate", input.getString("selectDate"));
        List<SignInfo> signInfoList = signInfoService.query(param);
        if(null == signInfoList || signInfoList.size() == 0) {
            return new Output(ERROR_NO_RECORD, "当天无签到信息！");
        }

        result.setData(toArray(signInfoList));
        result.setStatus(SUCCESS);
        result.setMsg("获取签到信息成功！");
        return result;
    }

    /**
     * 获取当前月份的签到日期
     * <p>传入参数</p>
     *
     * <pre>
     *     token : 13381503907
     *     method : getSignInfoByCurMonth
     *     params : {selectMonth : "2018-08"}
     * </pre>
     * @param input
     * @return
     */
    @Checked(Item.TYPE)
    public Output getSignDatesByCurMonth(Input input){
        Output result = new Output();
        User user = input.getCurrentUser();
        Map<String, Object> param = input.getParams();
        if(user.getUserType().equals(Constants.User.STUDENT)){
            param.put("classId", ((Student) user).getClasses().getId());
        }else{
            param.put("adminId", user.getId());
        }
        param.put("curMonth", input.getString("curMonth"));
        List<String> signInfoList = signInfoService.getSignDatesByCurMonth(param);
        result.setData(signInfoList);
        result.setStatus(SUCCESS);
        result.setMsg("获取当月签到日期成功！");
        return result;
    }
}
