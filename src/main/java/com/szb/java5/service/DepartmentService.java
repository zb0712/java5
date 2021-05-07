package com.szb.java5.service;

import com.szb.java5.bean.Department;
import com.baomidou.mybatisplus.extension.service.IService;
import com.szb.java5.common.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 石致彬
 * @since 2021-03-28
 */
public interface DepartmentService extends IService<Department> {

    Result creatDepartment(Department department);

    Result showAllDepartment();

    Result modifyDepartmentName(Long departmentId,String name);

    Result deleteDepartment(Long departmentId);

    Result modifyDepartmentURL(Long departmentId, String url);

    Result modifyDepartmentIntroduction(Long departmentId, String introduction);

    Result modifyDepartmentQuestionnaire(Long departmentId,Long questionnaireId);

}
