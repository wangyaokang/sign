/**
 * 
 */
package com.wyk.sign.persistence;

import com.wyk.sign.model.Student;
import org.springframework.stereotype.Repository;
import com.wyk.framework.mybatis.BaseMapper;

import java.util.Map;


/**
 * @author wyk
 *
 */
@Repository
public interface StudentMapper extends BaseMapper<Student> {

    /**
     * 批量更新数据
     *
     * @param param
     */
   int batchUpdate(Map<String, Object> param);

}
