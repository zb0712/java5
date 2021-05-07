package com.szb.java5.controller;


import com.szb.java5.bean.Admin;
import com.szb.java5.common.Result;
import com.szb.java5.service.AdminService;
import com.szb.java5.service.DepartmentService;
import com.szb.java5.service.QuestionnaireService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 石致彬
 * @since 2021-03-28
 */
@RestController
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private QuestionnaireService questionnaireService;

    @ApiOperation("获取所有的部门信息")
    @GetMapping("/department/show")
    public Result showDepartments() {
        return departmentService.showAllDepartment();
    }

    @ApiOperation("修改部门名称")
    @Secured("ROLE_super")
    @PostMapping("/department/updateName")
    public Result modifyDepartmentName(@RequestParam("departmentId") String id,
                                       @RequestParam("departmentName") String name) {
        Long departmentId = Long.parseLong(id);
        return departmentService.modifyDepartmentName(departmentId,name);
    }

    @ApiOperation("修改部门url")
    @PostMapping("/department/updateURL")
    public Result modifyDepartmentURL(@RequestParam("departmentId") String id,
                                      @RequestParam("departmentUrl") String url,
                                      Principal principal) {
        Long departmentId = Long.parseLong(id);
        String username = principal.getName();
        Admin admin = adminService.getAdminByName(username);
        if ("manager".equals(admin.getRole()) && !admin.getDepartmentId().equals(departmentId)) {
            return Result.error("你不是该部门管理员");
        }
        return departmentService.modifyDepartmentURL(departmentId,url);
    }

    @ApiOperation("修改部门简介")
    @PostMapping("/department/updateIntroduction")
    public Result modifyDepartmentIntroduction(@RequestParam("departmentId") String id,
                                               @RequestParam("introduction") String introduction,
                                               Principal principal) {
        Long departmentId = Long.parseLong(id);
        String username = principal.getName();
        Admin admin = adminService.getAdminByName(username);
        //如果不是对应部门的管理员或者部门不存在
        if ("manager".equals(admin.getRole()) && !admin.getDepartmentId().equals(departmentId)) {
            return Result.error("你不是该部门管理员");
        }
        return departmentService.modifyDepartmentIntroduction(departmentId,introduction);
    }

    @ApiOperation("设置部门报名表")
    @PostMapping("/department/updateQuestionnaire")
    public Result modifyDepartmentQuestionnaire(@RequestParam("departmentId") String id,
                                               @RequestParam("questionnaireId") String qid,
                                               Principal principal) {

        Long departmentId = Long.parseLong(id);
        Long questionnaireId = Long.parseLong(qid);
        String username = principal.getName();
        Admin admin = adminService.getAdminByName(username);
        //如果不是对应部门的管理员或者部门不存在
        if ("manager".equals(admin.getRole()) && !admin.getDepartmentId().equals(departmentId)) {
            return Result.error("你不是该部门管理员");
        }
        return departmentService.modifyDepartmentQuestionnaire(departmentId,questionnaireId);
    }

    @ApiOperation("根据部门id获取问卷")
    @GetMapping("/getDepartmentQuestionnaire")
    public Result getQuestionnaireByDepartmentId(@RequestParam("departmentId") String id) {
        Long departmentId = Long.parseLong(id);
        return questionnaireService.getQuestionnaireByDepartmentId(departmentId);
    }


}
