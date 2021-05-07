package com.szb.java5.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.szb.java5.bean.Department;
import com.szb.java5.common.Result;
import com.szb.java5.mapper.DepartmentMapper;
import com.szb.java5.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 石致彬
 * @since 2021-03-28
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {

    @Autowired
    DepartmentMapper departmentMapper;

    @Override
    public Result creatDepartment(Department department) {
        String departmentName = department.getDepartmentName();
        String departmentUrl = department.getDepartmentUrl();
        if (null != departmentMapper.selectOne(new QueryWrapper<Department>().eq("department_name",departmentName))) {
            return Result.error("该部门已存在");
        }
        if (null != departmentMapper.selectOne(new QueryWrapper<Department>().eq("department_url",departmentUrl))) {
            return Result.error("该URL已存在");
        }
        departmentMapper.insert(department);
        return Result.ok(null);
    }

    @Override
    public Result showAllDepartment() {
        List<Department> departments = departmentMapper.selectList(null);
        Map<String,Object> map = new HashMap<>();
        map.put("departments",departments);
        return Result.ok(map);
    }

    @Override
    public Result modifyDepartmentName(Long departmentId,String name) {
        Department department1 = departmentMapper.selectOne(new QueryWrapper<Department>().eq("department_name", name));
        if (department1 != null) {
            return Result.error("该部门已存在");
        }
        Department department = new Department();
        department.setDepartmentId(departmentId);
        department.setDepartmentName(name);
        departmentMapper.updateById(department);
        return Result.ok(null);
    }

    @Override
    public Result deleteDepartment(Long departmentId) {
        Department department = departmentMapper.selectOne(new QueryWrapper<Department>().eq("department_id", departmentId));
        if (department == null) {
            return Result.error("该部门不存在");
        }
        departmentMapper.deleteById(departmentId);
        return Result.ok(null);
    }

    @Override
    public Result modifyDepartmentURL(Long departmentId, String url) {
        Department department = departmentMapper.selectOne(new QueryWrapper<Department>().eq("department_url", url));
        if (department != null) {
            return Result.error("该url已存在");
        }
        Department department1 = new Department();
        department1.setDepartmentId(departmentId);
        department1.setDepartmentUrl(url);
        departmentMapper.updateById(department1);
        return Result.ok(null);
    }

    @Override
    public Result modifyDepartmentIntroduction(Long departmentId, String introduction) {
        Department department = new Department();
        department.setDepartmentId(departmentId);
        department.setIntroduction(introduction);
        departmentMapper.updateById(department);
        return Result.ok(null);
    }

    @Override
    public Result modifyDepartmentQuestionnaire(Long departmentId,Long questionnaireId) {
        Department department = new Department();
        department.setDepartmentId(departmentId);
        department.setQuestionnaireId(questionnaireId);

        departmentMapper.updateById(department);
        return Result.ok(null);
    }

}
