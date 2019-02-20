/**
 *
 */
package com.wyk.sign.web.api;

import com.wyk.framework.utils.DateUtil;
import com.wyk.framework.utils.LocationUtil;
import com.wyk.sign.annotation.Checked;
import com.wyk.sign.annotation.Item;
import com.wyk.sign.model.*;
import com.wyk.sign.service.TbSignInfoService;
import com.wyk.sign.service.TbSignService;
import com.wyk.sign.conts.Constants;
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

    /**
     * 创建签到
     * <p>
     * 传入参数
     * </p>
     * <pre>
     * token : openid
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
        try {
            TbSign tbSign = new TbSign();
            TbSignInfo tbSignInfo = tbSignInfoService.get(input.getLong("infoId"));
            if (null == tbSignInfo) {
                return new Output(ERROR_UNKNOWN, "没有对应的签到信息！");
            }
            tbSign.setInfo(tbSignInfo);
            TbStudent tbStudent = (TbStudent) input.getCurrentTbUser();
            tbSign.setTbStudent(tbStudent);
            if(StringUtils.isNotEmpty(input.getString("signAddress"))){

            }
            tbSign.setSignAddress(input.getString("signAddress"));

            Date now = new Date();
            if (now.before(tbSignInfo.getStartDate())) {
                return new Output(ERROR_UNKNOWN, String.format("请在%s的%s至%s的时间段内签到！", DateUtil.format(tbSignInfo.getStartDate(), DateUtil.DATE_FORMAT), DateUtil.format(tbSignInfo.getStartDate(), DateUtil.DATETIME_FORMAT_HM), DateUtil.format(tbSignInfo.getStopDate(), DateUtil.DATETIME_FORMAT_HM)));
            }
            // 计算签到地与打卡点的距离
            double distance = LocationUtil.getDistance(Double.valueOf(tbSignInfo.getLongitude()), Double.valueOf(tbSignInfo.getLatitude()),
                    input.getDouble("longitude"), input.getDouble("latitude"));
            // 签到逻辑（签到地和打卡地不同、或者距离大于200m,显示打卡失败！)
            if (distance > 200) {
                return new Output(ERROR_UNKNOWN, "签到地点不对，请到达指定地点定位！");
            }

            if (now.after(tbSignInfo.getStopDate())) {
                result.setMsg("你的签到状态为：【迟到】！");
                tbSign.setStatus(Constants.Sign.LATE);
            } else {
                result.setMsg("你的签到状态为：【签到】！");
                tbSign.setStatus(Constants.Sign.SIGNED);
            }
            try {
                tbSignService.insert(tbSign);
            } catch (Exception e) {
                logger.error(e.getStackTrace());
                return new Output(ERROR_NO_RECORD, "已经签到！");
            }
            result.setData(tbSign);
            result.setStatus(SUCCESS);
        } catch (Exception e) {
            logger.error(e, e);
            return new Output(ERROR_NO_RECORD, "签到：" + e.getCause());
        }
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
        TbSign tbSign = tbSignService.get(input.getLong("id"));
        if (null == tbSign) {
            return new Output(ERROR_NO_RECORD, "未能获取签到信息！");
        }
        tbSignService.delete(tbSign);
        result.setMsg("删除签到成功！");
        result.setStatus(SUCCESS);
        return result;
    }

    /**
     * 申请补签
     * <p>传入参数：</p>
     * <pre>
     *      method:suppleSign
     *      token: openid
     *      params: {infoId: 1}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.STU)
    public Output suppleSign(Input input) {
        Output result = new Output();
        TbSignInfo info = tbSignInfoService.get(input.getLong("infoId"));
        if (null == info) {
            return new Output(ERROR_UNKNOWN, "没有对应的签到信息！");
        }
        Map<String, Object> param = new HashMap<>();
        TbStudent tbStudent = (TbStudent) input.getCurrentTbUser();
        param.put("wxId", tbStudent.getWxId());
        param.put("infoId", info.getId());
        TbSign tbSign = tbSignService.get(param);

        if (null == tbSign) {
            if (new Date().before(info.getStopDate())) {
                return new Output(ERROR_NO_RECORD, "还没到补签时间!");
            }
            tbSign = new TbSign();
            tbSign.setTbStudent(tbStudent);
            tbSign.setInfo(info);
            tbSign.setSignDate(new Date());
        } else {
            if (Constants.Sign.SUPPLE_SIGN.equals(tbSign.getStatus())) {
                return new Output(ERROR_NO_RECORD, "您已申请补签！");
            }else if(Constants.Sign.SIGNED.equals(tbSign.getStatus())){
                return new Output(ERROR_NO_RECORD, "您已签到！");
            }
            tbSign.setModifyTime(new Date());
        }
        tbSign.setStatus(Constants.Sign.SUPPLE_SIGN);
        tbSignService.save(tbSign);
        result.setStatus(SUCCESS);
        result.setMsg("申请补签成功，待教师同意！");
        result.setData(tbSign);
        return result;
    }

    /**
     * 同意签到
     * <p>传入参数：</p>
     * <pre>
     *      method:modify
     *      token: openid
     *      params: {id: 2, isAccept: 1}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.ADMIN)
    public Output acceptSign(Input input) {
        Output result = new Output();
        TbSign tbSign = tbSignService.get(input.getLong("id"));
        if (null == tbSign) {
            return new Output(ERROR_NO_RECORD, "没有对应的签到！");
        }

        if (Constants.Sign.NOT_SIGNED.equals(input.getInteger("isAccept"))) {
            tbSign.setStatus(Constants.Sign.NOT_SIGNED);
            result.setMsg("补签未批准！");
        } else if (Constants.Sign.SIGNED.equals(input.getInteger("isAccept"))) {
            tbSign.setStatus(Constants.Sign.SIGNED);
            result.setMsg("补签成功！");
        } else {
            return new Output(ERROR_NO_RECORD, "请选择是否同意补签！【1：批准；2：不批准】");
        }
        tbSignService.update(tbSign);
        result.setStatus(SUCCESS);
        result.setData(tbSign);
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
        TbSignInfo tbSignInfo = tbSignInfoService.get(input.getLong("infoId"));
        if (null == tbSignInfo) {
            return new Output(ERROR_UNKNOWN, "没有对应的签到信息，请重试！");
        }

        Map<String, Object> param = new HashMap<>();
        param.put("classId", tbSignInfo.getTbClass().getId());
        List<TbStudent> tbStudentList = tbStudentService.query(param);
        List<Map<String, Object>> signList = new ArrayList<>();
        for (TbStudent tbStudent : tbStudentList) {
            signList.add(toMap(tbStudent, tbSignInfo));
        }
        result.setData(signList);
        result.setMsg("获取签到情况成功！");
        return result;
    }

    /**
     * 用于展示
     *
     * @param tbStudent  学生
     * @param tbSignInfo 签到信息
     * @return
     */
    public Map<String, Object> toMap(TbStudent tbStudent, TbSignInfo tbSignInfo) {
        Map<String, Object> result = new HashMap<String, Object>();
        // 查询该学生签到
        Map<String, Object> param = new HashMap<>();
        param.put("infoId", tbSignInfo.getId());
        param.put("stuId", tbStudent.getId());
        TbSign tbSign = tbSignService.get(param);
        if (tbSign == null) {
            result.put("status", Constants.Sign.NOT_SIGNED);
            result.put("signDate", "");
            result.put("signAddress", "");
        } else {
            result.put("id", tbSign.getId());
            result.put("status", tbSign.getStatus());
            result.put("signDate", DateUtil.format(tbSign.getSignDate(), DateUtil.DATETIME_FORMAT_YMDHMS));
            result.put("signAddress", tbSign.getSignAddress());
        }
        // 签到信息
        Map<String, Object> info = new HashMap<String, Object>();
        info.put("id", tbSignInfo.getId());
        info.put("signDate", DateUtil.format(tbSignInfo.getStopDate(), DateUtil.DATE_FORMAT));
        info.put("stopDate", DateUtil.format(tbSignInfo.getStopDate(), DateUtil.DATETIME_FORMAT_HM));
        info.put("startDate", DateUtil.format(tbSignInfo.getStartDate(), DateUtil.DATETIME_FORMAT_HM));
        info.put("address", tbSignInfo.getAddress());
        info.put("status", tbSignInfo.getStatus());
        info.put("admin", tbSignInfo.getAdmin());
        info.put("classes", tbSignInfo.getTbClass());
        info.put("course", tbSignInfo.getTbCourse());
        info.put("remark", tbSignInfo.getRemark());
        result.put("info", info);
        // 学生信息
        result.put("student", tbStudent);
        return result;
    }
}
