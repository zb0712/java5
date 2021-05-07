package com.szb.java5;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.szb.java5.bean.*;
import com.szb.java5.common.Result;
import com.szb.java5.common.Zxing;
import com.szb.java5.mapper.*;
import com.szb.java5.service.DepartmentService;
import com.szb.java5.utils.MailUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class Java5ApplicationTests {
    @Autowired
    AdminMapper adminMapper;

    @Autowired
    MailUtils mailUtils;

    @Autowired
    DepartmentMapper departmentMapper;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    StuDepartmentMapper  stuDepartmentMapper;

    @Autowired
    StuExamMapper stuExamMapper;

    @Autowired
    StudentMapper studentMapper;


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;
    @Test
    void contextLoads() {
        Page<StuExam> page = new Page<>(1,5);
        stuExamMapper.selectPage(page,null);
        List<StuExam> records = page.getRecords();
        System.out.println(records);
//        QueryWrapper<StuExam> wrapper = new QueryWrapper<StuExam>().select("stu_id").eq("exam_id", 3L);
//        List<StuExam> stuExams = stuExamMapper.selectList(wrapper);
//        List<Long> collect = stuExams.stream().map(StuExam::getStuId).collect(Collectors.toList());
//        collect.forEach(System.out::println);
//        List<Student> students = studentMapper.selectBatchIds(collect);
//        students.forEach(System.out::println);
//        List<String> collect1 = students.stream().map(Student::getStuEmail).collect(Collectors.toList());
//        String[] strings = new String[collect1.size()];
//        String[] strings1 = collect1.toArray(strings);
//        StuExam stuExam = new StuExam();
//        stuExam.setExamId(1L);
//        stuExam.setStatus("passed");
//        stuExamMapper.updateById(stuExam);
//        QueryWrapper<StuDepartment> wrapper = new QueryWrapper<>();
//        wrapper.select("stu_id").eq("status","examing").eq("department_id",1);
//        List<StuDepartment> stuDepartments = stuDepartmentMapper.selectList(wrapper);
//        for (StuDepartment stuDepartment : stuDepartments) {
//            System.out.println(stuDepartment);
//        }

//        int code = (int) ((Math.random() * 9 + 1) * 1000);
//        System.out.println(code);
//        List<Department> departments = departmentMapper.selectList(null);
//        System.out.println(departments);
//        ArrayList<String> list = new ArrayList<>();
//        list.add("1982700870@qq.com");
//        String[] strings = new String[list.size()];
//        String[] strings1 = list.toArray(strings);
//        mailUtils.sendSimpleMessage("TestEmail","这是一个测试", strings1);
//        Zxing.orCode("https://www.baidu.com/","D:\\data\\1.jpg");
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String encode = passwordEncoder.encode("admin");
//        Admin admin = new Admin(null,"admin",encode,null,"super");
//        adminMapper.insert(admin);
//        System.out.println(encode);
//        if (passwordEncoder.matches("123","$2a$10$R7oYBvn6lcFJU8AeDRgOKuFZAjXfb9aASO8qzb8MRBQkBm5u8aiLK")){
//            System.out.println("nice");
//        }
//        if (encode.equals("$2a$10$R7oYBvn6lcFJU8AeDRgOKuFZAjXfb9aASO8qzb8MRBQkBm5u8aiLK")) {
//            System.out.println("true");
//        } else {
//            System.out.println("false");
//        }
//        System.out.println(secret);
//        System.out.println(expiration);
    }

}
