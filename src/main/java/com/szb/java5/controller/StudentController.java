package com.szb.java5.controller;

import com.szb.java5.bean.Student;
import com.szb.java5.common.Result;
import com.szb.java5.service.StudentService;
import com.szb.java5.utils.MailUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author 石致彬
 * @since 2021-04-12 23:57
 */
@RestController
public class StudentController {
    @Autowired
    private MailUtils mailSender;

    @Autowired
    private StudentService studentService;

    @ApiOperation("邮件发送验证码，确保输入的邮箱真实性")
    @PostMapping("/code")
    public Result sendCode(@RequestParam String email, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        String code = ""+(int) ((1+Math.random()*9)*1000);
        session.setAttribute("code",code);
        session.setAttribute("email",email);
        try {
            mailSender.sendSimpleMessage("报名验证码",code,email);
        } catch (Exception e) {
            throw new Exception("邮件发送失败，请检查邮箱是否输入正确");
        }
        return Result.ok(null);
    }

    @ApiOperation("提交报名表")
    @PostMapping("/submit")
    public Result submitQuestionnaire(@RequestBody Student student, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String code = (String) session.getAttribute("code");
        String email = (String) session.getAttribute("email");
        if (!student.getStuEmail().equals(email) || !student.getCode().equals(code)) {
            return Result.error("验证码错误");
        }
        return studentService.submit(student);
    }
}
