package com.szb.java5.service;

import com.szb.java5.bean.Student;
import com.baomidou.mybatisplus.extension.service.IService;
import com.szb.java5.common.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 石致彬
 * @since 2021-04-08
 */
public interface StudentService extends IService<Student> {

    Result submit(Student student);
    Student getStudentByNameAndNum(String name,String num);
}
