package com.szb.java5.controller;

import com.szb.java5.bean.Admin;
import com.szb.java5.bean.Questionnaire;
import com.szb.java5.bean.Questions;
import com.szb.java5.common.Result;
import com.szb.java5.service.AdminService;
import com.szb.java5.service.QuestionnaireService;
import com.szb.java5.service.QuestionsService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;

/**
 * @author 石致彬
 * @since 2021-03-30 22:16
 */
@Secured("ROLE_manager")
@RestController
@Slf4j
public class ManagerAdminController {

    @Autowired
    AdminService adminService;
    @Autowired
    QuestionnaireService questionnaireService;
    @Autowired
    QuestionsService questionsService;

    @ApiOperation("创建问卷")
    @PostMapping("/creatQuestionnaire")
    public Result createQuestionnaire(@RequestBody Questionnaire questionnaire,
                                      Principal principal) {
        //获取所管理的部门id
        String name = principal.getName();
        Admin admin = adminService.getAdminByName(name);
        Long departmentId = admin.getDepartmentId();
        Questionnaire questionnaire1 = questionnaireService.getQuestionnaireByNameAndDepartId(questionnaire.getQuestionnaireName(), departmentId);
        if (questionnaire1 != null) {
            return Result.error("该问卷已存在");
        }

        //把问卷写入数据库
        questionnaire.setBeginTime(new Date());
        Integer lastDays = questionnaire.getLastDays();
        questionnaire.setOverTime(new Date(System.currentTimeMillis()+lastDays*3600*24*1000));
        questionnaire.setDepartmentId(departmentId);
        questionnaireService.creatQuestionnaire(questionnaire);
        //根据部门id和名字获取问卷
        questionnaire1 = questionnaireService.getQuestionnaireByNameAndDepartId(questionnaire.getQuestionnaireName(),departmentId);
        //获取问卷的所有问题并写入数据库
        List<Questions> questions = questionnaire.getQuestions();
        for (Questions question : questions) {
            question.setQuestionnaireId(questionnaire1.getQuestionnaireId());
            questionsService.creatQuestion(question);
        }
        return Result.ok(null);
    }

    @ApiOperation("获取所属部门的所有问卷信息")
    @GetMapping("/Questionnaire/info")
    public Result getAllQuestionnaireInfo(Principal principal) {
        String name = principal.getName();
        Admin admin = adminService.getAdminByName(name);
        return questionnaireService.getQuestionnairesByDepartmentId(admin.getDepartmentId());
    }

    @ApiOperation("根据id获取问卷")
    @GetMapping("/getQuestionnaire")
    public Result getQuestionnaireById(@RequestParam("questionnaireId") String id,
                                       Principal principal) {
        Long questionnaireId = Long.parseLong(id);
        String username = principal.getName();
        Admin admin = adminService.getAdminByName(username);
        return questionnaireService.getQuestionnaireById(questionnaireId,admin);
    }

    @ApiOperation("发布新一轮考核")
    @PostMapping("/creatExam")
    public Result creatExam(@RequestParam("examName") String examName,
                            Principal principal) {
        String name = principal.getName();
        Admin admin = adminService.getAdminByName(name);
        Long departmentId = admin.getDepartmentId();
        return adminService.creatExam(examName,departmentId);
    }

    @ApiOperation("获取所有的考核基本信息")
    @GetMapping("/ExamInfo")
    public Result getAllExamInfo(Principal principal) {
        String name = principal.getName();
        Admin admin = adminService.getAdminByName(name);
        Long departmentId = admin.getDepartmentId();
        return adminService.getAllExamInfo(departmentId);
    }

    @ApiOperation("通过某一轮考核")
    @PostMapping("/pass")
    public Result pass(@RequestParam("stuId") String sId,
                       @RequestParam("examId") String eId,
                       Principal principal){
        Long stuId = Long.parseLong(sId);
        Long examId = Long.parseLong(eId);
        String name = principal.getName();
        Admin admin = adminService.getAdminByName(name);
        Long departmentId = admin.getDepartmentId();
        return adminService.pass(stuId,examId,departmentId);
    }

    @ApiOperation("淘汰某个学生")
    @PostMapping("/fail")
    public Result fail(@RequestParam("stuId") String sId,
                       @RequestParam("examId") String eId,
                       Principal principal){
        Long stuId = Long.parseLong(sId);
        Long examId = Long.parseLong(eId);
        String name = principal.getName();
        Admin admin = adminService.getAdminByName(name);
        Long departmentId = admin.getDepartmentId();
        return adminService.fail(stuId,examId,departmentId);
    }

    @ApiOperation("根据考核id结束一轮考核")
    @PostMapping("/finish")
    public Result finishExam(@RequestParam("examId") String id,
                             Principal principal) {
        Long examId = Long.parseLong(id);
        String name = principal.getName();
        Admin admin = adminService.getAdminByName(name);
        Long departmentId = admin.getDepartmentId();
        return adminService.finishExam(examId,departmentId);
    }

    @ApiOperation("根据考核id查看考核结果详情")
    @PostMapping("/msg")
    public Result getExamMsg(@RequestParam("examId") String id,
                             @RequestParam("current") String cu,
                             @RequestParam("pageSize") String size,
                             Principal principal) {
        Integer current = Integer.parseInt(cu);
        Integer pageSize = Integer.parseInt(size);
        Long examId = Long.parseLong(id);
        String name = principal.getName();
        Admin admin = adminService.getAdminByName(name);
        Long departmentId = admin.getDepartmentId();
        return adminService.getExamMsg(examId,current,pageSize,departmentId);
    }

}
