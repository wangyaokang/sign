/**
 * 
 */
package com.wyk.sign.persistence;


import com.wyk.framework.mybatis.BaseMapper;
import com.wyk.sign.model.Elective;
import org.springframework.stereotype.Repository;

import java.util.Map;


/**
 * @author wyk
 *
 */
@Repository
public interface ElectiveMapper extends BaseMapper<Elective> {

    /**
     * 根据课程、教师、班级删除授课信息
     * @param param
     * @return
     */
    int deleteElectiveByMap(Map<String, Object> param);
}
