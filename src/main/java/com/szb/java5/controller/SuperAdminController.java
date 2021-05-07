package com.szb.java5.controller;

import com.szb.java5.bean.Admin;
import com.szb.java5.bean.Department;
import com.szb.java5.common.Result;
import com.szb.java5.service.AdminService;
import com.szb.java5.service.DepartmentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * @author 石致彬
 * @since 2021-03-30 22:15
 */
@RestController("/super")
@Secured("ROLE_super")
public class SuperAdminController {
    @Autowired
    DepartmentService departmentService;

    @Autowired
    AdminService adminService;

    @ApiOperation("新建部门")
    @PostMapping("/creatDepart")
    public Result creatDepartment(Department department) {
        return departmentService.creatDepartment(department);
    }

    @ApiOperation("删除部门")
//    @DeleteMapping("/deleteDepartment")
    @PostMapping("/deleteDepartment")
    public Result deleteDepartment(@RequestParam("departmentId") String id) {
        Long departmentId = Long.parseLong(id);
        return departmentService.deleteDepartment(departmentId);
    }

    @ApiOperation("添加部门管理员")
    @PostMapping("/regist")
    public Result creatAdmin(@RequestParam("adminName") String name,
                             @RequestParam("adminPassword") String password,
                             @RequestParam("departmentName") String departmentName) {
        return adminService.creatAdmin(name,password,departmentName);
    }

    @ApiOperation("查看部门报名人员信息")
    @PostMapping("/departmentStuInfo")
    public Result getAllStudentInfoByDepartmentId(@RequestParam("departmentId") String id) {
        Long departmentId = Long.parseLong(id);
        return adminService.getAllStudentInfoByDepartmentId(departmentId);
    }

    @ApiOperation("修改报名学生的姓名")
    @PostMapping("/updateStuName")
    public Result updateStudentName(@RequestParam("stuId") String id,
                                    @RequestParam("stuName") String name) {
        Long stuId = Long.parseLong(id);
        return adminService.updateStudentName(stuId,name);
    }

    @ApiOperation("修改报名学生的学号")
    @PostMapping("/updateStuNum")
    public Result updateStudentNum(@RequestParam("stuId") String id,
                                    @RequestParam("stuNum") String stuNum) {
        Long stuId = Long.parseLong(id);
        return adminService.updateStudentNum(stuId,stuNum);
    }

    @ApiOperation("修改报名学生的邮箱")
    @PostMapping("/updateStuEmail")
    public Result updateStuEmail(@RequestParam("stuId") String id,
                                    @RequestParam("stuEmail") String stuEmail) {
        Long stuId = Long.parseLong(id);
        return adminService.updateStudentEmail(stuId,stuEmail);
    }


}
