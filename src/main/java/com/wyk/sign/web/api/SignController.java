/**
 *
 */
package com.wyk.sign.web.api;

import com.wyk.framework.utils.DateUtil;
import com.wyk.framework.utils.LocationUtil;
import com.wyk.sign.annotation.Checked;
import com.wyk.sign.annotation.Item;
import com.wyk.sign.model.*;
import com.wyk.sign.service.SignInfoService;
import com.wyk.sign.service.SignService;
import com.wyk.sign.util.Constants;
import com.wyk.sign.web.api.param.Input;
import com.wyk.sign.web.api.param.Output;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

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
     * params : {"infoId" : "2", "signAddress" : "xxxx大楼", "longitude": "经度", "latitude": "纬度"}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.STU)
    public Output insert(Input input) {
        Output result = new Output();
        Sign sign = new Sign();
        SignInfo signInfo = signInfoService.get(input.getLong("infoId"));
        if (null == signInfo) {
            return new Output(ERROR_UNKNOWN, "没有对应的签到信息！");
        }
        sign.setInfo(signInfo);
        Student student = (Student) input.getCurrentUser();
        sign.setStudent(student);
        sign.setSignAddress(input.getString("signAddress"));

        // 计算签到地与打卡点的距离
        double distance = LocationUtil.getDistance(Double.valueOf(signInfo.getLongitude()), Double.valueOf(signInfo.getLatitude()),
                input.getDouble("longitude"), input.getDouble("latitude"));
        // 签到逻辑（签到地和打卡地不同、或者距离大于200m,显示打卡失败！)
        if (!sign.getSignAddress().equals(signInfo.getAddress()) || distance > 200) {
            return new Output(ERROR_UNKNOWN, "签到地点不对，请重新定位！");
        }

        Date now = new Date();
        if (now.before(signInfo.getStartDate())) {
            return new Output(ERROR_UNKNOWN, String.format("请在%s至%s的时间段内签到！", DateUtil.format(signInfo.getStartDate(), DateUtil.DATETIME_FORMAT_YMDHMS), DateUtil.format(signInfo.getStopDate(), DateUtil.DATETIME_FORMAT_YMDHMS)));
        }

        if (now.after(signInfo.getStopDate())) {
            result.setMsg("你的签到状态为：【迟到】！");
            sign.setStatus(Constants.Sign.LATE);
        } else {
            result.setMsg("你的签到状态为：【签到】！");
            sign.setStatus(Constants.Sign.SIGNED);
        }
        signService.insert(sign);
        result.setData(sign);
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
     * params : {"id" : "2018-06-09 10:30"}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.STU)
    public Output delete(Input input) {
        Output result = new Output();
        Sign sign = signService.get(input.getLong("id"));
        if (null == sign) {
            return new Output(ERROR_NO_RECORD, "未能获取签到信息！");
        }
        signService.delete(sign);
        result.setMsg("删除签到成功！");
        result.setStatus(SUCCESS);
        return result;
    }

    /**
     * 申请补签
     * <p>传入参数：</p>
     * <pre>
     *      method:suppleSign
     *      token: wxId
     *      params: {infoId: 1}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.STU)
    public Output suppleSign(Input input) {
        Output result = new Output();
        Map<String, Object> param = new HashMap<>();
        Student student = (Student) input.getCurrentUser();
        param.put("wxId", student.getWxId());
        Sign sign = signService.get(param);

        if (null == sign) {
            sign = new Sign();
            sign.setStudent(student);
            SignInfo info = signInfoService.get(input.getLong("infoId"));
            if (null == info) {
                return new Output(ERROR_UNKNOWN, "没有对应的签到信息！");
            }
            sign.setInfo(info);
            sign.setSignDate(new Date());
        } else {
            sign.setModifyTime(new Date());
        }
        sign.setStatus(Constants.Sign.SUPPLE_SIGN);
        signService.save(sign);
        result.setStatus(SUCCESS);
        result.setMsg("申请补签成功，待教师同意！");
        result.setData(sign);
        return result;
    }

    /**
     * 同意签到
     * <p>传入参数：</p>
     * <pre>
     *      method:modify
     *      token: wxopenid
     *      params: {id: 2, isAccept: 1}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.ADMIN)
    public Output acceptSign(Input input) {
        Output result = new Output();
        Sign sign = signService.get(input.getLong("id"));
        if (null == sign) {
            return new Output(ERROR_NO_RECORD, "没有对应的签到！");
        }

        if (Constants.Sign.NOT_SIGNED.equals(input.getInteger("isAccept"))) {
            sign.setStatus(Constants.Sign.NOT_SIGNED);
            result.setMsg("补签未批准！");
        } else if (Constants.Sign.SIGNED.equals(input.getInteger("isAccept"))) {
            sign.setStatus(Constants.Sign.SIGNED);
            result.setMsg("补签成功！");
        } else {
            return new Output(ERROR_NO_RECORD, "请选择是否同意补签！【1：批准；2：不批准】");
        }
        signService.update(sign);
        result.setStatus(SUCCESS);
        result.setData(sign);
        return result;
    }

    /**
     * 查看签到情况
     * <p>传入参数</p>
     *
     * <pre>
     *     token : wxId,
     *     method : queryListByInfoId
     *     params : {"infoId" : "2"}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.ADMIN)
    public Output querySignListByInfoId(Input input) {
        Output result = new Output();
        SignInfo signInfo = signInfoService.get(input.getLong("infoId"));
        if (null == signInfo) {
            return new Output(ERROR_UNKNOWN, "没有对应的签到信息，请重试！");
        }

        Map<String, Object> param = new HashMap<>();
        param.put("classId", signInfo.getClasses().getId());
        List<Student> studentList = studentService.query(param);
        List<Map<String, Object>> signList = new ArrayList<>();
        for (Student student : studentList) {
            signList.add(toMap(student, signInfo));
        }
        result.setData(signList);
        result.setMsg("获取签到情况成功！");
        return result;
    }

    public Map<String, Object> toMap(Student student, SignInfo signInfo) {
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> info = new HashMap<String, Object>();
        info.put("id", signInfo.getId());
        info.put("signDate", DateUtil.format(signInfo.getStopDate(), DateUtil.DATE_FORMAT));
        info.put("stopDate", DateUtil.format(signInfo.getStopDate(), DateUtil.DATETIME_FORMAT_HM));
        info.put("startDate", DateUtil.format(signInfo.getStartDate(), DateUtil.DATETIME_FORMAT_HM));
        info.put("address", signInfo.getAddress());
        info.put("status", signInfo.getStatus());
        info.put("admin", signInfo.getAdmin());
        info.put("classes", signInfo.getClasses());
        info.put("course", signInfo.getCourse());
        info.put("remark", signInfo.getRemark());
        result.put("info", info);
        Map<String, Object> param = new HashMap<>();
        param.put("infoId", signInfo.getId());
        param.put("stuId", student.getId());
        Sign sign = signService.get(param);
        if (sign == null) {
            result.put("status", Constants.Sign.NOT_SIGNED);
            result.put("signDate", "");
            result.put("signAddress", "");
        } else {
            result.put("status", sign.getStatus());
            result.put("signDate", DateUtil.format(sign.getSignDate(), DateUtil.DATETIME_FORMAT_YMDHMS));
            result.put("signAddress", sign.getSignAddress());
        }
        result.put("student", student);
        return result;
    }
}
