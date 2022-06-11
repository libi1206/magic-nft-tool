package com.libi.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libi.bean.Student;
import com.libi.dao.StudentMapper;
import com.libi.service.StudentService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author libi
 * @since 2022-06-11
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

}
