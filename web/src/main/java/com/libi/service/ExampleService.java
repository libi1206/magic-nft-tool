package com.libi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.libi.bean.Student;
import com.libi.model.TokenInfo;
import com.libi.response.BaseResult;
import com.libi.model.StudentDTO;

/**
 * @author :Libi
 * @version :1.0
 * @date :2020-04-03 14:56
 */
public interface ExampleService {
    BaseResult<Student> creationStudent(TokenInfo tokenInfo, StudentDTO studentDTO);

    BaseResult<Student> selectStudent(Integer studentId);

    BaseResult<IPage> selectAll(Integer page, Integer pageSize);
}
