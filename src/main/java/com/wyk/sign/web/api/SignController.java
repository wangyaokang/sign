/**
 *
 */
package com.wyk.sign.web.api;

import com.wyk.framework.util.DateUtils;
import com.wyk.sign.annotation.Checked;
import com.wyk.sign.annotation.Item;
import com.wyk.sign.model.*;
import com.wyk.sign.service.SignInfoService;
import com.wyk.sign.service.SignService;
import com.wyk.sign.web.api.param.Input;
import com.wyk.sign.web.api.param.Output;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 签到相关接口
 *
 * <pre>由学生创建</pre>
 *
 * @author wyk
 */
@Controller("apiSign")
@RequestMapping("/api/sign")
public class SignController extends AbstractController {

    @Autowired
    SignInfoService signInfoService;

    @Autowired
    SignService signService;

    /**
     * 创建签到
     * <p>
     * 传入参数
     * </p>
     * <pre>
     * token : wxid
     * params : {"id" : "2018-06-09 10:30"}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.STU)
    public Output info(Input input) {
        Output result = new Output();
        Sign sign = signService.get(input.getLong("id"));
        if (null == sign) {
            return new Output(ERROR_NO_RECORD, "未能获取签到信息！");
        }
        result.setData(sign);
        result.setMsg("创建签到成功！");
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
     * params : {"signinfo_id" : "2", "signDate" : "2018-06-09 10:30", "signAddress" : "xxxx大楼"}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.STU)
    public Output insert(Input input) {
        Output result = new Output();
        Sign sign = new Sign();
        SignInfo signInfo = signInfoService.get(input.getLong("signinfo_id"));
        sign.setSignInfo(signInfo);
        User student = input.getCurrentUser();
        sign.setStudent(student);
        sign.setSignAddress(input.getString("signAddress"));

        // 签到逻辑
        if (sign.getSignAddress().equals(signInfo.getAddress())) {
            return new Output(ERROR_UNKNOWN, "签到地点不对，请重新定位！");
        }

        if (StringUtils.isNotEmpty(input.getString("signDate"))) {
            sign.setSignDate(input.getDate("signDate", DateUtils.DATETIME_MIN_FORMAT));
        }

        if (sign.getSignDate().before(signInfo.getStartDate())) {
            return new Output(ERROR_UNKNOWN, String.format("请在%s至%s的时间段内签到！", DateUtils.format(signInfo.getStartDate(), DateUtils.DATETIME_SEC_FORMAT), DateUtils.format(signInfo.getStopDate(), DateUtils.DATETIME_SEC_FORMAT)));
        }

        if (sign.getSignDate().after(signInfo.getStopDate())) {
            result.setMsg("你的签到状态为：【迟到】！");
            sign.setStatus(0);
        }else{
            result.setMsg("你的签到状态为：【正常签到】！");
            sign.setStatus(1);
        }
        signService.insert(sign);
        result.setStatus(SUCCESS);
        return result;
    }

    /**
     * 补签
     * <p>传入参数：</p>
     * <pre>
     *      method:modify
     *      token: wxopenid
     *      params: 全部信息，例如：{id: "25", "startDate" : "2018-06-09 10:30", "stopDate" : "2018-06-09 12:30", "address" : "xxxx大楼", "classId" : "02", "courseId" : "2"}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.ADMIN)
    public Output suppleSign(Input input) {
        Output result = new Output();
        SignInfo signInfo = signInfoService.get(input.getLong("id"));
        if (null == signInfo) {
            return new Output(ERROR_NO_RECORD, "未获取到对应的签到信息！");
        }
        signInfo.setAddress(input.getString("address"));
        if (StringUtils.isNotEmpty(input.getString("startDate"))) {
            signInfo.setStartDate(input.getDate("startDate", DateUtils.DATETIME_MIN_FORMAT));
        }
        if (StringUtils.isNotEmpty(input.getString("stopDate"))) {
            signInfo.setStopDate(input.getDate("stopDate", DateUtils.DATETIME_MIN_FORMAT));
        }
        Classes classes = new Classes();
        classes.setId(input.getLong("classId"));
        signInfo.setClasses(classes);
        Course course = new Course();
        course.setId(input.getLong("courseId"));
        signInfo.setCourse(course);
        signInfoService.update(signInfo);
        result.setStatus(SUCCESS);
        result.setMsg("修改签到信息成功！");
        result.setData(classes);
        return result;
    }
}
